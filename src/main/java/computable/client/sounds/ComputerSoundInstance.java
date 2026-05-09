package computable.client.sounds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ComputerSoundInstance extends AbstractTickableSoundInstance {
    private float basePitch = 1f;
    private float modulation = 0.02f;

    public ComputerSoundInstance(SoundEvent soundEvent, BlockPos blockPos) {
        super(soundEvent, SoundSource.BLOCKS, SoundInstance.createUnseededRandom());
        looping = true;
        Vec3 pos = blockPos.getCenter();
        x = pos.x;
        y = pos.y;
        z = pos.z;
    }

    @Override
    public void tick() {
        float scaledVolume = Math.max(volume, 1.0F) * sound.getAttenuationDistance();
        float range = !relative && attenuation != SoundInstance.Attenuation.NONE ? scaledVolume : Float.POSITIVE_INFINITY;
        WeighedSoundEvents soundEventAccessor = resolve(Minecraft.getInstance().getSoundManager());
        Minecraft.getInstance().gui.subtitleOverlay.onPlaySound(this, soundEventAccessor, range);
        pitch = basePitch * (1 + (float) random.nextGaussian() * modulation);
    }

    void setVolume(float volume) {
        this.volume = volume;
    }

    void setBasePitch(float basePitch) {
        this.basePitch = basePitch;
    }

    public void setModulation(float modulation) {
        this.modulation = modulation;
    }

    public void kill() {
        stop();
    }
}
