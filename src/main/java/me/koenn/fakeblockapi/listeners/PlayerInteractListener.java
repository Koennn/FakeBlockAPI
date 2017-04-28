package me.koenn.fakeblockapi.listeners;

import me.koenn.fakeblockapi.FakeBlock;
import me.koenn.fakeblockapi.util.FakeBlockHelper;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

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
                fakeBlock.refreshFor(player);
                event.setCancelled(true);
            }
        }
    }
}
