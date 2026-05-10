package computable.gui;

import computable.content.ComputableMenus;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class MotherboardMenu extends ComputableContainerMenu {
    public MotherboardMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, new ItemStackHandler(9));
    }

    public MotherboardMenu(int containerId, Inventory inventory, IItemHandler dataInventory) {
        super(ComputableMenus.MOTHERBOARD.get(), containerId, 9);

        int i = 0;
        // cpu
        this.addSlot(new SlotItemHandler(dataInventory, i, 80, 19));
        // memory
        this.addSlot(new SlotItemHandler(dataInventory, ++i, 8, 44));
        this.addSlot(new SlotItemHandler(dataInventory, ++i, 26, 44));
        // drives
        this.addSlot(new SlotItemHandler(dataInventory, ++i, 134, 44));
        this.addSlot(new SlotItemHandler(dataInventory, ++i, 152, 44));
        // cards
        this.addSlot(new SlotItemHandler(dataInventory, ++i, 8, 76));
        this.addSlot(new SlotItemHandler(dataInventory, ++i, 26, 76));
        this.addSlot(new SlotItemHandler(dataInventory, ++i, 44, 76));
        // EEPROM
        this.addSlot(new SlotItemHandler(dataInventory, ++i, 152, 76));

        addPlayerHotbar(inventory, 8, 167);
        addPlayerInventory(inventory, 8, 109);
    }
}
