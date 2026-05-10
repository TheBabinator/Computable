package computable.net;

import computable.Computable;
import computable.gui.ComputableContainerMenu;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record UpdateDataSlot(int index, int value) implements CustomPacketPayload {
    public static final Type<UpdateDataSlot> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Computable.ID, "update_data_slot"));

    public static final StreamCodec<ByteBuf, UpdateDataSlot> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            UpdateDataSlot::index,
            ByteBufCodecs.INT,
            UpdateDataSlot::value,
            UpdateDataSlot::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext payloadContext) {
        payloadContext.enqueueWork(() -> {
            if (payloadContext.player().containerMenu instanceof ComputableContainerMenu computableContainerMenu) {
                computableContainerMenu.setData(index, value);
            }
        });
    }
}
