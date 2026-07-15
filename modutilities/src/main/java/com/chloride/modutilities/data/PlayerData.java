package com.chloride.modutilities.data;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

@Data
public class PlayerData {
    private final Player bukkitPlayer;

    private boolean vanished;
    private boolean inStaffChat;
    private boolean inModMode;

    @Data
    public static class ModModeData {
        private ItemStack[] modModeLastInventory;
        private ItemStack[] modModeLastArmor;
    }

    private ModModeData modModeData = new ModModeData();
}
