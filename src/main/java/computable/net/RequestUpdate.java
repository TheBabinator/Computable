package computable.net;

import computable.Computable;
import computable.api.UpdateRequestable;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record RequestUpdate(BlockPos blockPos) implements CustomPacketPayload {
    public static final Type<RequestUpdate> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Computable.ID, "request_update"));

    public static final StreamCodec<ByteBuf, RequestUpdate> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            RequestUpdate::blockPos,
            RequestUpdate::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext payloadContext) {
        payloadContext.enqueueWork(() -> {
            Level level = payloadContext.player().level();
            if (level.getBlockEntity(blockPos) instanceof UpdateRequestable<?> updateRequestable) {
                ComputableNetworking.sendBlockPosUpdate(level, blockPos, updateRequestable.getUpdatePayload());
            }
        });
    }
}
