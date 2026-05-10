package computable.client.tiles;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import computable.Computable;
import computable.tiles.ComputerCaseBlockEntity;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ComputerCaseBlockEntityRenderer implements BlockEntityRenderer<ComputerCaseBlockEntity> {
    public static final ResourceLocation POWER = ResourceLocation.fromNamespaceAndPath(Computable.ID, "textures/block/computer_case/power.png");
    public static final ResourceLocation INDICATOR = ResourceLocation.fromNamespaceAndPath(Computable.ID, "textures/block/computer_case/indicator.png");

    public ComputerCaseBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(ComputerCaseBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (!blockEntity.isRunning()) {
            return;
        }

        Direction direction = blockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);

        poseStack.pushPose();
        poseStack.rotateAround(direction.getRotation(), 0.5f, 0.5f, 0.5f);
        PoseStack.Pose pose = poseStack.last();
        poseStack.popPose();

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder power = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        power.addVertex(pose, 0f, 1.01f, 0f).setUv(0, 0f);
        power.addVertex(pose, 0f, 1.01f, 1f).setUv(0, 1f);
        power.addVertex(pose, 1f, 1.01f, 1f).setUv(1, 1f);
        power.addVertex(pose, 1f, 1.01f, 0f).setUv(1, 0f);
        RenderSystem.setShaderTexture(0, POWER);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.enableDepthTest();
        BufferUploader.drawWithShader(power.buildOrThrow());
        if (blockEntity.getLight()) {
            BufferBuilder indicator = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            indicator.addVertex(pose, 0f, 1.01f, 0f).setUv(0, 0f);
            indicator.addVertex(pose, 0f, 1.01f, 1f).setUv(0, 1f);
            indicator.addVertex(pose, 1f, 1.01f, 1f).setUv(1, 1f);
            indicator.addVertex(pose, 1f, 1.01f, 0f).setUv(1, 0f);
            RenderSystem.setShaderTexture(0, INDICATOR);
            BufferUploader.drawWithShader(indicator.buildOrThrow());
        }
    }
}
