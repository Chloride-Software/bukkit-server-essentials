package com.chloride.utilities.server.settings;

import com.chloride.utilities.inventory.InventoryUI;
import org.bukkit.entity.Player;

public interface SettingsHandler {
    void onCreateSettings(InventoryUI inventoryUI, Player player);
}
