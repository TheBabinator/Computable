package computable.content;

import computable.Computable;
import computable.gui.ComputerCaseMenu;
import computable.gui.MotherboardMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ComputableMenus {
    public static final DeferredRegister<MenuType<?>> MENU = DeferredRegister.create(Registries.MENU, Computable.ID);

    public static final DeferredHolder<MenuType<?>, MenuType<MotherboardMenu>> MOTHERBOARD = MENU.register("motherboard",
            () -> new MenuType<>(MotherboardMenu::new, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<ComputerCaseMenu>> COMPUTER_CASE = MENU.register("computer_case",
            () -> new MenuType<>(ComputerCaseMenu::new, FeatureFlags.DEFAULT_FLAGS));

    public static void register(IEventBus eventBus) {
        MENU.register(eventBus);
    }

}
