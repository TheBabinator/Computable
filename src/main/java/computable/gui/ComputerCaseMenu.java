package computable.gui;

import computable.content.ComputableItems;
import computable.content.ComputableMenus;
import computable.items.MotherboardItem;
import computable.items.components.Hardware;
import computable.net.ComputableNetworking;
import computable.tiles.ComputerCaseBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class ComputerCaseMenu extends ComputableContainerMenu {
    private final ComputerCaseBlockEntity blockEntity;
    private final DataSlot buttonSlot;
    int offsetX = 98;
    int offsetY = 17;

    public ComputerCaseMenu(int containerId, Inventory inventory) {
        super(ComputableMenus.COMPUTER_CASE.get(), containerId, 10);
        blockEntity = null;

        buttonSlot = DataSlot.standalone();
        addDataSlot(buttonSlot);
        // motherboard
        addSlot(new SlotItemHandler(new ItemStackHandler(), 0, 62, 35));
        // motherboard proxy
        addSlot(new SlotItemHandler(new ItemStackHandler(), 0, offsetX, offsetY));
        addSlot(new SlotItemHandler(new ItemStackHandler(), 0,  offsetX + 18, offsetY));
        addSlot(new SlotItemHandler(new ItemStackHandler(), 0,  offsetX + 36, offsetY));
        addSlot(new SlotItemHandler(new ItemStackHandler(), 0,  offsetX, offsetY + 18));
        addSlot(new SlotItemHandler(new ItemStackHandler(), 0,  offsetX + 18, offsetY + 18));
        addSlot(new SlotItemHandler(new ItemStackHandler(), 0,  offsetX + 36, offsetY + 18));
        addSlot(new SlotItemHandler(new ItemStackHandler(), 0,  offsetX, offsetY + 36));
        addSlot(new SlotItemHandler(new ItemStackHandler(), 0,  offsetX + 18, offsetY + 36));
        addSlot(new SlotItemHandler(new ItemStackHandler(), 0,  offsetX + 36, offsetY + 36));

        addPlayerHotbar(inventory, 8, 142);
        addPlayerInventory(inventory, 8, 84);
    }

    public ComputerCaseMenu(int containerId, Inventory inventory, IItemHandlerModifiable itemHandler, ComputerCaseBlockEntity blockEntity) {
        super(ComputableMenus.COMPUTER_CASE.get(), containerId, 10);
        this.blockEntity = blockEntity;

        buttonSlot = new DataSlot() {
            @Override
            public int get() {
                if (blockEntity.isRunning()) {
                    return 1;
                } else {
                    return 0;
                }
            }

            @Override
            public void set(int value) {
                if (value == 0) {
                    blockEntity.stop();
                } else {
                    blockEntity.start();
                }
            }
        };
        addDataSlot(buttonSlot);
        // motherboard
        addSlot(new SlotItemHandler(itemHandler, 9, 62, 35));
        // motherboard proxy
        // row 1
        addSlot(new SlotItemHandler(itemHandler, 8, offsetX, offsetY));
        addSlot(new SlotItemHandler(itemHandler, 0, offsetX + 18, offsetY));
        addSlot(new SlotItemHandler(itemHandler, 5, offsetX + 36, offsetY));
        // row 2
        addSlot(new SlotItemHandler(itemHandler, 3, offsetX, offsetY + 18));
        addSlot(new SlotItemHandler(itemHandler, 1, offsetX + 18, offsetY + 18));
        addSlot(new SlotItemHandler(itemHandler, 6, offsetX + 36, offsetY + 18));
        // row 3
        addSlot(new SlotItemHandler(itemHandler, 4, offsetX, offsetY + 36));
        addSlot(new SlotItemHandler(itemHandler, 2, offsetX + 18, offsetY + 36));
        addSlot(new SlotItemHandler(itemHandler, 7, offsetX + 36, offsetY + 36));

        addPlayerHotbar(inventory, 8, 142);
        addPlayerInventory(inventory, 8, 84);
    }

    @Override
    public boolean stillValid(Player player) {
        if (blockEntity != null) {
            return Container.stillValidBlockEntity(blockEntity, player);
        }
        return true;
    }

    public boolean getButton() {
        return buttonSlot.get() != 0;
    }

    public void setButton(boolean value) {
        buttonSlot.set(value ? 1 : 0);
        ComputableNetworking.updateServerMenuDataSlot(0, value ? 1 : 0);
    }
}
