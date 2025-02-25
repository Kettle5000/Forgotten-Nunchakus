package com.kettle.nunchakus;

import net.minecraft.world.entity.player.Player;

import java.util.UUID;
import java.util.WeakHashMap;

public class LeftClickStateManager {
    private static final WeakHashMap<UUID, Boolean> leftClickStates = new WeakHashMap<>();

    public static void set(Player player, boolean state) {
        leftClickStates.put(player.getUUID(), state);
    }

    public static boolean isLeftClickHeld(Player player) {
        return leftClickStates.getOrDefault(player.getUUID(), false);
    }

    // Optionally, add cleanup methods when players disconnect.
}
