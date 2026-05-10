package computable.net;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ComputableNetworking {
    public static void register(IEventBus eventBus) {
        eventBus.addListener(ComputableNetworking::onRegisterPayloadHandlersEvent);
    }

    private static void onRegisterPayloadHandlersEvent(RegisterPayloadHandlersEvent registerPayloadHandlersEvent) {
        PayloadRegistrar payloadRegistrar = registerPayloadHandlersEvent.registrar("1");
        payloadRegistrar.playToServer(UpdateDataSlot.TYPE, UpdateDataSlot.STREAM_CODEC, UpdateDataSlot::handle);
        payloadRegistrar.playToServer(RequestUpdate.TYPE, RequestUpdate.STREAM_CODEC, RequestUpdate::handle);
        payloadRegistrar.playToClient(ComputerCaseUpdate.TYPE, ComputerCaseUpdate.STREAM_CODEC, ComputerCaseUpdate::handle);
    }

    public static void updateServerMenuDataSlot(int index, int value) {
        PacketDistributor.sendToServer(new UpdateDataSlot(index, value));
    }

    public static void requestBlockPosUpdate(BlockPos blockPos) {
        PacketDistributor.sendToServer(new RequestUpdate(blockPos));
    }

    public static void sendBlockPosUpdate(Level level, BlockPos blockPos, CustomPacketPayload payload) {
        if (level instanceof ServerLevel serverLevel) {
            PacketDistributor.sendToPlayersTrackingChunk(serverLevel, new ChunkPos(blockPos), payload);
        }
    }

    public static void sendBlockEntityUpdate(BlockEntity blockEntity, CustomPacketPayload payload) {
        sendBlockPosUpdate(blockEntity.getLevel(), blockEntity.getBlockPos(), payload);
    }
}
