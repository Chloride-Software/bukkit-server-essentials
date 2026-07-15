package com.chloride.modutilities.listener.bukkit;

import com.chloride.modutilities.data.PlayerData;
import com.chloride.modutilities.data.PlayerDataManager;
import com.chloride.modutilities.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ModModeListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("clmodutilities.modmode")) return;

        PlayerData data = PlayerDataManager.getPlayerData(player);
        if (data == null) return;

        if (!data.isInModMode()) return;

        // item check
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack.getType() == Material.GLASS) {
            player.performCommand("vanish");
        }
        else if (itemStack.getType() == Material.COMPASS) {
            player.performCommand("thru"); // worldedit
        }
        else if (itemStack.getType() == Material.DIAMOND) {
            List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
            players.remove(player);

            if (players.isEmpty())
                player.sendMessage(Messages.MOD_MODE_RANDOM_TP_EMPTY);

            Player randomPlayer = players.isEmpty()
                    ? null
                    : players.get(ThreadLocalRandom.current().nextInt(players.size()));

            if (randomPlayer != null)
                player.teleport(randomPlayer);
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;
        if (!(event.getEntity() instanceof Player target)) return;
        if (!player.hasPermission("clmodutilities.modmode")) return;

        PlayerData data = PlayerDataManager.getPlayerData(player);
        if (data == null) return;

        if (!data.isInModMode()) return;

        // item check
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack.getType() == Material.BOOK) {
            player.performCommand("inventorysee " + target.getName());
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("clmodutilities.modmode")) return;

        PlayerData data = PlayerDataManager.getPlayerData(player);
        if (data == null) return;

        if (!data.isInModMode()) return;

        event.setCancelled(true);
    }
}
