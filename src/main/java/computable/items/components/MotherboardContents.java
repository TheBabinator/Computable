package computable.items.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import computable.Computable;
import computable.content.ComputableItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public record MotherboardContents (
        ItemStack cpu,
        ItemStack eeprom,
        ItemStack drive0, ItemStack drive1,
        ItemStack ram0, ItemStack ram1,
        ItemStack card0, ItemStack card1, ItemStack card2
) {

    public ItemStack getItemFromSlot(int slot) {
        switch (slot) {
            case 0 -> {
                return cpu;
            }
            case 1 -> {
                return ram0;
            }
            case 2 -> {
                return ram1;
            }
            case 3 -> {
                return drive0;
            }
            case 4 -> {
                return drive1;
            }
            case 5 -> {
                return card0;
            }
            case 6 -> {
                return card1;
            }
            case 7 -> {
                return card2;
            }
            case 8 -> {
                return eeprom;
            }
        }
        return ItemStack.EMPTY;
    }

    // temporary
    public MotherboardContents addItem(ItemStack stack) {
        List<ItemStack> replaced = new java.util.ArrayList<>(List.of());
        if (stack.is(ComputableItems.BASIC_CPU)) {
            replaced.addFirst(stack);
        }
        if (stack.is(Items.AIR)) {
            replaced.add(ItemStack.EMPTY);
        }
        return new MotherboardContents(replaced.getFirst(), ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY);
    }

    // temporary
    public MotherboardContents removeItem(ItemStack stack) {
        List<ItemStack> kept = new java.util.ArrayList<>(List.of(cpu, eeprom, drive0, drive1, ram0, ram1, card0, card1, card2));
        if (stack.is(ComputableItems.BASIC_CPU)) {
            kept.addFirst(ItemStack.EMPTY);
        }
        return new MotherboardContents(kept.getFirst(), kept.get(1), kept.get(2), kept.get(3), kept.get(4), kept.get(5), kept.get(6), kept.get(7), kept.get(8));
    }


    public static final Codec<MotherboardContents> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    ItemStack.OPTIONAL_CODEC.fieldOf("cpu").forGetter(MotherboardContents::cpu),
                    ItemStack.OPTIONAL_CODEC.fieldOf("eeprom").forGetter(MotherboardContents::eeprom),

                    ItemStack.OPTIONAL_CODEC.fieldOf("drive0").forGetter(MotherboardContents::drive0),
                    ItemStack.OPTIONAL_CODEC.fieldOf("drive1").forGetter(MotherboardContents::drive1),

                    ItemStack.OPTIONAL_CODEC.fieldOf("ram0").forGetter(MotherboardContents::ram0),
                    ItemStack.OPTIONAL_CODEC.fieldOf("ram1").forGetter(MotherboardContents::ram1),

                    ItemStack.OPTIONAL_CODEC.fieldOf("card0").forGetter(MotherboardContents::card0),
                    ItemStack.OPTIONAL_CODEC.fieldOf("card1").forGetter(MotherboardContents::card1),
                    ItemStack.OPTIONAL_CODEC.fieldOf("card2").forGetter(MotherboardContents::card2)
            ).apply(instance, MotherboardContents::new));

    public static final StreamCodec<ByteBuf, MotherboardContents> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public MotherboardContents decode(ByteBuf byteBuf) {
            return new MotherboardContents(
                    ItemStack.OPTIONAL_STREAM_CODEC.decode((RegistryFriendlyByteBuf) byteBuf),
                    ItemStack.OPTIONAL_STREAM_CODEC.decode((RegistryFriendlyByteBuf) byteBuf),
                    ItemStack.OPTIONAL_STREAM_CODEC.decode((RegistryFriendlyByteBuf) byteBuf),
                    ItemStack.OPTIONAL_STREAM_CODEC.decode((RegistryFriendlyByteBuf) byteBuf),
                    ItemStack.OPTIONAL_STREAM_CODEC.decode((RegistryFriendlyByteBuf) byteBuf),
                    ItemStack.OPTIONAL_STREAM_CODEC.decode((RegistryFriendlyByteBuf) byteBuf),
                    ItemStack.OPTIONAL_STREAM_CODEC.decode((RegistryFriendlyByteBuf) byteBuf),
                    ItemStack.OPTIONAL_STREAM_CODEC.decode((RegistryFriendlyByteBuf) byteBuf),
                    ItemStack.OPTIONAL_STREAM_CODEC.decode((RegistryFriendlyByteBuf) byteBuf)
            );
        }

        @Override
        public void encode(ByteBuf byteBuf, MotherboardContents motherboardContents) {
            ItemStack.OPTIONAL_STREAM_CODEC.encode((RegistryFriendlyByteBuf) byteBuf, motherboardContents.cpu());
            ItemStack.OPTIONAL_STREAM_CODEC.encode((RegistryFriendlyByteBuf) byteBuf, motherboardContents.eeprom());

            ItemStack.OPTIONAL_STREAM_CODEC.encode((RegistryFriendlyByteBuf) byteBuf, motherboardContents.drive0());
            ItemStack.OPTIONAL_STREAM_CODEC.encode((RegistryFriendlyByteBuf) byteBuf, motherboardContents.drive1());

            ItemStack.OPTIONAL_STREAM_CODEC.encode((RegistryFriendlyByteBuf) byteBuf, motherboardContents.ram0());
            ItemStack.OPTIONAL_STREAM_CODEC.encode((RegistryFriendlyByteBuf) byteBuf, motherboardContents.ram1());

            ItemStack.OPTIONAL_STREAM_CODEC.encode((RegistryFriendlyByteBuf) byteBuf, motherboardContents.card0());
            ItemStack.OPTIONAL_STREAM_CODEC.encode((RegistryFriendlyByteBuf) byteBuf, motherboardContents.card1());
            ItemStack.OPTIONAL_STREAM_CODEC.encode((RegistryFriendlyByteBuf) byteBuf, motherboardContents.card2());
        }
    };
}