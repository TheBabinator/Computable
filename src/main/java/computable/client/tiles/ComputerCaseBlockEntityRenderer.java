package computable.client.tiles;

import com.mojang.blaze3d.vertex.*;
import computable.tiles.ComputerCaseBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ComputerCaseBlockEntityRenderer implements BlockEntityRenderer<ComputerCaseBlockEntity> {
    public ComputerCaseBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(ComputerCaseBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();

        PoseStack.Pose pose = poseStack.last();
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.addVertex(pose, 0, 0, 0).setUv(0, 0);
        bufferBuilder.addVertex(pose, 1, 0, 0).setUv(1, 0);
        bufferBuilder.addVertex(pose, 1, 1, 0).setUv(1, 1);
        bufferBuilder.addVertex(pose, 0, 1, 0).setUv(0, 1);
        BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());

        poseStack.popPose();
    }
}
