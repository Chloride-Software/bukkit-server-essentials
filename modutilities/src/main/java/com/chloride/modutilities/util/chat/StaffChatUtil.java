package com.chloride.modutilities.util.chat;

import com.chloride.modutilities.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class StaffChatUtil {
    private StaffChatUtil() {
        throw new RuntimeException("Cannot instantiate a utility class.");
    }

    public static void messageStaff(Player player, String message, boolean staffChat) {
        if (player == null) throw new RuntimeException("Cannot message staff using a null Player. (StaffChatUtil)");

        String formattedMessage = message;
        if (staffChat) {
            formattedMessage = ChatColor.translateAlternateColorCodes('&', Messages.STAFF_CHAT_FORMAT);
            formattedMessage = String.format(formattedMessage, player.getName(), message);
        }

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!onlinePlayer.hasPermission("clmodutilities.staffchat")) continue;

            onlinePlayer.sendMessage(formattedMessage);
        }
    }
}
