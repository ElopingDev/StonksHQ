package me.uwu.skybot.utils;

import me.xtrm.skybot.utils.Renderer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

import java.awt.*;

public class RenderUtils {
    public static void drawBlock(int x, int y, int z, Color color) {
        Renderer.startLines(new Color(0, 0, 255, 255), 1, true);
        AxisAlignedBB aabb = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
        Renderer.glColor(color, 1);
        Renderer.drawAABB(aabb);
        Renderer.endLines(true);
    }

    public static void drawBlock(BlockPos pos, Color color) {
        drawBlock(pos.getX(), pos.getY(), pos.getZ(), color);
    }
}
