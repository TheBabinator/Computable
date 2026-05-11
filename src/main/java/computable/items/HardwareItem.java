package computable.items;

import computable.content.ComputableDataComponentTypes;
import computable.items.components.Hardware;
import net.minecraft.world.item.Item;

public class HardwareItem extends Item {
    public HardwareItem(Properties properties, Hardware hardware) {
        super(properties.component(ComputableDataComponentTypes.HARDWARE.get(), hardware));
    }
}
