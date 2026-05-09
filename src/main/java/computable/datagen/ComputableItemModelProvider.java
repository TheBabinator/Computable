package computable.datagen;

import computable.Computable;
import computable.content.ComputableItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ComputableItemModelProvider extends ItemModelProvider {
    public ComputableItemModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, Computable.ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (DeferredHolder<Item, ?> entry : ComputableItems.ITEMS.getEntries()) {
            Item item = entry.get();
            if (item instanceof BlockItem blockItem) {
                simpleBlockItem(blockItem.getBlock());
            } else {
                basicItem(item);
            }
        }
    }
}
