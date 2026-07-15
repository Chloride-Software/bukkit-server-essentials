package com.chloride.modutilities.listener.bukkit;

import com.chloride.modutilities.data.PlayerData;
import com.chloride.modutilities.data.PlayerDataManager;
import com.chloride.modutilities.util.Messages;
import com.chloride.modutilities.util.chat.StaffChatUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;

public class VanishListener implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        this.processEvent(event.getPlayer(), event, true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        this.processEvent(event.getPlayer(), event, true);
    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent event) {
        this.processEvent(event.getPlayer(), event);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        this.processEvent(event.getPlayer(), event);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        this.processEvent(event.getPlayer(), event, true);
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        this.processEvent(player, event);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        this.processEvent(player, event);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;

        this.processEvent(player, event, true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByBlockEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        this.processEvent(player, event);
    }

    private void processEvent(Player player, Cancellable cancellable, boolean message) {
        PlayerData data = PlayerDataManager.getPlayerData(player);

        if (data == null) return;

        if (player.hasPermission("clmodutilities.vanish") && data.isVanished()) {
            if (message) player.sendMessage(Messages.VANISH_ACTION_DISALLOWED);
            cancellable.setCancelled(true);
        }
    }

    private void processEvent(Player player, Cancellable cancellable) {
        this.processEvent(player, cancellable, false);
    }
}
