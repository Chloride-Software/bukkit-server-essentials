package com.chloride.modutilities.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import com.chloride.modutilities.ModUtilitiesPlugin;
import com.chloride.modutilities.data.PlayerData;
import com.chloride.modutilities.data.PlayerDataManager;
import com.chloride.modutilities.modmode.ModModeManager;
import com.chloride.modutilities.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@CommandAlias("modmode|mod")
@CommandPermission("clmodutilities.modmode")
public class ModModeCommand extends BaseCommand {
    @Default
    public void modmode(Player player) {
        PlayerData data = PlayerDataManager.getPlayerData(player);
        if (data == null) return;

        data.setInModMode(!data.isInModMode());

        boolean modMode = data.isInModMode();
        player.sendMessage(modMode ? Messages.MOD_MODE_ENTER : Messages.MOD_MODE_EXIT);

        if (player.getGameMode() == GameMode.SURVIVAL) {
            player.setAllowFlight(modMode);
        }

        if (modMode) {
            ModModeManager.giveItems(player);
        }
        else {
            ModModeManager.resetItems(player);
        }
    }
}
