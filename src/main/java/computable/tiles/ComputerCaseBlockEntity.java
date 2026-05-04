package computable.tiles;

import computable.content.ComputableBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ComputerCaseBlockEntity extends BlockEntity {
    public ComputerCaseBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ComputableBlockEntityTypes.COMPUTER_CASE.get(), blockPos, blockState);
    }
}
