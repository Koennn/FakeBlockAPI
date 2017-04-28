package me.koenn.fakeblockapi.util;

import me.koenn.core.misc.LocationHelper;
import me.koenn.fakeblockapi.FakeBlock;
import me.koenn.fakeblockapi.FakeBlockAPI;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Copyright (C) Koenn - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Koen Willemse, April 2017
 */
public final class FakeBlockHelper {

    public static FakeBlock[] getFakeBlocks(Location location) {
        List<FakeBlock> fakeBlocks = new ArrayList<>();
        for (FakeBlock fakeBlock : FakeBlockAPI.fakeBlockRegistry.getRegisteredObjects()) {
            if (fakeBlock.getLocation().equals(location)) {
                fakeBlocks.add(fakeBlock);
            }
        }

        if (fakeBlocks.isEmpty()) {
            return null;
        }

        FakeBlock[] array = new FakeBlock[fakeBlocks.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = fakeBlocks.get(i);
        }
        return array;
    }

    public static boolean hasFakeBlock(Location location) {
        return getFakeBlocks(location) != null;
    }

    public static FakeBlock getFakeBlock(Material material, short meta, Location location) {
        return FakeBlockAPI.fakeBlockRegistry.get(getFakeBlockString(material, meta, location));
    }

    public static String getFakeBlockString(Material material, short meta, Location location) {
        return material.name() + ":" + meta + "@(" + LocationHelper.getString(location) + ")";
    }
}
