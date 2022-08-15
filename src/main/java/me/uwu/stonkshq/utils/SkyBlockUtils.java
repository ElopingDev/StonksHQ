package me.uwu.stonkshq.utils;

import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenEnd;

public class SkyBlockUtils {
    public static int countNearBlocks(Minecraft mc, Class<? extends Block> blockToCount) {
        int wartCount = 0;

        for (int a = -1; a <= 1; a++) {
            for (int b = -1; b <= 1; b++) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        for (int y = 0; y < 255; y++) {
                            Block block = mc.theWorld.getChunkFromChunkCoords(mc.thePlayer.chunkCoordX + a, mc.thePlayer.chunkCoordZ + b).getBlock(x, y, z);
                            /*if (!block.getUnlocalizedName().contains("air")) {
                                System.out.println(block.getUnlocalizedName());
                            }*/
                            if (blockToCount.isAssignableFrom(block.getClass())) {
                                wartCount++;
                            }
                        }
                    }
                }
            }
        }
        return wartCount;
    }

    public static boolean isCurrentlyInOwnIsland(Minecraft mc) {
        return countNearBlocks(mc, BlockNetherWart.class) +
                countNearBlocks(mc, BlockSoulSand.class) >
                500;
    }

    public static boolean isCurrentlyInHubIsland(Minecraft mc) {
        int count = countNearBlocks(mc, BlockEndPortal.class);
        System.out.println("Count: " + count);
        return count == 21;
    }
    
    public static boolean isCurrentlyInLimbo(Minecraft mc) {
        Block b = mc.theWorld.getChunkFromChunkCoords(-2, 1).getBlock(15, 30, 0);
        System.out.println(b.getUnlocalizedName());
        return BlockRedFlower.class.isAssignableFrom(b.getClass());
    }

    public static boolean isCurrentlyInHypixelLobby(Minecraft mc) {
        ItemStack currentItem = mc.thePlayer.inventory.getStackInSlot(0);
        return currentItem == null && currentItem.getDisplayName().contains("Game Menu");
    }

    public static IslandType getCurrentIslandType(Minecraft mc) {
        if (isCurrentlyInOwnIsland(mc)) {
            return IslandType.OWN_ISLAND;
        } else if (isCurrentlyInHubIsland(mc)) {
            return IslandType.HUB_ISLAND;
        } else if (isCurrentlyInLimbo(mc)) {
            return IslandType.LIMBO;
        } else if (isCurrentlyInHypixelLobby(mc)) {
            return IslandType.HYPIXEL_LOBBY;
        } else {
            return IslandType.UNKNOWN;
        }
    }

    public static enum IslandType {
        OWN_ISLAND,
        HUB_ISLAND,
        LIMBO,
        HYPIXEL_LOBBY,
        UNKNOWN;
    }
}
