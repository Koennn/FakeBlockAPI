package me.koenn.fakeblockapi;

import me.koenn.core.registry.Registry;
import me.koenn.fakeblockapi.listeners.PlayerInteractListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Collectors;

/**
 * <p>
 * Copyright (C) Koenn - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Koen Willemse, April 2017
 */
public final class FakeBlockAPI extends JavaPlugin {

    public static final Registry<FakeBlock> fakeBlockRegistry = new Registry<>(FakeBlock::toString);
    private static FakeBlockAPI instance;

    public static FakeBlockAPI getInstance() {
        return instance;
    }

    public static void resetPlayer(Player player) {
        for (FakeBlock fakeBlock : fakeBlockRegistry.getRegisteredObjects()) {
            if (fakeBlock.visibleFor(player)) {
                fakeBlock.resetFor(player);
            }
        }
    }

    @Override
    public void onEnable() {
        instance = this;
        this.getLogger().info("All credits for this plugin go to Koenn");

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> fakeBlockRegistry.getRegisteredObjects().stream().filter(fakeBlock -> fakeBlock.getVisibleFor().isEmpty()).collect(Collectors.toList()).forEach(fakeBlockRegistry::unregister), 0, 1200);

        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(FakeBlockAPI::resetPlayer);
        fakeBlockRegistry.clear();
        Bukkit.getScheduler().cancelTasks(this);
    }
}
