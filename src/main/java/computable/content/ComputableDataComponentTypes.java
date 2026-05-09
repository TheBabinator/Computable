package computable.content;

import computable.Computable;
import computable.items.components.MotherboardContents;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.UUID;

public class ComputableDataComponentTypes {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Computable.ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<UUID>> ADDRESS = DATA_COMPONENT_TYPES.register("address",
            () -> DataComponentType.<UUID>builder()
                    .persistent(UUIDUtil.CODEC)
                    .networkSynchronized(UUIDUtil.STREAM_CODEC)
                    .build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<MotherboardContents>> MOTHERBOARD_CONTENTS = DATA_COMPONENT_TYPES.register("motherboard_contents",
            () -> DataComponentType.<MotherboardContents>builder()
                    .persistent(MotherboardContents.CODEC)
                    .networkSynchronized(MotherboardContents.STREAM_CODEC)
                    .build());


    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
