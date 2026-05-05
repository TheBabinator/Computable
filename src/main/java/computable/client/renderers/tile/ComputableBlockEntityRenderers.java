package computable.client.renderers.tile;

import computable.content.ComputableBlockEntityTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class ComputableBlockEntityRenderers {
    public static void register(IEventBus eventBus) {
        eventBus.addListener(ComputableBlockEntityRenderers::onRegisterRenderers);
    }

    private static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers registerRenderers) {
        registerRenderers.registerBlockEntityRenderer(
                ComputableBlockEntityTypes.COMPUTER_CASE.get(),
                ComputerCaseBlockEntityRenderer::new);
    }
}
