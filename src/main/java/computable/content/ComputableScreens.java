package computable.content;

import computable.Computable;
import computable.gui.MotherboardScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = Computable.ID)
public class ComputableScreens {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ComputableMenus.MOTHERBOARD_MENU.get(), MotherboardScreen::new);
    }

}
