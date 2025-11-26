package net.yiran.jcrj;

import dev.architectury.extensions.injected.InjectedBucketItemExtension;
import dev.latvian.mods.kubejs.core.BlockKJS;
import dev.latvian.mods.kubejs.core.IngredientKJS;
import dev.latvian.mods.kubejs.core.ItemStackKJS;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.kubejs.platform.IngredientPlatformHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;

public class KubeJSCompat {
    public static void showItemInfo(Player player, ItemStack stack) {
        player.sendSystemMessage(Component.literal("Item in screen:"));
        var holder = stack.getItemHolder();

        player.sendSystemMessage(copy(ItemStackJS.toItemString(stack), ChatFormatting.GREEN, "Item ID"));

        var itemTags = holder.tags().toList();
        for (var tag : itemTags) {
            var id = "'#%s'".formatted(tag.location());
            var size = BuiltInRegistries.ITEM.getTag(tag).map(HolderSet::size).orElse(0);
            player.sendSystemMessage(copy(id, ChatFormatting.YELLOW, "Item Tag [" + size + " items]"));
        }

        player.sendSystemMessage(copy("'@" + ((ItemStackKJS) (Object) stack).kjs$getMod() + "'", ChatFormatting.AQUA, "Mod [" + ((IngredientKJS) IngredientPlatformHelper.get().mod(((ItemStackKJS) (Object) stack).kjs$getMod())).kjs$getStacks().size() + " items]"));

        if (stack.getItem() instanceof BlockItem blockItem) {
            player.sendSystemMessage(Component.literal("Block:"));
            var block = blockItem.getBlock();
            var blockHolder = block.builtInRegistryHolder();

            player.sendSystemMessage(copy(((BlockKJS) block).kjs$getId(), ChatFormatting.GREEN, "Block ID"));

            var blockTags = blockHolder.tags().toList();
            for (var tag : blockTags) {
                var id = "'#%s'".formatted(tag.location());
                var size = BuiltInRegistries.BLOCK.getTag(tag).map(HolderSet::size).orElse(0);
                player.sendSystemMessage(copy(id, ChatFormatting.YELLOW, "Block Tag [" + size + " items]"));
            }
        }

        if (stack.getItem() instanceof BucketItem bucket) {
            player.sendSystemMessage(Component.literal("Fluid:"));
            var fluid = ((InjectedBucketItemExtension) bucket).arch$getFluid();
            var fluidHolder = fluid.builtInRegistryHolder();

            player.sendSystemMessage(copy(fluidHolder.key().location().toString(), ChatFormatting.GREEN, "Fluid ID"));

            var fluidTags = fluidHolder.tags().toList();
            for (var tag : fluidTags) {
                var id = "'#%s'".formatted(tag.location());
                var size = BuiltInRegistries.FLUID.getTag(tag).map(HolderSet::size).orElse(0);
                player.sendSystemMessage(copy(id, ChatFormatting.YELLOW, "Fluid Tag [" + size + " items]"));
            }
        }
    }


    private static Component copy(String s, ChatFormatting col, String info) {
        return copy(Component.literal(s).withStyle(col), info);
    }

    private static Component copy(Component c, String info) {
        return Component.literal("- ")
                .withStyle(ChatFormatting.GRAY)
                .withStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, c.getString())))
                .withStyle(Style.EMPTY.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal(info + " (Click to copy)"))))
                .append(c);
    }


}
