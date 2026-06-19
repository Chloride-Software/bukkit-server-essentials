package com.chloride.utilities.command.param.impl;

import com.chloride.utilities.command.param.Parameter;
import org.bukkit.command.CommandSender;

public class StringParameter extends Parameter<String> {
    @Override
    public String transfer(CommandSender sender, String argument) {
        return argument;
    }
}
