package computable.net;

import computable.Computable;
import computable.tiles.ComputerCaseBlockEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ComputerCaseUpdate(BlockPos blockPos, boolean running, boolean light) implements CustomPacketPayload {
    public static final Type<ComputerCaseUpdate> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Computable.ID, "computer_case_update"));

    public static final StreamCodec<ByteBuf, ComputerCaseUpdate> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            ComputerCaseUpdate::blockPos,
            ByteBufCodecs.BOOL,
            ComputerCaseUpdate::running,
            ByteBufCodecs.BOOL,
            ComputerCaseUpdate::light,
            ComputerCaseUpdate::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext payloadContext) {
        payloadContext.enqueueWork(() -> {
           if (payloadContext.player().level().getBlockEntity(blockPos) instanceof ComputerCaseBlockEntity blockEntity) {
                blockEntity.clientUpdate(this);
           }
        });
    }
}
