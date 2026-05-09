package computable.content;

import computable.Computable;
import computable.items.AddressibleItem;
import computable.items.AnalyserItem;
import computable.items.MotherboardItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ComputableItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Computable.ID);

    public static final DeferredHolder<Item, Item> TRANSISTOR = ITEMS.register("transistor", ingredient());
    public static final DeferredHolder<Item, Item> BASIC_CHIP = ITEMS.register("basic_chip", ingredient());
    public static final DeferredHolder<Item, Item> CLOCK = ITEMS.register("clock", ingredient());
    public static final DeferredHolder<Item, Item> ALU = ITEMS.register("alu", ingredient());

    public static final DeferredHolder<Item, Item> EEPROM = ITEMS.register("eeprom", AddressibleItem::new);
    public static final DeferredHolder<Item, Item> BASIC_CPU = ITEMS.register("basic_cpu", AddressibleItem::new);
    public static final DeferredHolder<Item, Item> MOTHERBOARD = ITEMS.register("motherboard", MotherboardItem::new);
    public static final DeferredHolder<Item, Item> BASIC_RAM = ITEMS.register("basic_ram", AddressibleItem::new);
    public static final DeferredHolder<Item, Item> BASIC_DRIVE = ITEMS.register("basic_drive", AddressibleItem::new);
    public static final DeferredHolder<Item, Item> ANALYSER = ITEMS.register("analyser", AnalyserItem::new);

    public static final DeferredHolder<Item, Item> BASIC_COMPUTER_CASE = ITEMS.register("basic_computer_case", block(ComputableBlocks.BASIC_COMPUTER_CASE));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    private static Supplier<Item> ingredient() {
        return () -> new Item(new Item.Properties());
    }

    private static Supplier<Item> block(DeferredHolder<Block, ? extends Block> block) {
        return () -> new BlockItem(block.get(), new Item.Properties());
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent eventBus) {
        eventBus.registerItem(Capabilities.ItemHandler.ITEM,
                ((itemStack, context) -> {
                    MotherboardItem item = (MotherboardItem) MOTHERBOARD.get();
                    return item.createItemHandler(itemStack);
                }),
                MOTHERBOARD.get()
                );
    }

}
