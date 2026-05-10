package computable.client.gui;

import computable.Computable;
import computable.gui.ComputerCaseMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ComputerCaseScreen extends ComputableContainerScreen<ComputerCaseMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Computable.ID, "textures/gui/computer_case.png");
    private Button button;

    public ComputerCaseScreen(ComputerCaseMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        imageWidth = 176;
        imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();
        button = Button.builder(Component.empty(), this::onPress)
                .pos(leftPos + 25, topPos + 34)
                .size(18, 18)
                .build();
        addWidget(button);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);
        boolean hover = button.isMouseOver(mouseX, mouseY);
        boolean active = menu.getButton();
        guiGraphics.blit(TEXTURE, leftPos + 25, topPos + 34, 176, hover ^ active ? 18 : 0, 18, 18);
        guiGraphics.blit(TEXTURE, leftPos + 25, topPos + 34, 194, active ? 18 : 0, 18, 18);
    }

    private void onPress(Button button) {
        menu.setButton(!menu.getButton());
    }
}
