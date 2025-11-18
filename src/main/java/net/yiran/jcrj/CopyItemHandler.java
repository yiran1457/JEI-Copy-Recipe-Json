package net.yiran.jcrj;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import static net.yiran.jcrj.JEICopyRecipeJson.KEYMAPPING2;

public class CopyItemHandler {
    public static ItemStack StackBuffer = ItemStack.EMPTY;
    public static int count = 0;
    @SubscribeEvent
    public static void onClientTick (TickEvent.ClientTickEvent event) {
        if(StackBuffer.isEmpty())return;
        if(count>0){
            count--;
        }else {
            StackBuffer = ItemStack.EMPTY;
        }
    }
    @SubscribeEvent
    public static void onTooltip (ItemTooltipEvent event) {
        if(event.getItemStack().isEmpty())return;
        StackBuffer = event.getItemStack();
        count=3;
    }
    @SubscribeEvent
    public static void onKeyInput (ScreenEvent.KeyPressed.Pre event) {
        if (KEYMAPPING2.isActiveAndMatches(InputConstants.getKey(event.getKeyCode(),event.getScanCode()))) {
            ItemStack stack = StackBuffer;
            if (stack.isEmpty()) return;
            String itemID =  ForgeRegistries.ITEMS.getKey(stack.getItem()).toString();
            String copyId = I18n.get("jcrj.copy.item.id.format",itemID);
            Minecraft minecraft = Minecraft.getInstance();
            Player player = minecraft.player;
            minecraft.keyboardHandler.setClipboard(copyId);
            player.displayClientMessage(Component.translatable("jcrj.message.copy.item.id.success", itemID), false);

        }
    }
}
