package computable.items;

import computable.content.ComputableDataComponentTypes;
import computable.gui.MotherboardMenu;
import computable.items.components.MotherboardContents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandlerModifiable;

import java.util.Objects;

public class MotherboardItem extends BundleItem {
    public MotherboardItem() {
        super(new Properties().stacksTo(1).component(ComputableDataComponentTypes.MOTHERBOARD_CONTENTS.get(), new MotherboardContents(ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY)));
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        return false;
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(new SimpleMenuProvider(
                    (containerId, playerInventory, player1) -> new MotherboardMenu(containerId, playerInventory, createItemHandler(itemStack)),
                    getName(itemStack)
            ));
        }
        return InteractionResultHolder.success(itemStack);
    }



    public ItemHandler createItemHandler(ItemStack itemStack) {
        return new ItemHandler(itemStack);
    }

    public record ItemHandler(ItemStack itemStack) implements IItemHandlerModifiable {

        @Override
        public int getSlots() {
            return 9;
        }

        @Override
        public ItemStack getStackInSlot(int slot) {
            return Objects.requireNonNull(itemStack.get(ComputableDataComponentTypes.MOTHERBOARD_CONTENTS.get())).getItemFromSlot(slot);
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            MotherboardContents contents = itemStack.get(ComputableDataComponentTypes.MOTHERBOARD_CONTENTS.get());
            if (!simulate) {
                assert contents != null;
                MotherboardContents newContents = contents.addItem(stack, slot);
                itemStack.set(ComputableDataComponentTypes.MOTHERBOARD_CONTENTS, newContents);
            }
            return stack.copyWithCount(stack.getCount() - 1);
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            MotherboardContents contents = itemStack.get(ComputableDataComponentTypes.MOTHERBOARD_CONTENTS.get());
            assert contents != null;
            ItemStack replacedItem = contents.getItemFromSlot(slot);
            if (!simulate) {
                MotherboardContents newContents = contents.removeItem(slot);
                itemStack.set(ComputableDataComponentTypes.MOTHERBOARD_CONTENTS.get(), newContents);
            }
            return replacedItem;
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return Objects.requireNonNull(itemStack.get(ComputableDataComponentTypes.MOTHERBOARD_CONTENTS.get())).validateSlot(slot, stack);
        }

        @Override
        public void setStackInSlot(int slot, ItemStack stack) {
            MotherboardContents contents = itemStack.get(ComputableDataComponentTypes.MOTHERBOARD_CONTENTS.get());
            assert contents != null;
            MotherboardContents newContents = contents.removeItem(slot).addItem(stack, slot);
            itemStack.set(ComputableDataComponentTypes.MOTHERBOARD_CONTENTS.get(), newContents);
        }
    }

}
