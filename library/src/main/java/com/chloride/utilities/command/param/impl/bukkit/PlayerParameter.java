package com.chloride.utilities.command.param.impl.bukkit;

import com.chloride.utilities.command.param.Parameter;
import com.chloride.utilities.server.message.color.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerParameter extends Parameter<Player> {
    @Override
    public Player transfer(CommandSender sender, String value) {
        if (sender instanceof Player && (value.equalsIgnoreCase("self") || value.equals(""))) {
            return (Player) sender;
        }

        Player player = Bukkit.getPlayer(value);

        if (player == null) {
            sender.sendMessage(CC.RED + "No player with the name \"" + value + "\" found.");
            return null;
        }

        return player;
    }
}
