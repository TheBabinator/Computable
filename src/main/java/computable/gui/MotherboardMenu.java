package computable.gui;

import computable.content.ComputableMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class MotherboardMenu extends AbstractContainerMenu {

    public MotherboardMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, new ItemStackHandler(9));
    }

    public MotherboardMenu(int containerId, Inventory inventory, IItemHandler dataInventory) {
        super(ComputableMenus.MOTHERBOARD_MENU.get(), containerId);

        int i = 0;
        // cpu
        this.addSlot(new SlotItemHandler(dataInventory, i, 80, 4));
        // memory
        this.addSlot(new SlotItemHandler(dataInventory, ++i, 8, 29));
        this.addSlot(new SlotItemHandler(dataInventory, ++i, 26, 29));
        // drives
        this.addSlot(new SlotItemHandler(dataInventory, ++i, 134, 29));
        this.addSlot(new SlotItemHandler(dataInventory, ++i, 152, 29));
        // cards
        this.addSlot(new SlotItemHandler(dataInventory, ++i, 8, 61));
        this.addSlot(new SlotItemHandler(dataInventory, ++i, 26, 61));
        this.addSlot(new SlotItemHandler(dataInventory, ++i, 44, 61));
        // EEPROM
        this.addSlot(new SlotItemHandler(dataInventory, ++i, 152, 61));

        addPlayerHotbar(inventory);
        addPlayerInventory(inventory);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    private void addPlayerInventory(Inventory pPlayerInventory) {
        for (int i = 0; i < 3; i++) {
            for (int l = 0; l < 9; l++) {
                addSlot(new Slot(pPlayerInventory, l + i * 9 + 9, 8 + l * 18, 91 + i * 18+3));
            }
        }
    }

    private void addPlayerHotbar(Inventory pPlayerInventory) {
        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(pPlayerInventory, i, 8 + i * 18, 149+3));
        }
    }
}
