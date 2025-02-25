package com.kettle.nunchakus.render;

import java.util.UUID;
import java.util.WeakHashMap;

import net.minecraft.client.Minecraft;

public class LightningEffectManager {
    // Map storing lightning effects keyed by the entity's UUID.
    private static final WeakHashMap<UUID, LightningEffectData> effects = new WeakHashMap<>();
    
    public static void addEffect(UUID targetUUID, LightningEffectData data) {
        effects.put(targetUUID, data);
    }
    
    public static LightningEffectData getEffect(UUID targetUUID) {
        return effects.get(targetUUID);
    }
    
    public static void removeEffect(UUID targetUUID) {
        effects.remove(targetUUID);
    }
    
    // Optionally, a method to clean up expired effects.
    public static void cleanup() {
        long now = (int)Minecraft.getInstance().level.getGameTime();
        effects.entrySet().removeIf(entry -> now - entry.getValue().getTimestamp() > 40); // 1 second duration
    }
}