package net.yiran.jcrj;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;


@SuppressWarnings("removal")
@Mod(JEICopyRecipeJson.MODID)
public class JEICopyRecipeJson {
    public static final String MODID = "jei_copy_recipe_json";
    private static final Logger LOGGER = LogUtils.getLogger();
    public JEICopyRecipeJson() {
        if(FMLEnvironment.dist == Dist.CLIENT) {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientHandler::registerKeyMappings);
            MinecraftForge.EVENT_BUS.register(CopyItemHandler.class);
        }
    }
}
