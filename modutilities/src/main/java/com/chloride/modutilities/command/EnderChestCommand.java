package com.chloride.modutilities.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Name;
import com.chloride.modutilities.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("enderchest|echest")
@CommandPermission("clmodutilities.enderchest")
public class EnderChestCommand extends BaseCommand {
    @Default
    public void enderChest(Player player, @Name(value = "target") String target) {
        Player targetPlayer = Bukkit.getPlayer(target);
        if (targetPlayer == null) {
            player.sendMessage(Messages.INVALID_PLAYER);
            return;
        }

        player.openInventory(targetPlayer.getEnderChest());
    }
}
