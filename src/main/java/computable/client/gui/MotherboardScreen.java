package computable.client.gui;

import computable.Computable;
import computable.gui.MotherboardMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MotherboardScreen extends ComputableContainerScreen<MotherboardMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Computable.ID, "textures/gui/motherboard.png");

    public MotherboardScreen(MotherboardMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        imageWidth = 176;
        imageHeight = 191;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);
        guiGraphics.drawString(font, Component.translatable("computable.motherboard.drives"), 136, 31, 4210752, false);
        guiGraphics.drawString(font, Component.translatable("computable.motherboard.eeprom"), 132, 64, 4210752, false);
        guiGraphics.drawString(font, Component.translatable("computable.motherboard.memory"), 8, 31, 4210752, false);
        guiGraphics.drawString(font, Component.translatable("computable.motherboard.cards"), 8, 64, 4210752, false);
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int x, int y) {
        super.renderTooltip(guiGraphics, x, y);
    }
}
