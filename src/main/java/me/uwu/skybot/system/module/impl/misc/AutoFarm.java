package me.uwu.skybot.system.module.impl.misc;

import fr.shyrogan.post.receiver.annotation.Subscribe;
import me.uwu.skybot.SkyBot;
import me.uwu.skybot.event.impl.EventRender;
import me.uwu.skybot.event.impl.EventUpdate;
import me.xtrm.skybot.system.module.Category;
import me.xtrm.skybot.system.module.Module;
import me.xtrm.skybot.utils.RenderUtils;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.lwjgl.opengl.GL11;

@Module.Metadata(
        id = "auto-farm",
        category = Category.MISCELLANEOUS
)
public class AutoFarm extends Module {
    @Subscribe
    public void onUpdate(EventUpdate.Pre eventUpdate) {
        EnumFacing facing = EnumFacing.values()[SkyBot.INSTANCE.getConfig().enumFacingOrd + 2];

        IBlockState rightBlock = mc.theWorld.getBlockState(mc.thePlayer.getPosition().add(facing.getDirectionVec().getZ() + 0.5f, 0, facing.getDirectionVec().getX() + 0.5f));
        IBlockState bottomBlock = mc.theWorld.getBlockState(mc.thePlayer.getPosition().add(0, -0.8f, 0)); // Nyaa~

        mc.thePlayer.rotationYaw = facing.getHorizontalIndex() * 90;
        mc.thePlayer.rotationPitch = -3.1f;
    }

    @Subscribe
    public void onRender3D(EventRender.R3D eventRender) {
        EnumFacing facing = EnumFacing.values()[SkyBot.INSTANCE.getConfig().enumFacingOrd + 2];

        BlockPos bussy = mc.thePlayer.getPosition().add(facing.getDirectionVec().getZ() + 0.5f, 0, facing.getDirectionVec().getX() + 0.5f);
        AxisAlignedBB bb = new AxisAlignedBB(bussy.getX() - 0.5f, bussy.getY() - 0.5f, bussy.getZ() - 0.5f, bussy.getX() + 0.5f, bussy.getY() + 0.5f, bussy.getZ() + 0.5f);

        GL11.glPushMatrix();
        GL11.glColor4d(1, 1, 1, 1);
        RenderUtils.init3D();
        RenderUtils.drawOutlinedBox(bb);

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();

        RenderUtils.reset3D();
        GL11.glPopMatrix();
    }
}
