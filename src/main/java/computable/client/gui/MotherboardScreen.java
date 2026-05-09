package computable.client.gui;

import computable.Computable;
import computable.gui.MotherboardMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MotherboardScreen extends AbstractContainerScreen<MotherboardMenu> {

    private static final ResourceLocation MENU_SCREEN = ResourceLocation.fromNamespaceAndPath(Computable.ID, "textures/gui/motherboard.png");

    public MotherboardScreen(MotherboardMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(MENU_SCREEN, this.leftPos, this.topPos-15, 0, 0, this.imageWidth, this.imageHeight+25);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, this.inventoryLabelX, -9, 4210752, false);

        guiGraphics.drawString(this.font, Component.translatable("computable.motherboard.drives"), 136, 18, 4210752, false);
        guiGraphics.drawString(this.font, Component.translatable("computable.motherboard.eeprom"), 132, 50, 4210752, false);
        guiGraphics.drawString(this.font, Component.translatable("computable.motherboard.memory"), this.inventoryLabelX, 18, 4210752, false);
        guiGraphics.drawString(this.font, Component.translatable("computable.motherboard.cards"), this.inventoryLabelX, 50, 4210752, false);

        guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY+10, 4210752, false);
    }
}
