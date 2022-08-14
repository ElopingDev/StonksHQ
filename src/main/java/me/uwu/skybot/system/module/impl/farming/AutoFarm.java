package me.uwu.skybot.system.module.impl.farming;

import fr.shyrogan.post.receiver.annotation.Subscribe;
import gg.essential.api.EssentialAPI;
import me.uwu.skybot.SkyBot;
import me.uwu.skybot.event.impl.EventPacket;
import me.uwu.skybot.event.impl.EventRender;
import me.uwu.skybot.event.impl.EventTick;
import me.uwu.skybot.event.impl.EventUpdate;
import me.uwu.skybot.struct.BotDirection;
import me.uwu.skybot.utils.RenderUtils;
import me.xtrm.skybot.accessor.IKeyBinding;
import me.xtrm.skybot.system.module.Category;
import me.xtrm.skybot.system.module.Module;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;

import java.awt.*;
import java.util.Random;

@Module.Metadata(
        id = "auto-farm",
        category = Category.FARMING
)
public class AutoFarm extends Module {
    private static final Random RANDOM = new Random();
    private int dodo = 0;
    private boolean goRight = true;
    private boolean stuck = false;

    @Override
    protected void onToggle() {
        EssentialAPI.getNotifications().push(
                "AutOwO FAwArm",
                "Farmbot is now " + (
                        isEnabled()
                                ? EnumChatFormatting.LIGHT_PURPLE + "EnAwAbled"
                                : EnumChatFormatting.RED + "DisAwAbled"
                ) + EnumChatFormatting.RESET + "."
        );
    }

    @Override
    public void onEnable() {
        mc.thePlayer.rotationYaw = (EnumFacing.values()[SkyBot.INSTANCE.getConfig().enumFacingOrd + 2].getHorizontalIndex() * 90);
        mc.thePlayer.rotationPitch = -3.1f;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        ((IKeyBinding) mc.gameSettings.keyBindForward).sb$setPressed(false);
        ((IKeyBinding) mc.gameSettings.keyBindSneak).sb$setPressed(false);
        ((IKeyBinding) mc.gameSettings.keyBindBack).sb$setPressed(false);
        ((IKeyBinding) mc.gameSettings.keyBindLeft).sb$setPressed(false);
        ((IKeyBinding) mc.gameSettings.keyBindRight).sb$setPressed(false);
        ((IKeyBinding) mc.gameSettings.keyBindAttack).sb$setPressed(false);
    }

    @Subscribe
    public void onUpdate(EventTick e) {

    }

    @Subscribe
    public void onPre(EventUpdate.Pre e) {
        //mc.thePlayer.rotationPitch = -3.1f;

        BlockPos bottomPos = new BlockPos(
                mc.thePlayer.posX,
                mc.thePlayer.posY - 0.5f,
                mc.thePlayer.posZ
        );

        Block bottomBlock = mc.theWorld.getBlockState(bottomPos).getBlock();

        ((IKeyBinding) mc.gameSettings.keyBindForward).sb$setPressed(false);
        ((IKeyBinding) mc.gameSettings.keyBindLeft).sb$setPressed(false);
        ((IKeyBinding) mc.gameSettings.keyBindRight).sb$setPressed(false);
        ((IKeyBinding) mc.gameSettings.keyBindSneak).sb$setPressed(false);

        if (getBestDirection() == BotDirection.FORWARD) {
            mc.thePlayer.rotationYaw = (EnumFacing.values()[SkyBot.INSTANCE.getConfig().enumFacingOrd + 2].getHorizontalIndex() * 90);
            ((IKeyBinding) mc.gameSettings.keyBindSneak).sb$setPressed(true);
            ((IKeyBinding) mc.gameSettings.keyBindForward).sb$setPressed(true);
        }
        else if (getBestDirection() == BotDirection.LEFT)
            ((IKeyBinding) mc.gameSettings.keyBindLeft).sb$setPressed(true);
        else if (getBestDirection() == BotDirection.RIGHT)
            ((IKeyBinding) mc.gameSettings.keyBindRight).sb$setPressed(true);

        if (dodo <= 0) {
            if ((mc.thePlayer.posY - ((int) mc.thePlayer.posY)) == .8125f) {
                mc.thePlayer.jump();
                dodo = 40;
            }
        } else dodo--;
    }

    @Subscribe
    public void onPre(EventUpdate.Post e) {

    }

    @Subscribe
    public void onRender2D(EventRender.R2D e) {

    }

    @Subscribe
    public void onRender3D(EventRender.R3D e) {
        BlockPos[] pos = getPositions();
        RenderUtils.drawBlock(pos[0], getBlockColor(pos[0]));
        RenderUtils.drawBlock(pos[1], getBlockColor(pos[1]));
        RenderUtils.drawBlock(pos[2], getBlockColor(pos[2]));

        BlockPos bottomPos = new BlockPos(
                mc.thePlayer.posX,
                mc.thePlayer.posY - 0.5f,
                mc.thePlayer.posZ
        );

        RenderUtils.drawBlock(bottomPos, Color.BLUE);
    }


    @Subscribe
    public void onPacket(EventPacket.Receive e) {

    }

    private BlockPos[] getPositions() {
        BlockPos[] positions = new BlockPos[3];
        EnumFacing[] sides = new EnumFacing[3];
        EnumFacing facing = EnumFacing.values()[SkyBot.INSTANCE.getConfig().enumFacingOrd + 2];
        BlockPos playerPos = new BlockPos(
                mc.thePlayer.posX,
                mc.thePlayer.posY,
                mc.thePlayer.posZ
        );

        switch (facing) {
            case NORTH:
                sides[0] = EnumFacing.WEST;
                sides[1] = EnumFacing.EAST;
                sides[2] = EnumFacing.NORTH;
                break;
            case SOUTH:
                sides[0] = EnumFacing.EAST;
                sides[1] = EnumFacing.WEST;
                sides[2] = EnumFacing.SOUTH;
                break;
            case WEST:
                sides[0] = EnumFacing.SOUTH;
                sides[1] = EnumFacing.NORTH;
                sides[2] = EnumFacing.WEST;
                break;
            case EAST:
                sides[0] = EnumFacing.NORTH;
                sides[1] = EnumFacing.SOUTH;
                sides[2] = EnumFacing.EAST;
                break;
        }
        positions[0] = playerPos.offset(sides[0]);
        positions[1] = playerPos.offset(sides[1]);
        positions[2] = playerPos.offset(sides[2]);

        return positions;
    }

    private boolean isPathBlock(BlockPos pos) {
        return mc.theWorld.getBlockState(pos).getBlock().getUnlocalizedName().toLowerCase().contains("slab") ||
                mc.theWorld.getBlockState(pos).getBlock().getUnlocalizedName().toLowerCase().contains("portal");
    }

    public Color getBlockColor(BlockPos pos) {
        return isPathBlock(pos) ? Color.GREEN : Color.RED;
    }

    public boolean canGoRight() {
        return isPathBlock(getPositions()[1]);
    }

    public boolean canGoLeft() {
        return isPathBlock(getPositions()[0]);
    }

    public boolean canGoForward() {
        return isPathBlock(getPositions()[2]);
    }

    public BotDirection getBestDirection() {
        if (canGoForward())
            return BotDirection.FORWARD;
        if (canGoRight() && canGoLeft()) {
            if (goRight)
                return BotDirection.RIGHT;
            else return BotDirection.LEFT;
        }
        if (canGoRight()) {
            goRight = true;
            return BotDirection.RIGHT;
        }
        if (canGoLeft()) {
            goRight = false;
            return BotDirection.LEFT;
        }
        return BotDirection.NONE;
    }
}
