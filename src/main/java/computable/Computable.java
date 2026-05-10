package computable;

import computable.content.*;
import computable.datagen.ComputableDataProviders;
import computable.content.ComputableSoundEvents;
import computable.net.ComputableNetworking;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(Computable.ID)
public class Computable {
    public static final String ID = "computable";

    public Computable(IEventBus eventBus, ModContainer modContainer) {
        ComputableItems.register(eventBus);
        ComputableBlocks.register(eventBus);
        ComputableBlockEntityTypes.register(eventBus);
        ComputableCreativeModeTabs.register(eventBus);
        ComputableDataComponentTypes.register(eventBus);
        ComputableMenus.register(eventBus);
        ComputableSoundEvents.register(eventBus);
        ComputableNetworking.register(eventBus);

        eventBus.addListener(ComputableDataProviders::onGatherDataEvent);
        modContainer.registerConfig(ModConfig.Type.COMMON, ComputableConfig.SPEC);
    }
}
