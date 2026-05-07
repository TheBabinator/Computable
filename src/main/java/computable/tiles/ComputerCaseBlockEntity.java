package computable.tiles;

import computable.api.ComputableBlockEntity;
import computable.client.sounds.ComputerSoundInstance;
import computable.content.ComputableBlockEntityTypes;
import computable.content.ComputableSoundEvents;
import computable.network.NetworkMember;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ComputerCaseBlockEntity extends ComputableBlockEntity {
    private NetworkMember networkMember;
    private ComputerSoundInstance idleLow;
    private ComputerSoundInstance idleHigh;

    public ComputerCaseBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ComputableBlockEntityTypes.COMPUTER_CASE.get(), blockPos, blockState);
    }

    public NetworkMember getNetworkMember() {
        return networkMember;
    }

    @Override
    public void clearRemoved() {
        super.clearRemoved();
        Level level = getLevel();
        if (level == null) {
            return;
        }
        if (level.isClientSide()) {
            idleLow = new ComputerSoundInstance(ComputableSoundEvents.COMPUTER_IDLE_LOW.get(), getBlockPos());
            // idleHigh = new ComputerSoundInstance(ComputableSoundEvents.COMPUTER_IDLE_HIGH.get(), getBlockPos());
            Minecraft.getInstance().getSoundManager().queueTickingSound(idleLow);
            // Minecraft.getInstance().getSoundManager().queueTickingSound(idleHigh);
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
        // todo: noise
    }
}
