package computable.api;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public interface ComputableEntityBlock extends EntityBlock {
    default boolean tickServer() {
        return false;
    }

    default boolean tickClient() {
        return false;
    }

    @Override
    ComputableBlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState);

    @Override
    default <U extends BlockEntity> BlockEntityTicker<U> getTicker(Level level, BlockState blockState, BlockEntityType<U> blockEntityType) {
        if (level.isClientSide()) {
            if (tickClient()) {
                return ComputableEntityBlock::clientTicker;
            } else {
                return null;
            }
        } else {
            if (tickServer()) {
                return ComputableEntityBlock::serverTicker;
            } else {
                return null;
            }
        }
    }

    static void serverTicker(Level level, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity) {
        if (blockEntity instanceof ComputableBlockEntity computableBlockEntity) {
            computableBlockEntity.serverTick();
        }
    }

    static void clientTicker(Level level, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity) {
        if (blockEntity instanceof ComputableBlockEntity computableBlockEntity) {
            computableBlockEntity.clientTick();
        }
    }
}
