package computable.client;

import computable.Computable;
import computable.client.gui.ComputableScreens;
import computable.client.tiles.ComputableBlockEntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@OnlyIn(Dist.CLIENT)
@Mod(value = Computable.ID, dist = Dist.CLIENT)
public class ComputableClient {
    public ComputableClient(IEventBus eventBus, ModContainer modContainer) {
        ComputableBlockEntityRenderers.register(eventBus);
        ComputableScreens.register(eventBus);

        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
