package com.chloride.utilities.command.commands;

import com.chloride.utilities.command.annotation.commandTypes.SubCommand;
import com.chloride.utilities.player.PlayerUtil;
import com.chloride.utilities.server.message.StringUtil;
import com.chloride.utilities.server.message.color.CC;
import com.google.common.base.Strings;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Getter
public class BaseCommandComponent extends Command {

    private final Permission requiredPermission;
    private final boolean requiresOp;
    private final Map<String, SubCommandComponent> subCommands = new HashMap<>();

    @Setter
    private Pair<Method, Object> invoke;

    public BaseCommandComponent(String name, Permission permission, boolean requiresOp) {
        super(name);
        this.requiredPermission = permission;
        this.requiresOp = requiresOp;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, String[] args) {
        if (!PlayerUtil.testPermission(sender, this.requiredPermission, this.requiresOp)) {
            sender.sendMessage(StringUtil.NO_PERMISSION);
            return false;
        }

        // Check if a subcommand should handle this first!
        if (args.length >= 1 && subCommands.containsKey(args[0].toLowerCase())) {
            String argumentCombined = String.join(" ", args);
            argumentCombined = argumentCombined.replace(args[0].toLowerCase(), "");
            subCommands.get(args[0].toLowerCase()).execute(sender, commandLabel, args[0], argumentCombined.split(" "));
            return true;
        }

        // Then check if we can invoke the command -- If its defined
        try {
            if (!invoke.getKey().isAnnotationPresent(SubCommand.class) && invoke.getKey().getParameters().length == 1) {
                this.invoke.getKey().setAccessible(true);
                this.invoke.getKey().invoke(this.invoke.getValue(), sender);
                return true;
            }
        } catch (InvocationTargetException e) {
            if (!(e.getTargetException() instanceof IllegalArgumentException)) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            if (!(e instanceof IllegalArgumentException)) {
                e.printStackTrace();
            }
        }

        // If no base command was defined, then lets make a usage message
        sender.sendMessage(CC.GRAY + CC.STRIKE_THROUGH + Strings.repeat("-", 52));
        for (Map.Entry<String, SubCommandComponent> st : subCommands.entrySet()) {
            sender.sendMessage(CC.RED + "/" + commandLabel + " " + st.getKey() + (st.getValue().getDescription().isEmpty() ? "" : CC.GRAY + ": " + CC.RED + st.getValue().getDescription()));
        }
        sender.sendMessage(CC.GRAY + CC.STRIKE_THROUGH + Strings.repeat("-", 52));
        return true;
    }
}
