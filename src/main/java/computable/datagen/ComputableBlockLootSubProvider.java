package computable.datagen;

import computable.content.ComputableBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Set;

public class ComputableBlockLootSubProvider extends BlockLootSubProvider {
    protected ComputableBlockLootSubProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ComputableBlocks.BLOCKS.getEntries()
                .stream()
                .map(block -> (Block) block.get())
                .toList();
    }

    @Override
    protected void generate() {
        for (DeferredHolder<Block, ?> entry : ComputableBlocks.BLOCKS.getEntries()) {
            dropSelf(entry.get());
        }
    }
}
