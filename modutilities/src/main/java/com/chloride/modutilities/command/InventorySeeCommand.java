package com.chloride.modutilities.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.chloride.modutilities.data.PlayerData;
import com.chloride.modutilities.data.PlayerDataManager;
import com.chloride.modutilities.util.Messages;
import com.chloride.modutilities.util.chat.StaffChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("inventorysee|invsee")
@CommandPermission("clmodutilities.inventorysee")
public class InventorySeeCommand extends BaseCommand {
    @Default
    public void inventorySee(Player player, @Name(value = "target") @Single String target) {
        Player targetPlayer = Bukkit.getPlayer(target);
        if (targetPlayer == null) {
            player.sendMessage(Messages.INVALID_PLAYER);
            return;
        }

        player.openInventory(targetPlayer.getInventory());
    }
}
