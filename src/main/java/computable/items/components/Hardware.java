package computable.items.components;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import java.util.function.IntFunction;

public enum Hardware implements StringRepresentable {

    MOTHERBOARD("motherboard"),
    CPU("cpu"),
    MEMORY("memory"),
    CARD("card"),
    EEPROM("eeprom"),
    DRIVE("drive")
    ;

    private final String name;

    public static final Codec<Hardware> CODEC = Codec.lazyInitialized(() ->
            StringRepresentable.fromEnum(Hardware::values)
    );

    public static final IntFunction<Hardware> BY_ID = ByIdMap.continuous(
            Hardware::ordinal,
            Hardware.values(),
            ByIdMap.OutOfBoundsStrategy.ZERO
    );

    public static final StreamCodec<ByteBuf, Hardware> STREAM_CODEC =
            ByteBufCodecs.idMapper(BY_ID, Hardware::ordinal);

    Hardware(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
