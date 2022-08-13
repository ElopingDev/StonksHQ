package me.uwu.skybot.system.module.impl.misc;

import fr.shyrogan.post.receiver.annotation.Subscribe;
import me.uwu.skybot.SkyBot;
import me.uwu.skybot.event.impl.EventUpdate;
import me.xtrm.skybot.system.module.Category;
import me.xtrm.skybot.system.module.Module;
import net.minecraft.util.EnumFacing;

@Module.Metadata(
        id = "auto-farm",
        category = Category.MISCELLANEOUS
)
public class AutoFarm extends Module {
    @Subscribe
    public void onUpdate(EventUpdate.Pre eventUpdate) {
        EnumFacing facing = EnumFacing.values()[SkyBot.INSTANCE.getConfig().enumFacingOrd + 2];
        mc.thePlayer.rotationYaw = facing.getHorizontalIndex() * 90;
        mc.thePlayer.rotationPitch = -3.1f;
    }
}
