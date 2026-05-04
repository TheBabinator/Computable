package computable.items;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import computable.content.ComputableDataComponentTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.UUID;

public class AddressibleItem extends Item {
    public AddressibleItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> components, TooltipFlag tooltipFlag) {
        MutableComponent component;
        UUID address = itemStack.get(ComputableDataComponentTypes.ADDRESS);
        if (address != null) {
            component = Component.translatable("computable.address", address.toString());
        } else {
            component = Component.translatable("computable.address.unset");
        }
        components.add(component.withStyle(ChatFormatting.DARK_GRAY));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!player.isShiftKeyDown()) {
            return super.use(level, player, interactionHand);
        }
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (!level.isClientSide()) {
            itemStack.set(ComputableDataComponentTypes.ADDRESS, UUID.randomUUID());
        }
        return InteractionResultHolder.success(itemStack);
    }
}
