package net.yiran.jcrj;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

public class KeyMappingUtil {
    public static boolean isModifierPressed(KeyMapping keyMapping) {
        return keyMapping.getKeyModifier().isActive(keyMapping.getKeyConflictContext());
    }

    public static boolean isCommonKeyPressed(KeyMapping keyMapping) {
        return InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), keyMapping.getKey().getValue());
    }

    public static boolean isKeyMappingPressed(KeyMapping keyMapping) {
        return isModifierPressed(keyMapping) && isCommonKeyPressed(keyMapping);
    }
}
