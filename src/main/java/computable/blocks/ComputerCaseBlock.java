package computable.blocks;

import com.mojang.serialization.MapCodec;
import computable.api.ComputableBlockEntity;
import computable.api.ComputableEntityBlock;
import computable.api.Inspectable;
import computable.gui.ComputerCaseMenu;
import computable.tiles.ComputerCaseBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class ComputerCaseBlock extends HorizontalDirectionalBlock implements ComputableEntityBlock {
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
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (player instanceof ServerPlayer serverPlayer) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof ComputerCaseBlockEntity computerCaseBlockEntity) {
                serverPlayer.openMenu(new SimpleMenuProvider(
                        (containerId, playerInventory, player1) -> new ComputerCaseMenu(containerId, playerInventory, computerCaseBlockEntity),
                        getName()
                ));
            }
        }
        return InteractionResult.SUCCESS;
    }
}
