package net.yiran.jcrj;

import com.google.gson.JsonParser;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.logging.LogUtils;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

@SuppressWarnings("removal")
@Mod(JEICopyRecipeJson.MODID)
public class JEICopyRecipeJson {
    public static final String MODID = "jei_copy_recipe_json";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static KeyMapping KEYMAPPING = new KeyMapping(
            "jcrj.copy.recipe.json",
            -1,
            "jei.key.category.dev.tools"
    );
    public static KeyMapping KEYMAPPING2 = new KeyMapping(
            "jcrj.copy.item.id",
            -1,
            "jei.key.category.dev.tools"
    );
    public static boolean isCopyJsonMode = false;

    public JEICopyRecipeJson() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerKeyMappings);
        MinecraftForge.EVENT_BUS.register(CopyItemHandler.class);
    }

    public void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(KEYMAPPING);
        event.register(KEYMAPPING2);
    }


}
