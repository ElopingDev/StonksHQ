package me.uwu.stonkshq.system.module.impl.farming;

import fr.shyrogan.post.receiver.annotation.Subscribe;
import gg.essential.api.EssentialAPI;
import me.uwu.stonkshq.StonksHQ;
import me.uwu.stonkshq.event.impl.EventPacket;
import me.uwu.stonkshq.event.impl.EventRender;
import me.uwu.stonkshq.event.impl.EventTick;
import me.uwu.stonkshq.event.impl.EventUpdate;
import me.uwu.stonkshq.mixin.accessor.IKeyBinding;
import me.uwu.stonkshq.struct.BotDirection;
import me.uwu.stonkshq.system.module.Category;
import me.uwu.stonkshq.system.module.Module;
import me.uwu.stonkshq.utils.Renderer;
import me.uwu.stonkshq.utils.SkyBlockUtils;
import net.minecraft.block.Block;
import net.minecraft.network.play.server.S07PacketRespawn;
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
    private boolean canWork = false;

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
        MC.thePlayer.rotationYaw = (EnumFacing.values()[StonksHQ.INSTANCE.getConfig().enumFacingOrd + 2].getHorizontalIndex() * 90);
        MC.thePlayer.rotationPitch = -3.1f;
        canWork = false;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        ((IKeyBinding) MC.gameSettings.keyBindForward).sb$setPressed(false);
        ((IKeyBinding) MC.gameSettings.keyBindSneak).sb$setPressed(false);
        ((IKeyBinding) MC.gameSettings.keyBindBack).sb$setPressed(false);
        ((IKeyBinding) MC.gameSettings.keyBindLeft).sb$setPressed(false);
        ((IKeyBinding) MC.gameSettings.keyBindRight).sb$setPressed(false);
        ((IKeyBinding) MC.gameSettings.keyBindAttack).sb$setPressed(false);
    }

    @Subscribe
    public void onUpdate(EventTick e) {

    }

    @Subscribe
    public void onPre(EventUpdate.Pre e) {
        if (MC.thePlayer.ticksExisted % 36000 == 35998)
            MC.thePlayer.sendChatMessage("/lobby");

        if (canWork) {
            BlockPos bottomPos = new BlockPos(
                    MC.thePlayer.posX,
                    MC.thePlayer.posY - 0.5f,
                    MC.thePlayer.posZ
            );

            Block bottomBlock = MC.theWorld.getBlockState(bottomPos).getBlock();

            ((IKeyBinding) MC.gameSettings.keyBindForward).sb$setPressed(false);
            ((IKeyBinding) MC.gameSettings.keyBindLeft).sb$setPressed(false);
            ((IKeyBinding) MC.gameSettings.keyBindRight).sb$setPressed(false);
            ((IKeyBinding) MC.gameSettings.keyBindSneak).sb$setPressed(false);

            if (getBestDirection() == BotDirection.FORWARD) {
                MC.thePlayer.rotationYaw = (EnumFacing.values()[StonksHQ.INSTANCE.getConfig().enumFacingOrd + 2].getHorizontalIndex() * 90);
                ((IKeyBinding) MC.gameSettings.keyBindSneak).sb$setPressed(true);
                ((IKeyBinding) MC.gameSettings.keyBindForward).sb$setPressed(true);
            } else if (getBestDirection() == BotDirection.LEFT)
                ((IKeyBinding) MC.gameSettings.keyBindLeft).sb$setPressed(true);
            else if (getBestDirection() == BotDirection.RIGHT)
                ((IKeyBinding) MC.gameSettings.keyBindRight).sb$setPressed(true);

            if (dodo <= 0) {
                if ((MC.thePlayer.posY - ((int) MC.thePlayer.posY)) == .8125f) {
                    MC.thePlayer.jump();
                    dodo = 40;
                }
            } else dodo--;
        } else {
            if (MC.thePlayer.ticksExisted % 80 == 0) {
                SkyBlockUtils.IslandType islandType = SkyBlockUtils.getCurrentIslandType(MC);
                if (islandType == SkyBlockUtils.IslandType.OWN_ISLAND) {
                    canWork = true;
                } else {
                    if (islandType == SkyBlockUtils.IslandType.HYPIXEL_LOBBY || islandType == SkyBlockUtils.IslandType.UNKNOWN) {
                        MC.thePlayer.sendChatMessage("/play skyblock");
                    } else if (islandType == SkyBlockUtils.IslandType.HUB_ISLAND) {
                        MC.thePlayer.sendChatMessage("/is");

                    } else {
                        MC.thePlayer.sendChatMessage("/lobby");
                    }
                }
            }
        }
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
        Renderer.drawBlock(pos[0], getBlockColor(pos[0]));
        Renderer.drawBlock(pos[1], getBlockColor(pos[1]));
        Renderer.drawBlock(pos[2], getBlockColor(pos[2]));

        BlockPos bottomPos = new BlockPos(
                MC.thePlayer.posX,
                MC.thePlayer.posY - 0.5f,
                MC.thePlayer.posZ
        );

        Renderer.drawBlock(bottomPos, Color.BLUE);
    }

    @Subscribe
    public void onPacket(EventPacket.Receive e) {
        if (e.getPacket() instanceof S07PacketRespawn)
            canWork = false;
    }

    private BlockPos[] getPositions() {
        BlockPos[] positions = new BlockPos[3];
        EnumFacing[] sides = new EnumFacing[3];
        EnumFacing facing = EnumFacing.values()[StonksHQ.INSTANCE.getConfig().enumFacingOrd + 2];
        BlockPos playerPos = new BlockPos(
                MC.thePlayer.posX,
                MC.thePlayer.posY,
                MC.thePlayer.posZ
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
        return MC.theWorld.getBlockState(pos).getBlock().getUnlocalizedName().toLowerCase().contains("slab") ||
                MC.theWorld.getBlockState(pos).getBlock().getUnlocalizedName().toLowerCase().contains("portal");
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
