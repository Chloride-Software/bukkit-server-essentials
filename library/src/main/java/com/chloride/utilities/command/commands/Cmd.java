package com.chloride.utilities.command.commands;

import com.chloride.utilities.command.ParamType;
import com.chloride.utilities.command.param.ParamData;
import com.chloride.utilities.command.param.Parameter;
import com.chloride.utilities.player.PlayerUtil;
import com.chloride.utilities.server.message.StringUtil;
import com.chloride.utilities.server.message.color.CC;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Cmd extends Command {

    private final Permission permission;
    private final boolean requiresOp;
    @Getter
    private final List<Pair<Parameter<?>, ParamData>> arguments = new ArrayList<>();
    @Setter
    private Pair<Method, Object> invoke;

    public Cmd(String label, Permission permission, boolean requiresOp) {
        super(label);
        this.permission = permission;
        this.requiresOp = requiresOp;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, String[] args) {
        if (!PlayerUtil.testPermission(sender, this.permission, this.requiresOp)) {
            sender.sendMessage(StringUtil.NO_PERMISSION);
            return false;
        }

        int i = 0;
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(sender);

        StringBuilder usage = new StringBuilder(CC.RED + "Usage: /" + commandLabel + " ");

        for (Pair<Parameter<?>, ParamData> paramPair : arguments) {

            if (paramPair.getValue().getType() == ParamType.MULTI) {
                usage.append(CC.RED).append("[").append(paramPair.getValue().getName()).append(CC.RED)
                        .append("] ");
                parameters
                        .add(paramPair.getKey().transform(sender, StringUtils.join(args, ' ', i, args.length)));
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
                    if (paramPair.getValue().getType() != ParamType.FLAG) {
                        i++;
                        continue;
                    } else {
                        parameters.add(false);
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
                return true;
            }


            parameters.add(object);
            i++;
        }

        try {
            this.invoke.getKey().setAccessible(true);
            this.invoke.getKey().invoke(this.invoke.getValue(), parameters.toArray());
            return true;
        } catch (IllegalArgumentException e) {
            sender.sendMessage(
                    usage + (description.isEmpty() ? "" : CC.GRAY + ": " + description));
            return false;
        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof IllegalArgumentException) {
                sender.sendMessage(
                        usage + (description.isEmpty() ? "" : CC.GRAY + ": " + description));
                return false;
            } else {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
