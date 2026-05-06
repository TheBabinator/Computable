package computable.content;

import computable.Computable;
import computable.gui.MotherboardMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ComputableMenus {
    public static final DeferredRegister<MenuType<?>> MENU = DeferredRegister.create(Registries.MENU, Computable.ID);
    public static final Supplier<MenuType<MotherboardMenu>> MOTHERBOARD_MENU = MENU.register("motherboard_menu",() -> new MenuType<>(MotherboardMenu::new, FeatureFlags.DEFAULT_FLAGS));

    public static void register(IEventBus eventBus) {
        MENU.register(eventBus);
    }

}
