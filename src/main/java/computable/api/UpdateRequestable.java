package computable.api;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public interface UpdateRequestable<T extends CustomPacketPayload> {
    T getUpdatePayload();
    void clientUpdate(T payload);
}
