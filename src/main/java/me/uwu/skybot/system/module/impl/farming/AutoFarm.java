package me.uwu.skybot.system.module.impl.farming;

import fr.shyrogan.post.receiver.annotation.Subscribe;
import gg.essential.api.EssentialAPI;
import me.uwu.skybot.SkyBot;
import me.uwu.skybot.event.impl.EventPacket;
import me.uwu.skybot.event.impl.EventRender;
import me.uwu.skybot.event.impl.EventUpdate;
import me.uwu.skybot.utils.Timer;
import me.xtrm.skybot.accessor.IKeyBinding;
import me.xtrm.skybot.system.module.Category;
import me.xtrm.skybot.system.module.Module;
import me.xtrm.skybot.utils.Renderer;
import net.minecraft.block.Block;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

import java.awt.*;
import java.util.Random;

@Module.Metadata(
        id = "auto-farm",
        category = Category.FARMING
)
public class AutoFarm extends Module {
    private static final Random RANDOM = new Random();

    private final Timer timer = new Timer();
    private float rng;
    private int dodo = 0;
    private boolean goRight = true;
    private boolean stuck = false;

    @Override
    public void onEnable() {
        super.onEnable();
        rng = RANDOM.nextFloat();
        goRight = true;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        ((IKeyBinding) mc.gameSettings.keyBindForward).sb$setPressed(false);
        ((IKeyBinding) mc.gameSettings.keyBindBack).sb$setPressed(false);
        ((IKeyBinding) mc.gameSettings.keyBindLeft).sb$setPressed(false);
        ((IKeyBinding) mc.gameSettings.keyBindRight).sb$setPressed(false);
        ((IKeyBinding) mc.gameSettings.keyBindAttack).sb$setPressed(false);
    }

    @Subscribe
    public void onUpdate(EventUpdate.Pre eventUpdate) {
        ((IKeyBinding)mc.gameSettings.keyBindAttack).sb$setPressed(true);

        EnumFacing facing = EnumFacing.values()[SkyBot.INSTANCE.getConfig().enumFacingOrd + 2];
        BlockPos[] pos = getPositions();

        BlockPos bottomPos = new BlockPos(
                mc.thePlayer.posX,
                mc.thePlayer.posY - 0.5f,
                mc.thePlayer.posZ
        );

        Block bottomBlock = mc.theWorld.getBlockState(bottomPos).getBlock();

        if (dodo > 0) {
            dodo--;
        } else {
            if (bottomBlock.getUnlocalizedName().contains("Portal") && mc.thePlayer.onGround) {
                ((IKeyBinding)mc.gameSettings.keyBindRight).sb$setPressed(false);
                ((IKeyBinding)mc.gameSettings.keyBindLeft).sb$setPressed(false);
                ((IKeyBinding)mc.gameSettings.keyBindJump).sb$setPressed(true);
            } else {
                ((IKeyBinding)mc.gameSettings.keyBindJump).sb$setPressed(false);

                if (goRight) {
                    if (mc.thePlayer.isCollidedHorizontally && !mc.theWorld.isAirBlock(pos[1])) {
                        goRight = !goRight;
                        dodo = 20;
                    }
                } else {
                    if (mc.thePlayer.isCollidedHorizontally && !mc.theWorld.isAirBlock(pos[0])) {
                        goRight = !goRight;
                        dodo = 20;
                    }
                }
            }
        }

        if (dodo <= 0) {
            if (goRight) {
                ((IKeyBinding)mc.gameSettings.keyBindRight).sb$setPressed(true);
                ((IKeyBinding)mc.gameSettings.keyBindLeft).sb$setPressed(false);
            } else {
                ((IKeyBinding)mc.gameSettings.keyBindRight).sb$setPressed(false);
                ((IKeyBinding)mc.gameSettings.keyBindLeft).sb$setPressed(true);
            }
        }

        if (!stuck) {
            ((IKeyBinding) mc.gameSettings.keyBindForward).sb$setPressed(true);
            ((IKeyBinding) mc.gameSettings.keyBindBack).sb$setPressed(false);
        }else {
            stuck = false;
            ((IKeyBinding) mc.gameSettings.keyBindForward).sb$setPressed(false);
            ((IKeyBinding) mc.gameSettings.keyBindBack).sb$setPressed(true);
        }


        mc.thePlayer.rotationYaw = (facing.getHorizontalIndex() * 90) + rng;
        mc.thePlayer.rotationPitch = -3.1f;
    }

    @SuppressWarnings("ConstantConditions")
    @Subscribe
    public void onRender2D(EventRender.R2D eventRender) {
        int i = 0;
        fr.drawStringWithShadow("AutoFarm", 2, 2 + (i++ * fr.FONT_HEIGHT), -1);
        BlockPos pos = new BlockPos(
                mc.thePlayer.posX,
                mc.thePlayer.posY,
                mc.thePlayer.posZ
        );
        fr.drawStringWithShadow("ppos   => " + pos, 2, 2 + (i++ * fr.FONT_HEIGHT), -1);
        BlockPos floor = new BlockPos(
                MathHelper.floor_double(pos.getX()),
                MathHelper.floor_double(pos.getY()),
                MathHelper.floor_double(pos.getZ())
        );
        fr.drawStringWithShadow("floord => " + floor, 2, 2 + (i++ * fr.FONT_HEIGHT), -1);
        fr.drawStringWithShadow("Go => " + (goRight ? "right" : "left"), 2, 2 + (i++ * fr.FONT_HEIGHT), -1);
        fr.drawStringWithShadow("Dodo => " + (dodo > 0), 2, 2 + (i * fr.FONT_HEIGHT), -1);
    }

    @Subscribe
    public void onRender3D(EventRender.R3D eventRender) {
        EnumFacing facing = EnumFacing.values()[SkyBot.INSTANCE.getConfig().enumFacingOrd + 2];

        BlockPos[] positions = getPositions();

        Renderer.startLines(new Color(0, 0, 255, 255), 1, true);

        BlockPos pos;
        AxisAlignedBB aabb;

        pos = positions[0];
        aabb = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
        Renderer.glColor(new Color(0, 255, 0, 255), 1);
        Renderer.drawAABB(aabb);

        pos = positions[1];
        aabb = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
        Renderer.glColor(new Color(255, 0, 0, 255), 1);
        Renderer.drawAABB(aabb);

        Renderer.endLines(true);
    }

    /**
     * @return Left / right
     */
    private BlockPos[] getPositions() {
        BlockPos[] positions = new BlockPos[2];
        EnumFacing[] sides = new EnumFacing[2];
        EnumFacing facing = EnumFacing.values()[SkyBot.INSTANCE.getConfig().enumFacingOrd + 2];
        BlockPos playerPos = new BlockPos(
                mc.thePlayer.posX,
                mc.thePlayer.posY + 1,
                mc.thePlayer.posZ
        );

        switch (facing) {
            case NORTH:
                sides[0] = EnumFacing.WEST;
                sides[1] = EnumFacing.EAST;
                break;
            case SOUTH:
                sides[0] = EnumFacing.EAST;
                sides[1] = EnumFacing.WEST;
                break;
            case WEST:
                sides[0] = EnumFacing.SOUTH;
                sides[1] = EnumFacing.NORTH;
                break;
            case EAST:
                sides[0] = EnumFacing.NORTH;
                sides[1] = EnumFacing.SOUTH;
                break;
        }
        positions[0] = playerPos.offset(sides[0]);
        positions[1] = playerPos.offset(sides[1]);

        return positions;
    }

    @Subscribe
    public void onPacket(EventPacket.Receive eventPacket) {
        if (!SkyBot.INSTANCE.getConfig().unstuck)
            return;
        if (eventPacket.getPacket() instanceof S08PacketPlayerPosLook) {
            stuck = true;
            dodo = 5;
            ((IKeyBinding)mc.gameSettings.keyBindLeft).sb$setPressed(false);
            ((IKeyBinding)mc.gameSettings.keyBindRight).sb$setPressed(false);
            ((IKeyBinding)mc.gameSettings.keyBindForward).sb$setPressed(false);
            ((IKeyBinding)mc.gameSettings.keyBindBack).sb$setPressed(true);
            EssentialAPI.getNotifications().push("Watchdog", "you where stuck in a block");
        }
    }
}
