package computable.gui;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ComputableContainerMenu extends AbstractContainerMenu {
    private final int containerSlots;

    protected ComputableContainerMenu(MenuType<?> menuType, int containerId, int containerSlots) {
        super(menuType, containerId);
        this.containerSlots = containerSlots;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack resultStack = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemStack = slot.getItem();
            resultStack = itemStack.copy();
            if (index < containerSlots) {
                if (!this.moveItemStackTo(itemStack, containerSlots, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }
            if (itemStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return resultStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    protected void addPlayerInventory(Inventory pPlayerInventory, int x, int y) {
        for (int i = 0; i < 3; i++) {
            for (int l = 0; l < 9; l++) {
                addSlot(new Slot(pPlayerInventory, l + i * 9 + 9, x + l * 18, y + i * 18));
            }
        }
    }

    protected void addPlayerHotbar(Inventory pPlayerInventory, int x, int y) {
        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(pPlayerInventory, i, x + i * 18, y));
        }
    }
}
