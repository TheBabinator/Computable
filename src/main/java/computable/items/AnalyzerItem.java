package computable.items;

import computable.api.Inspectable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class AnalyzerItem extends Item {
    public AnalyzerItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null || !player.isShiftKeyDown()) {
            return super.useOn(context);
        }
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        if (level.getBlockEntity(blockPos) instanceof Inspectable inspectable) {
            if (level.isClientSide()) {
                return InteractionResult.SUCCESS;
            }
            List<Component> components = new ArrayList<>();
            inspectable.inspect(components);
            MutableComponent message = Component.translatable("computable.analyzer_header");
            for (Component component : components) {
                message.append("\n");
                message.append(component);
            }
            player.sendSystemMessage(message);
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }
}
