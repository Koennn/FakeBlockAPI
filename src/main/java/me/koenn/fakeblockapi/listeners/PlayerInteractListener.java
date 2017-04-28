package me.koenn.fakeblockapi.listeners;

import me.koenn.fakeblockapi.FakeBlock;
import me.koenn.fakeblockapi.FakeBlockAPI;
import me.koenn.fakeblockapi.util.FakeBlockHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * <p>
 * Copyright (C) Koenn - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Koen Willemse, April 2017
 */
public class PlayerInteractListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) {
            return;
        }

        Location location = event.getClickedBlock().getLocation();
        if (!FakeBlockHelper.hasFakeBlock(location)) {
            return;
        }

        FakeBlock[] fakeBlocks = FakeBlockHelper.getFakeBlocks(location);
        if (fakeBlocks == null) {
            return;
        }

        Player player = event.getPlayer();
        for (FakeBlock fakeBlock : fakeBlocks) {
            if (fakeBlock.visibleFor(player)) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(FakeBlockAPI.getInstance(), () -> fakeBlock.refreshFor(player), 1);
                Bukkit.getScheduler().scheduleSyncDelayedTask(FakeBlockAPI.getInstance(), () -> fakeBlock.refreshFor(player), 5);
                if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(FakeBlockAPI.getInstance(), () -> fakeBlock.refreshFor(player), 40);
                }
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Bukkit.getLogger().info("break");
        if (event.getBlock() == null) {
            return;
        }

        Location location = event.getBlock().getLocation();
        if (!FakeBlockHelper.hasFakeBlock(location)) {
            return;
        }

        FakeBlock[] fakeBlocks = FakeBlockHelper.getFakeBlocks(location);
        if (fakeBlocks == null) {
            return;
        }

        Player player = event.getPlayer();
        for (FakeBlock fakeBlock : fakeBlocks) {
            if (fakeBlock.visibleFor(player)) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(FakeBlockAPI.getInstance(), () -> fakeBlock.refreshFor(player), 1);
                Bukkit.getScheduler().scheduleSyncDelayedTask(FakeBlockAPI.getInstance(), () -> fakeBlock.refreshFor(player), 10);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        //TODO: Fix login block reset.
        Bukkit.getScheduler().scheduleSyncDelayedTask(FakeBlockAPI.getInstance(), () -> {
            Player player = event.getPlayer();
            for (FakeBlock fakeBlock : FakeBlockAPI.fakeBlockRegistry.getRegisteredObjects()) {
                if (fakeBlock.visibleFor(player)) {
                    fakeBlock.refreshFor(player);
                }
            }
        }, 20);
    }
}
