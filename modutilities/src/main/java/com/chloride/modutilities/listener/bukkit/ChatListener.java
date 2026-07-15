package com.chloride.modutilities.listener.bukkit;

import com.chloride.modutilities.data.PlayerData;
import com.chloride.modutilities.data.PlayerDataManager;
import com.chloride.modutilities.util.chat.StaffChatUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class ChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        PlayerData data = PlayerDataManager.getPlayerData(player);

        if (data == null) return;

        if (player.hasPermission("clmodutilities.staffchat")) {
            boolean redirectToStaffChat = data.isInStaffChat();

            // if they have staff chat mode enabled

            // if they use the staff chat prefix
            if (!data.isInStaffChat() && message.startsWith("#")) {
                redirectToStaffChat = true;
                message = message.substring(1);
            }

            // remove leading/trailing whitespace
            message = message.trim();

            if (redirectToStaffChat) {
                StaffChatUtil.messageStaff(player, message, true);
                event.setCancelled(true);
            }
        }
    }
}
