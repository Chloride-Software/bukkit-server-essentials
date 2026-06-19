package com.chloride.utilities.command.commands;

import com.chloride.utilities.command.ParamType;
import com.chloride.utilities.command.param.ParamData;
import com.chloride.utilities.command.param.Parameter;
import com.chloride.utilities.player.PlayerUtil;
import com.chloride.utilities.server.message.color.CC;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Data
public class SubCommandComponent {

    private final Permission permission;
    private final boolean requiresOp;
    private String commandName;
    private String description;
    private List<String> alias;
    private List<Pair<Parameter<?>, ParamData>> arguments = new ArrayList<>();
    private Pair<Method, Object> invoke;

    public SubCommandComponent(String name, String description, Permission permission, boolean requiresOp, List<String> alias) {
        this.commandName = name;
        this.description = description;
        this.permission = permission;
        this.requiresOp = requiresOp;
        this.alias = alias;
    }

    public void execute(CommandSender sender, String commandLabel, String argumentLabel, String[] args) {
        if (!PlayerUtil.testPermission(sender, this.permission, this.requiresOp)) {
            return;
        }

        List<Object> parameters = new ArrayList<>();
        parameters.add(sender);

        int i = 1;
        StringBuilder usage = new StringBuilder(CC.RED + "Usage: /" + commandLabel + " " + argumentLabel + " ");

        for (Pair<Parameter<?>, ParamData> paramPair : arguments) {

            if (paramPair.getValue().getType() == ParamType.MULTI) {
                usage.append(CC.RED).append("[").append(paramPair.getValue().getName()).append(CC.RED)
                        .append("] ");
                parameters.add(paramPair.getKey().transform(sender, StringUtils.join(args, ' ', i, args.length)));
                break;
            }

            if (paramPair.getValue().getType() == ParamType.FLAG) {
                usage.append(CC.RED).append("(").append(CC.AQUA).append("-")
                        .append(paramPair.getValue().getName()).append(CC.RED).append(") ");
            } else {
                usage.append(CC.RED).append("<").append(paramPair.getValue().getName()).append(CC.RED)
                        .append("> ");
            }

            String argument;
            try {
                argument = args[i];
            } catch (ArrayIndexOutOfBoundsException ex) {
                argument = null;
            }

            if (argument == null) {
                if (!paramPair.getValue().getDefaultValue().equalsIgnoreCase("")) {
                    argument = paramPair.getValue().getDefaultValue();
                } else {
                    if (paramPair.getValue().getType() == ParamType.FLAG) {
                        parameters.add(false);
                        continue;
                    } else {
                        i++;
                        continue;
                    }
                }
            }

            if (paramPair.getValue().getType() == ParamType.FLAG) {
                if (argument.startsWith("-") &&
                        (argument).replace("-", "").equalsIgnoreCase(paramPair.getRight().getName())) {
                    parameters.add(true);
                    i++;
                    continue;
                } else {
                    parameters.add(false);
                    continue;
                }
            }

            Object object = paramPair.getKey().transform(sender, argument);
            if (object == null) {
                sender.sendMessage(CC.RED + "Illegal argument: " + argument + " was not expected.");
                return;
            }

            parameters.add(object);
            i++;
        }

        try {
            this.invoke.getKey().setAccessible(true);
            this.invoke.getKey().invoke(this.invoke.getValue(), parameters.toArray());
        } catch (IllegalArgumentException e) {
            sender.sendMessage(usage + (description.equals("") ? "" : CC.GRAY + ": " + description));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
