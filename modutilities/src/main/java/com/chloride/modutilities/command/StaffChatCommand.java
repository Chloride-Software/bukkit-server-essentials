package com.chloride.modutilities.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.chloride.modutilities.ModUtilitiesPlugin;
import com.chloride.modutilities.data.PlayerData;
import com.chloride.modutilities.data.PlayerDataManager;
import com.chloride.modutilities.util.Messages;
import com.chloride.modutilities.util.chat.StaffChatUtil;
import org.bukkit.entity.Player;

@CommandAlias("staffchat|sc")
@CommandPermission("clmodutilities.staffchat")
public class StaffChatCommand extends BaseCommand {
    @Default
    public void staffchat(Player player, @Name(value = "message") @Optional String message) {
        PlayerData data = PlayerDataManager.getPlayerData(player);
        if (data == null) return;

        if (message != null) {
            String trimmedMessage = message.trim();

            StaffChatUtil.messageStaff(player, trimmedMessage, true);
            return;
        }

        data.setInStaffChat(!data.isInStaffChat());
        player.sendMessage(data.isInStaffChat() ? Messages.STAFF_CHAT_ENTER : Messages.STAFF_CHAT_EXIT);
    }
}
