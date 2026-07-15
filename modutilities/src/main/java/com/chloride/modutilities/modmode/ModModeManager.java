package com.chloride.modutilities.modmode;

import com.chloride.modutilities.data.PlayerData;
import com.chloride.modutilities.data.PlayerDataManager;
import com.chloride.modutilities.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ModModeManager {
    public static void giveItems(Player player) {
        if (player == null)
            throw new RuntimeException("Cannot give mod-mode items to a null Player. (ModModeManager)");

        if (!player.hasPermission("clmodutilities.modmode")) return;

        PlayerData data = PlayerDataManager.getPlayerData(player);
        if (data == null) return;

        PlayerData.ModModeData modModeData = data.getModModeData();

        PlayerInventory inventory = player.getInventory();
        if (modModeData.getModModeLastInventory() == null && modModeData.getModModeLastArmor() == null) {
            modModeData.setModModeLastInventory(inventory.getStorageContents().clone());
            modModeData.setModModeLastArmor(inventory.getArmorContents().clone());
        }

        inventory.clear();

        inventory.setItem(0, new ItemBuilder(Material.COMPASS).name("&bCompass").build());
        inventory.setItem(1, new ItemBuilder(Material.BOOK).name("&bInspect Inventory &7(left click)").build());

        inventory.setItem(4, new ItemBuilder(Material.DIAMOND).name("&bRandom Teleport").build());
        inventory.setItem(8, new ItemBuilder(Material.GLASS).name("&bToggle Visibility").build());
    }

    public static void resetItems(Player player) {
        if (player == null)
            throw new RuntimeException("Cannot reset mod-mode items from a null Player. (ModModeManager)");

        if (!player.hasPermission("clmodutilities.modmode")) return;

        PlayerData data = PlayerDataManager.getPlayerData(player);
        if (data == null) return;

        PlayerInventory inventory = player.getInventory();
        inventory.clear();

        PlayerData.ModModeData modModeData = data.getModModeData();
        ItemStack[] lastInventory = modModeData.getModModeLastInventory();
        ItemStack[] lastArmor = modModeData.getModModeLastArmor();
        if (lastInventory != null)
            inventory.setStorageContents(lastInventory);

        if (lastArmor != null)
            inventory.setArmorContents(lastArmor);

        modModeData.setModModeLastInventory(null);
        modModeData.setModModeLastArmor(null);
    }
}
