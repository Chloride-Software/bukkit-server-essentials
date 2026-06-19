package com.chloride.utilities.command.annotation.commandTypes;

import org.bukkit.permissions.PermissionDefault;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    String[] name();

    String description() default "";

    String permission() default "";

    PermissionDefault permissionDefault() default PermissionDefault.FALSE;

    boolean requiresOp() default false;

}
