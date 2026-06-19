package com.chloride.library;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class LibraryPlugin extends JavaPlugin {
    @Getter
    private static LibraryPlugin instance;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
