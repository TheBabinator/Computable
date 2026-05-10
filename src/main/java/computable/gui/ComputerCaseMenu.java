package computable.gui;

import computable.content.ComputableMenus;
import computable.net.ComputableNetworking;
import computable.tiles.ComputerCaseBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;

public class ComputerCaseMenu extends ComputableContainerMenu {
    private final ComputerCaseBlockEntity blockEntity;
    private final DataSlot buttonSlot;

    public ComputerCaseMenu(int containerId, Inventory inventory) {
        super(ComputableMenus.COMPUTER_CASE.get(), containerId, 0);
        blockEntity = null;

        buttonSlot = DataSlot.standalone();
        addDataSlot(buttonSlot);

        addPlayerHotbar(inventory, 8, 142);
        addPlayerInventory(inventory, 8, 84);
    }

    public ComputerCaseMenu(int containerId, Inventory inventory, ComputerCaseBlockEntity blockEntity) {
        super(ComputableMenus.COMPUTER_CASE.get(), containerId, 0);
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
