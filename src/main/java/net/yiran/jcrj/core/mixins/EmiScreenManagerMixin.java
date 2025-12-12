package net.yiran.jcrj.core.mixins;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.input.EmiBind;
import dev.emi.emi.screen.EmiScreenManager;
import mezz.jei.gui.input.IUserInputHandler;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.yiran.jcrj.KeyMappingUtil;
import net.yiran.jcrj.core.ICopyJsonHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.function.Function;

@Mixin(value = EmiScreenManager.class, remap = false)
public class EmiScreenManagerMixin {
    @Inject(method = "recipeInteraction", at = @At("HEAD"), cancellable = true)
    private static void recipeInteraction(EmiRecipe recipe, Function<EmiBind, Boolean> function, CallbackInfoReturnable<Boolean> cir) {
        if (recipe == null) {
            cir.setReturnValue(false);
        } else if (function.apply(toEmiBind(KeyMappingUtil.KEYMAPPING))) {
            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            if (server == null) {
                return;
            }
            ResourceLocation registryName = recipe.getId();
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (registryName != null) {
                ICopyJsonHandler.handler(server, minecraft, registryName);
                return;
            }
            if (player != null) {
                player.displayClientMessage(Component.translatable("jcrj.message.copy.recipe.copy.failure"), false);
            }
            cir.setReturnValue(true);
        }
    }

    private static EmiBind toEmiBind(KeyMapping keyMapping) {
        int modifier = switch (keyMapping.getKeyModifier()) {
            case SHIFT -> 4;
            case ALT -> 2;
            case CONTROL -> 1;
            default -> 0;
        };
        return new EmiBind(keyMapping.getName(), modifier, keyMapping.getKey().getValue());
    }
}
