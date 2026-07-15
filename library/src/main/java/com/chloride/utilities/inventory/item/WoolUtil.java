package com.chloride.utilities.inventory.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.EnumMap;
import java.util.Map;

public final class WoolUtil {
    // todo: make sure this works 1.21.11+

    private static final Map<ChatColor, Material> WOOL_MATERIALS = new EnumMap<>(ChatColor.class);

    static {
        WOOL_MATERIALS.put(ChatColor.WHITE, Material.WHITE_WOOL);
        WOOL_MATERIALS.put(ChatColor.GOLD, Material.ORANGE_WOOL);
        WOOL_MATERIALS.put(ChatColor.LIGHT_PURPLE, Material.MAGENTA_WOOL);
        WOOL_MATERIALS.put(ChatColor.AQUA, Material.LIGHT_BLUE_WOOL);
        WOOL_MATERIALS.put(ChatColor.YELLOW, Material.YELLOW_WOOL);
        WOOL_MATERIALS.put(ChatColor.GREEN, Material.LIME_WOOL);
        WOOL_MATERIALS.put(ChatColor.DARK_GRAY, Material.GRAY_WOOL);
        WOOL_MATERIALS.put(ChatColor.GRAY, Material.LIGHT_GRAY_WOOL);
        WOOL_MATERIALS.put(ChatColor.DARK_AQUA, Material.CYAN_WOOL);
        WOOL_MATERIALS.put(ChatColor.DARK_PURPLE, Material.PURPLE_WOOL);
        WOOL_MATERIALS.put(ChatColor.BLUE, Material.BLUE_WOOL);
        WOOL_MATERIALS.put(ChatColor.BLACK, Material.BLACK_WOOL);
        WOOL_MATERIALS.put(ChatColor.DARK_GREEN, Material.GREEN_WOOL);
        WOOL_MATERIALS.put(ChatColor.RED, Material.RED_WOOL);
        WOOL_MATERIALS.put(ChatColor.DARK_RED, Material.RED_WOOL);
    }

    private WoolUtil() {
    }

    public static Material fromChatColor(ChatColor color) {
        return WOOL_MATERIALS.getOrDefault(color, Material.WHITE_WOOL);
    }
}