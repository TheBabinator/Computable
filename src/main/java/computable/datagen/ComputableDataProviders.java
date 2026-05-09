package computable.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class ComputableDataProviders {
    public static void onGatherDataEvent(GatherDataEvent gatherDataEvent) {
        CompletableFuture<HolderLookup.Provider> registries = gatherDataEvent.getLookupProvider();
        DataGenerator dataGenerator = gatherDataEvent.getGenerator();
        PackOutput packOutput = dataGenerator.getPackOutput();
        ExistingFileHelper existingFileHelper = gatherDataEvent.getExistingFileHelper();
        dataGenerator.addProvider(gatherDataEvent.includeClient(), new ComputableItemModelProvider(packOutput, existingFileHelper));
        dataGenerator.addProvider(gatherDataEvent.includeServer(), new ComputableLootTableProvider(packOutput, registries));
    }
}
