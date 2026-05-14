package computable.gui;

import computable.content.ComputableDataComponentTypes;
import computable.content.ComputableMenus;
import computable.items.components.Hardware;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class MotherboardMenu extends ComputableContainerMenu {

    final int motherboardSlot;

    public MotherboardMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, new ItemStackHandler(9), DataSlot.standalone());
    }

    public MotherboardMenu(int containerId, Inventory inventory, IItemHandler dataInventory, DataSlot motherboardInvSlot) {
        super(ComputableMenus.MOTHERBOARD.get(), containerId, 9);
        addDataSlot(motherboardInvSlot);
        motherboardSlot = motherboardInvSlot.get();

        int i = 0;
        // cpu
        this.addSlot(new MotherboardSlotItemHandler(dataInventory, i, 80, 19, Hardware.CPU));
        // memory
        this.addSlot(new MotherboardSlotItemHandler(dataInventory, ++i, 8, 43, Hardware.MEMORY));
        this.addSlot(new MotherboardSlotItemHandler(dataInventory, ++i, 26, 43, Hardware.MEMORY));
        // drives
        this.addSlot(new MotherboardSlotItemHandler(dataInventory, ++i, 134, 43, Hardware.DRIVE));
        this.addSlot(new MotherboardSlotItemHandler(dataInventory, ++i, 152, 43, Hardware.DRIVE));
        // cards
        this.addSlot(new MotherboardSlotItemHandler(dataInventory, ++i, 8, 76, Hardware.CARD));
        this.addSlot(new MotherboardSlotItemHandler(dataInventory, ++i, 26, 76, Hardware.CARD));
        this.addSlot(new MotherboardSlotItemHandler(dataInventory, ++i, 44, 76, Hardware.CARD));
        // EEPROM
        this.addSlot(new MotherboardSlotItemHandler(dataInventory, ++i, 152, 76, Hardware.EEPROM));

        addPlayerHotbar(inventory, 8, 167);
        addPlayerInventory(inventory, 8, 109);
    }

    @Override
    protected void addPlayerHotbar(Inventory pPlayerInventory, int x, int y) {
        for (int i = 0; i < 9; i++) {
            if (i != motherboardSlot) {
                addSlot(new Slot(pPlayerInventory, i, x + i * 18, y));
            }
            else {
                addSlot(new Slot(pPlayerInventory, i, x + i * 18, y) {
                    @Override
                    public boolean mayPickup(Player player) {
                        return false;
                    }
                });
            }
        }
    }

    static class MotherboardSlotItemHandler extends SlotItemHandler {

        final Hardware hardware;

        public MotherboardSlotItemHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition, Hardware hardware) {
            super(itemHandler, index, xPosition, yPosition);
            this.hardware = hardware;
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            Hardware hardwareType = stack.get(ComputableDataComponentTypes.HARDWARE);
            if (hardwareType == hardware) {
                return super.mayPlace(stack);
            }
            return false;
        }
    }
}
