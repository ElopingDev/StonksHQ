package me.uwu.skybot.system.module.impl.misc;

import fr.shyrogan.post.receiver.annotation.Subscribe;
import me.uwu.skybot.event.impl.EventUpdate;
import me.xtrm.skybot.system.module.Category;
import me.xtrm.skybot.system.module.Module;
import org.lwjgl.input.Keyboard;

@Module.Metadata(
        id = "auto-jump",
        keybind = Keyboard.KEY_P,
        category = Category.MISCELLANEOUS
)
public class AutoJump extends Module {
    @Subscribe
    public void onUpdate(EventUpdate.Pre eventUpdate) {
        if (mc.thePlayer.onGround && mc.thePlayer.jumpTicks == 0) {
            mc.thePlayer.jump();
        }
    }
}
