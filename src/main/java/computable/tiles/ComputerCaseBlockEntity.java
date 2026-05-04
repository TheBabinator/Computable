package computable.tiles;

import computable.blocks.ComputerCaseBlock;
import computable.content.ComputableBlockEntityTypes;
import computable.network.NetworkMember;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ComputerCaseBlockEntity extends BlockEntity {
    private final NetworkMember networkMember = new NetworkMember();

    public ComputerCaseBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ComputableBlockEntityTypes.COMPUTER_CASE.get(), blockPos, blockState);
    }

    public NetworkMember getNetworkMember() {
        return networkMember;
    }

    public void tick() {
        networkMember.getNetwork().getNetworkEnergy().use(10, false);
    }

    public static void ticker(Level level, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity) {
        if (blockEntity instanceof ComputerCaseBlockEntity computerCaseBlockEntity) {
            computerCaseBlockEntity.tick();
        }
    }
}
