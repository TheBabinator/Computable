package computable.client.gui;

import computable.content.ComputableMenus;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@OnlyIn(Dist.CLIENT)
public class ComputableScreens {
    public static void register(IEventBus eventBus) {
        eventBus.addListener(ComputableScreens::onRegisterMenuScreensEvent);
    }

    public static void onRegisterMenuScreensEvent(RegisterMenuScreensEvent event) {
        event.register(ComputableMenus.MOTHERBOARD_MENU.get(), MotherboardScreen::new);
    }
}
