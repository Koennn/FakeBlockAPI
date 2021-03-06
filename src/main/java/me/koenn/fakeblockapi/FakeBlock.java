package me.koenn.fakeblockapi;

import me.koenn.core.misc.LocationHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * Copyright (C) Koenn - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Koen Willemse, April 2017
 */
public class FakeBlock {

    private final Location location;
    private final Material material;
    private final short meta;
    private final List<Player> visibleFor;

    public FakeBlock(Location location, Material material, short meta, Player... visibleFor) {
        this.location = location;
        this.material = material;
        this.meta = meta;
        this.visibleFor = new ArrayList<>();
        Collections.addAll(this.visibleFor, visibleFor);
        Bukkit.getScheduler().scheduleSyncDelayedTask(FakeBlockAPI.getInstance(), () -> this.visibleFor.forEach(this::refreshFor), 5);
    }

    @SuppressWarnings("deprecation")
    public void refreshFor(Player player) {
        player.sendBlockChange(this.location, this.material, (byte) this.meta);
    }

    public boolean visibleFor(Player player) {
        return this.visibleFor.contains(player);
    }

    public Location getLocation() {
        return location;
    }

    public Material getMaterial() {
        return material;
    }

    public short getMeta() {
        return meta;
    }

    public List<Player> getVisibleFor() {
        return visibleFor;
    }

    @SuppressWarnings("deprecation")
    public void resetFor(Player player) {
        Block block = this.location.getBlock();
        player.sendBlockChange(this.location, block.getType(), block.getData());
        this.visibleFor.remove(player);
    }

    @Override
    public String toString() {
        return this.material.name() + ":" + this.meta + "@(" + LocationHelper.getString(this.location) + ")";
    }
}
