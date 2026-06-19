package com.chloride.utilities.command.param.impl;

import com.chloride.utilities.command.param.Parameter;
import org.bukkit.command.CommandSender;

public class BooleanParameter extends Parameter<Boolean> {
    @Override
    public Boolean transfer(CommandSender sender, String argument) {

        return argument.equalsIgnoreCase("yes") || argument.equalsIgnoreCase("yep") || argument.equalsIgnoreCase("yea") || argument.equalsIgnoreCase("true");

    }
}
