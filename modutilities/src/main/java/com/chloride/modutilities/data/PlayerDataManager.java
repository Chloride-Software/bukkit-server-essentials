package com.chloride.modutilities.data;

import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerDataManager {
    private final static Map<UUID, PlayerData> playerDataMap = new ConcurrentHashMap<>();

    public static void register(Player player) {
        if (player == null)
            throw new RuntimeException("Cannot register a null player. (PlayerDataManager)");

        UUID uuid = player.getUniqueId();
        if (playerDataMap.containsKey(uuid))
            throw new RuntimeException("Cannot register an already registered player. (PlayerDataManager)");

        playerDataMap.put(uuid, new PlayerData(player));
    }

    public static void unregister(Player player) {
        if (player == null)
            throw new RuntimeException("Cannot unregister a null player. (PlayerDataManager)");

        UUID uuid = player.getUniqueId();
        if (!playerDataMap.containsKey(uuid))
            throw new RuntimeException("Cannot unregister a player who isn't registered. (PlayerDataManager)");

        playerDataMap.remove(uuid);
    }

    public static PlayerData getPlayerData(UUID uuid) {
        if (uuid == null)
            throw new RuntimeException("Cannot retrieve player data using a null UUID. (PlayerDataManager)");

        return playerDataMap.get(uuid);
    }

    public static PlayerData getPlayerData(Player player) {
        if (player == null)
            throw new RuntimeException("Cannot retrieve player data using a null Player instance. (PlayerDataManager)");

        UUID uuid = player.getUniqueId();
        return playerDataMap.get(uuid);
    }
}
