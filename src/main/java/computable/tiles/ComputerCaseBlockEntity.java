package computable.tiles;

import computable.api.ComputableBlockEntity;
import computable.api.Inspectable;
import computable.api.UpdateRequestable;
import computable.client.sounds.ComputerSoundInstance;
import computable.content.ComputableBlockEntityTypes;
import computable.content.ComputableDataComponentTypes;
import computable.content.ComputableItems;
import computable.content.ComputableSoundEvents;
import computable.items.MotherboardItem;
import computable.net.ComputableNetworking;
import computable.net.ComputerCaseUpdate;
import computable.grid.NetworkMember;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;

import java.util.List;

public class ComputerCaseBlockEntity extends ComputableBlockEntity implements Inspectable, UpdateRequestable<ComputerCaseUpdate> {
    private NetworkMember networkMember;
    private ComputerSoundInstance idleLow;
    private ComputerSoundInstance idleHigh;
    private boolean running = false;
    private boolean light = true;
    public final IItemHandlerModifiable itemHandler = new ItemHandler();


    public ComputerCaseBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ComputableBlockEntityTypes.COMPUTER_CASE.get(), blockPos, blockState);
    }

    public NetworkMember getNetworkMember() {
        return networkMember;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean getLight() {
        return light;
    }

    public void start() {
        running = true;
        ComputableNetworking.sendBlockEntityUpdate(this, getUpdatePayload());
    }

    public void stop() {
        running = false;
        ComputableNetworking.sendBlockEntityUpdate(this, getUpdatePayload());
    }

    @Override
    public void clearRemoved() {
        super.clearRemoved();
        Level level = getLevel();
        if (level == null) {
            return;
        }
        if (level.isClientSide()) {
            ComputableNetworking.requestBlockPosUpdate(getBlockPos());
            return;
        }
        networkMember = new NetworkMember();
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (idleLow != null) {
            idleLow.kill();
        }
        if (idleHigh != null) {
            idleHigh.kill();
        }
    }

    @Override
    public void serverTick() {
        networkMember.getNetwork().getNetworkEnergy().use(10, false);
    }

    @Override
    public void clientTick() {

    }

    @Override
    public void inspect(List<Component> components) {
        networkMember.inspect(components);
    }

    @Override
    public ComputerCaseUpdate getUpdatePayload() {
        return new ComputerCaseUpdate(getBlockPos(), running, light);
    }

    @Override
    public void clientUpdate(ComputerCaseUpdate payload) {
        if (payload.running()) {
            if (!running) {
                running = true;
                idleLow = new ComputerSoundInstance(ComputableSoundEvents.COMPUTER_IDLE_LOW.get(), getBlockPos());
                Minecraft.getInstance().getSoundManager().queueTickingSound(idleLow);
            }
        } else {
            if (running) {
                running = false;
                idleLow.kill();
                idleLow = null;
            }
        }
        light = payload.light();
    }

    public static class ItemHandler implements IItemHandlerModifiable {
        ItemStack motherboardStack = ItemStack.EMPTY;

        @Override
        public int getSlots() {
            return 9;
        }

        @Override
        public ItemStack getStackInSlot(int slot) {
            if (slot == 9) {
                return motherboardStack;
            } else if (motherboardStack.getItem() instanceof MotherboardItem motherboardItem) {
                IItemHandler motherboardHandler = motherboardItem.createItemHandler(motherboardStack);
                return motherboardHandler.getStackInSlot(slot);
            }
            return ItemStack.EMPTY;
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if (slot == 9 && motherboardStack.isEmpty()) {
                if (!simulate) {
                    motherboardStack = stack;
                }
                return ItemStack.EMPTY;
            } else if (slot != 9 && motherboardStack.getItem() instanceof MotherboardItem motherboardItem) {
                IItemHandler motherboardHandler = motherboardItem.createItemHandler(motherboardStack);
                return motherboardHandler.insertItem(slot, stack, simulate);
            }
            return stack;
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (slot == 9 && !motherboardStack.isEmpty()) {
                ItemStack copiedMotherboardStack = motherboardStack;
                if (!simulate) {
                    motherboardStack = ItemStack.EMPTY;
                }
                return copiedMotherboardStack;
            } else if (slot != 9 && motherboardStack.getItem() instanceof MotherboardItem motherboardItem) {
                IItemHandler motherboardHandler = motherboardItem.createItemHandler(motherboardStack);
                return motherboardHandler.extractItem(slot, amount, simulate);
            }
            return ItemStack.EMPTY;
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            if (slot == 9) {
                return stack.is(ComputableItems.MOTHERBOARD);
            } else if (motherboardStack.getItem() instanceof MotherboardItem motherboardItem) {
                IItemHandler motherboardHandler = motherboardItem.createItemHandler(motherboardStack);
                return motherboardHandler.isItemValid(slot, stack);
            }
            return false;
        }

        @Override
        public void setStackInSlot(int slot, ItemStack stack) {
            if (slot == 9) {
                motherboardStack = stack;
            } else if (motherboardStack.getItem() instanceof MotherboardItem motherboardItem) {
                IItemHandlerModifiable motherboardHandler = motherboardItem.createItemHandler(motherboardStack);
                motherboardHandler.setStackInSlot(slot, stack);
            }
        }
    }


}
