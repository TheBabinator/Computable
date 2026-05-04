package computable.content;

import computable.Computable;
import computable.blocks.ComputerCaseBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ComputableBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, Computable.ID);

    public static final DeferredHolder<Block, Block> BASIC_COMPUTER_CASE = BLOCKS.register("basic_computer_case",
            () -> new ComputerCaseBlock(BlockBehaviour.Properties.of()));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
