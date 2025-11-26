package net.yiran.jcrj;

import dev.latvian.mods.kubejs.KubeJS;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.fml.ModList;

public class ClientHandle {
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(KeyMappingUtil.KEYMAPPING = new KeyMapping(
                "jcrj.copy.recipe.json",
                -1,
                "jei.key.category.dev.tools"
        ));
        event.register(KeyMappingUtil.KEYMAPPING2 = new KeyMapping(
                "jcrj.copy.item.id",
                -1,
                "jei.key.category.dev.tools"
        ));
        if (ModList.get().isLoaded(KubeJS.MOD_ID)) {
            event.register(KeyMappingUtil.KEYMAPPING3 = new KeyMapping(
                    "jcrj.show.item.info",
                    -1,
                    "jei.key.category.dev.tools"
            ));
        }
    }

}
