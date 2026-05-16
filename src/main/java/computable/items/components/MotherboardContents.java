package computable.items.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import computable.content.ComputableDataComponentTypes;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record MotherboardContents (
        ItemStack cpu,
        ItemStack ram0, ItemStack ram1,
        ItemStack drive0, ItemStack drive1,
        ItemStack card0, ItemStack card1, ItemStack card2,
        ItemStack eeprom
) {


    public MotherboardContents(List<ItemStack> itemList) {
        this(itemList.getFirst(), itemList.get(1), itemList.get(2), itemList.get(3), itemList.get(4), itemList.get(5), itemList.get(6), itemList.get(7), itemList.get(8));
    }

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

    public int findNextAvailableSlot(ItemStack stack) {
        for (int i = 0; i <= 9; i++ ) {
            if (validateSlot(i, stack)) {
                if (getItemFromSlot(i) == ItemStack.EMPTY) {
                    return i;
                }
            }
        }
        return -1;
    }

    public boolean validateSlot(int slot, ItemStack stack) {
        Hardware hardware = stack.get(ComputableDataComponentTypes.HARDWARE);
        if (hardware == null) {
            return false;
        }
        switch (slot) {
            case 0 -> {
                if (hardware == Hardware.CPU) {
                    return true;
                }
            }
            case 1, 2 -> {
                if (hardware == Hardware.MEMORY) {
                    return true;
                }
            }
            case 3, 4 -> {
                if (hardware == Hardware.DRIVE) {
                    return true;
                }
            }
            case 5, 6, 7 -> {
                if (hardware == Hardware.CARD) {
                    return true;
                }
            }
            case 8 -> {
                if (hardware == Hardware.EEPROM) {
                    return true;
                }
            }
        }
        return false;
    }

    // temporary
    public MotherboardContents addItem(ItemStack stack, int slot) {
        List<ItemStack> replaced = new java.util.ArrayList<>(List.of(cpu, ram0, ram1, drive0, drive1, card0, card1, card2, eeprom));
        replaced.set(slot, stack);
        return new MotherboardContents(replaced);
    }

    // temporary
    public MotherboardContents removeItem(int slot) {
        List<ItemStack> kept = new java.util.ArrayList<>(List.of(cpu, ram0, ram1, drive0, drive1, card0, card1, card2, eeprom));
        kept.set(slot, ItemStack.EMPTY);
        return new MotherboardContents(kept);
    }


    public static final Codec<MotherboardContents> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    ItemStack.OPTIONAL_CODEC.fieldOf("cpu").forGetter(MotherboardContents::cpu),

                    ItemStack.OPTIONAL_CODEC.fieldOf("ram0").forGetter(MotherboardContents::ram0),
                    ItemStack.OPTIONAL_CODEC.fieldOf("ram1").forGetter(MotherboardContents::ram1),

                    ItemStack.OPTIONAL_CODEC.fieldOf("drive0").forGetter(MotherboardContents::drive0),
                    ItemStack.OPTIONAL_CODEC.fieldOf("drive1").forGetter(MotherboardContents::drive1),

                    ItemStack.OPTIONAL_CODEC.fieldOf("card0").forGetter(MotherboardContents::card0),
                    ItemStack.OPTIONAL_CODEC.fieldOf("card1").forGetter(MotherboardContents::card1),
                    ItemStack.OPTIONAL_CODEC.fieldOf("card2").forGetter(MotherboardContents::card2),

                    ItemStack.OPTIONAL_CODEC.fieldOf("eeprom").forGetter(MotherboardContents::eeprom)

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

            ItemStack.OPTIONAL_STREAM_CODEC.encode((RegistryFriendlyByteBuf) byteBuf, motherboardContents.ram0());
            ItemStack.OPTIONAL_STREAM_CODEC.encode((RegistryFriendlyByteBuf) byteBuf, motherboardContents.ram1());

            ItemStack.OPTIONAL_STREAM_CODEC.encode((RegistryFriendlyByteBuf) byteBuf, motherboardContents.drive0());
            ItemStack.OPTIONAL_STREAM_CODEC.encode((RegistryFriendlyByteBuf) byteBuf, motherboardContents.drive1());

            ItemStack.OPTIONAL_STREAM_CODEC.encode((RegistryFriendlyByteBuf) byteBuf, motherboardContents.card0());
            ItemStack.OPTIONAL_STREAM_CODEC.encode((RegistryFriendlyByteBuf) byteBuf, motherboardContents.card1());
            ItemStack.OPTIONAL_STREAM_CODEC.encode((RegistryFriendlyByteBuf) byteBuf, motherboardContents.card2());

            ItemStack.OPTIONAL_STREAM_CODEC.encode((RegistryFriendlyByteBuf) byteBuf, motherboardContents.eeprom());
        }
    };
}