package com.chloride.modutilities.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import com.chloride.modutilities.ModUtilitiesPlugin;
import com.chloride.modutilities.data.PlayerData;
import com.chloride.modutilities.data.PlayerDataManager;
import com.chloride.modutilities.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@CommandAlias("vanish|v")
@CommandPermission("clmodutilities.vanish")
public class VanishCommand extends BaseCommand {
    @Default
    public void vanish(Player player) {
        ModUtilitiesPlugin plugin = ModUtilitiesPlugin.getInstance();

        PlayerData data = PlayerDataManager.getPlayerData(player);
        if (data == null) return;

        data.setVanished(!data.isVanished());

        boolean vanished = data.isVanished();
        player.sendMessage(vanished ? Messages.VANISH_ENTER : Messages.VANISH_EXIT);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.getUniqueId() == player.getUniqueId()) continue;

            if (vanished) {
                onlinePlayer.hidePlayer(plugin, player);
            } else {
                onlinePlayer.showPlayer(plugin, player);
            }
        }
    }
}
