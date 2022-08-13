package me.uwu.skybot.system.module.impl.misc;

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

@Module.Metadata(id = "tester", category = Category.MISCELLANEOUS)
public class Tester extends Module {

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Subscribe
    public void onUpdate(EventUpdate.Pre eventUpdate) {
        //mc.rig = 0;
    }

    @Subscribe
    public void onRender2D(EventRender.R2D eventRender) {

    }

    @Subscribe
    public void onRender3D(EventRender.R3D eventRender) {

    }



    @Subscribe
    public void onPacket(EventPacket.Receive eventPacket) {
        //System.out.println(eventPacket.getPacket().getClass().getSimpleName());

    }
}
