package com.chloride.utilities.command.param.impl;

import com.chloride.utilities.command.param.Parameter;
import com.chloride.utilities.server.message.color.CC;
import com.google.common.primitives.Ints;
import org.bukkit.command.CommandSender;

public class IntegerParameter extends Parameter<Integer> {
    @Override
    public Integer transfer(CommandSender sender, String argument) {
        Integer integer = Ints.tryParse(argument);

        if (integer == null) {
            sender.sendMessage(CC.RED + "'" + argument + "' is not a number!");
            return null;
        }

        return integer;
    }
}
