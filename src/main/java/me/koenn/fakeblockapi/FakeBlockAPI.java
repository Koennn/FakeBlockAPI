package me.koenn.fakeblockapi;

import me.koenn.core.registry.Registry;
import me.koenn.fakeblockapi.listeners.PlayerInteractListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * <p>
 * Copyright (C) Koenn - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Koen Willemse, April 2017
 */
public final class FakeBlockAPI extends JavaPlugin {

    public static final Registry<FakeBlock> fakeBlockRegistry = new Registry<>(FakeBlock::toString);

    @Override
    public void onEnable() {
        this.getLogger().info("All credits for this plugin go to Koenn");

        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
    }
}
