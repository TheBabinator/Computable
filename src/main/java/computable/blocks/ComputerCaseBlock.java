package computable.blocks;

import com.mojang.serialization.MapCodec;
import computable.api.ComputableBlockEntity;
import computable.api.ComputableEntityBlock;
import computable.api.Inspectable;
import computable.tiles.ComputerCaseBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import java.util.List;

public class ComputerCaseBlock extends HorizontalDirectionalBlock implements ComputableEntityBlock, Inspectable {
    public ComputerCaseBlock(Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return BlockBehaviour.simpleCodec(ComputerCaseBlock::new);
    }

    @Override
    public ComputableBlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ComputerCaseBlockEntity(blockPos, blockState);
    }

    @Override
    public boolean tickServer() {
        return true;
    }

    @Override
    public boolean tickClient() {
        return true;
    }

    @Override
    public void inspect(Level level, BlockPos blockPos, List<Component> components) {
        if (level.getBlockEntity(blockPos) instanceof ComputerCaseBlockEntity tile) {
            tile.getNetworkMember().inspect(components);
        }
    }
}
