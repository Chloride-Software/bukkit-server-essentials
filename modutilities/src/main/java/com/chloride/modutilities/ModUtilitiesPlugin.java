package com.chloride.modutilities;

import co.aikar.commands.PaperCommandManager;
import com.chloride.modutilities.command.*;
import com.chloride.modutilities.listener.bukkit.ChatListener;
import com.chloride.modutilities.listener.bukkit.ConnectionListener;
import com.chloride.modutilities.listener.bukkit.ModModeListener;
import com.chloride.modutilities.listener.bukkit.VanishListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

@Getter
public final class ModUtilitiesPlugin extends JavaPlugin {
    @Getter
    private static ModUtilitiesPlugin instance;

    private PaperCommandManager commandManager;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        this.commandManager = new PaperCommandManager(this);
        this.commandManager.enableUnstableAPI("help");

        this.registerCommands();
        this.registerListeners();
    }

    @Override
    public void onDisable() {
        // no-op
    }

    private void registerCommands() {
        List.of(
                new VanishCommand(),
                new StaffChatCommand(),
                new InventorySeeCommand(),
                new EnderChestCommand(),
                new ModModeCommand()
        ).forEach((command) -> this.commandManager.registerCommand(command));
    }

    private void registerListeners() {
        List.of(
                new ConnectionListener(),
                new ChatListener(),
                new VanishListener(),
                new ModModeListener()
        ).forEach((listener) -> Bukkit.getPluginManager().registerEvents(listener, this));
    }

}
