package com.chloride.modutilities.listener.bukkit;

import com.chloride.modutilities.ModUtilitiesPlugin;
import com.chloride.modutilities.data.PlayerData;
import com.chloride.modutilities.data.PlayerDataManager;
import com.chloride.modutilities.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        ModUtilitiesPlugin plugin = ModUtilitiesPlugin.getInstance();

        Player player = event.getPlayer();
        PlayerData data = PlayerDataManager.getPlayerData(player);

        // if a vanished player rejoins, they will remain vanished
        boolean stillVanished = false;
        if (player.hasPermission("clmodutilities.vanish") && data != null && data.isVanished()) {
            player.sendMessage(Messages.VANISH_REJOIN);
            stillVanished = true;

            event.setJoinMessage(null);
        }

        // mod-mode disable upon rejoin
        if (player.hasPermission("clmodutilities.modmode") && data != null && data.isInModMode()) {
            player.performCommand("modmode");
        }

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            // re-hide them from other players
            if (stillVanished) {
                onlinePlayer.hidePlayer(plugin, player);
            }

            if (!onlinePlayer.hasPermission("clmodutilities.vanish")) continue;

            PlayerData onlineData = PlayerDataManager.getPlayerData(onlinePlayer);
            if (onlineData == null) continue;

            if (onlineData.isVanished()) {
                player.hidePlayer(plugin, onlinePlayer);
            }
        }

        // make sure they haven't already been registered since last join
        if (PlayerDataManager.getPlayerData(player) != null) return;
        PlayerDataManager.register(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerData data = PlayerDataManager.getPlayerData(player);

        // stop the logout message while vanished
        if (player.hasPermission("clmodutilities.vanish") && data != null && data.isVanished()) {
            event.setQuitMessage(null);
        }
    }
}
