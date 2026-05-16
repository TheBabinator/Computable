package computable.content;

import computable.Computable;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ComputableSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, Computable.ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> COMPUTER_IDLE_LOW = sound("computer.idle_low");
    public static final DeferredHolder<SoundEvent, SoundEvent> COMPUTER_IDLE_HIGH = sound("computer.idle_high");
    public static final DeferredHolder<SoundEvent, SoundEvent> MOTHERBOARD_INSERT = sound("motherboard.insert");

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);

    }

    private static DeferredHolder<SoundEvent, SoundEvent> sound(String name) {
        ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(Computable.ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(resourceLocation));
    }
}
