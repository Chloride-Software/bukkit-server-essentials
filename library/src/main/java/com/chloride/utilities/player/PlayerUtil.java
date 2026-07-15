package com.chloride.utilities.player;

import com.chloride.utilities.server.message.StringUtil;
import com.chloride.utilities.server.message.color.Color;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.Comparator;
import java.util.Map;
import java.util.WeakHashMap;

public final class PlayerUtil {
    public PlayerUtil() {
        throw new RuntimeException("Cannot instantiate a utility class.");
    }

    public static boolean testPermission(CommandSender sender, Permission permission, boolean requiresOp) {
        if (requiresOp && !sender.isOp()) {
            return false;
        }

        if (permission != null) {
            return sender.hasPermission(permission);
        }

        return false;
    }
}
