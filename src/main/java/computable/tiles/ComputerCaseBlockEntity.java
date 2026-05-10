package computable.tiles;

import computable.api.ComputableBlockEntity;
import computable.api.Inspectable;
import computable.api.UpdateRequestable;
import computable.client.sounds.ComputerSoundInstance;
import computable.content.ComputableBlockEntityTypes;
import computable.content.ComputableSoundEvents;
import computable.net.ComputableNetworking;
import computable.net.ComputerCaseUpdate;
import computable.grid.NetworkMember;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class ComputerCaseBlockEntity extends ComputableBlockEntity implements Inspectable, UpdateRequestable<ComputerCaseUpdate> {
    private NetworkMember networkMember;
    private ComputerSoundInstance idleLow;
    private ComputerSoundInstance idleHigh;
    private boolean running = false;
    private boolean light = true;

    public ComputerCaseBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ComputableBlockEntityTypes.COMPUTER_CASE.get(), blockPos, blockState);
    }

    public NetworkMember getNetworkMember() {
        return networkMember;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean getLight() {
        return light;
    }

    public void start() {
        running = true;
        ComputableNetworking.sendBlockEntityUpdate(this, getUpdatePayload());
    }

    public void stop() {
        running = false;
        ComputableNetworking.sendBlockEntityUpdate(this, getUpdatePayload());
    }

    @Override
    public void clearRemoved() {
        super.clearRemoved();
        Level level = getLevel();
        if (level == null) {
            return;
        }
        if (level.isClientSide()) {
            ComputableNetworking.requestBlockPosUpdate(getBlockPos());
            return;
        }
        networkMember = new NetworkMember();
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (idleLow != null) {
            idleLow.kill();
        }
        if (idleHigh != null) {
            idleHigh.kill();
        }
    }

    @Override
    public void serverTick() {
        networkMember.getNetwork().getNetworkEnergy().use(10, false);
    }

    @Override
    public void clientTick() {

    }

    @Override
    public void inspect(List<Component> components) {
        networkMember.inspect(components);
    }

    @Override
    public ComputerCaseUpdate getUpdatePayload() {
        return new ComputerCaseUpdate(getBlockPos(), running, light);
    }

    @Override
    public void clientUpdate(ComputerCaseUpdate payload) {
        if (payload.running()) {
            if (!running) {
                running = true;
                idleLow = new ComputerSoundInstance(ComputableSoundEvents.COMPUTER_IDLE_LOW.get(), getBlockPos());
                Minecraft.getInstance().getSoundManager().queueTickingSound(idleLow);
            }
        } else {
            if (running) {
                running = false;
                idleLow.kill();
                idleLow = null;
            }
        }
        light = payload.light();
    }
}
