package com.chloride.utilities.command.param.impl.bukkit;

import com.chloride.utilities.command.param.Parameter;
import com.chloride.utilities.server.message.color.CC;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

public class WorldParameter extends Parameter<World> {

    @Override
    public World transfer(CommandSender sender, String argument) {
        World world = Bukkit.getWorld(argument);
        if (world == null) {
            sender.sendMessage(CC.RED + "World '" + argument + "' not found!");
            return null;
        }

        return world;
    }

}
