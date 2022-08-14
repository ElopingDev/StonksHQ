package me.uwu.skybot.system.module.impl.misc;

import fr.shyrogan.post.receiver.annotation.Subscribe;
import me.uwu.skybot.event.impl.EventTick;
import me.xtrm.skybot.accessor.IMinecraft;
import me.xtrm.skybot.system.module.Category;
import me.xtrm.skybot.system.module.Module;

@Module.Metadata(
        id = "fast-place",
        category = Category.MISCELLANEOUS
)
public class FastPlace extends Module {
    @Subscribe
    public void onTick(EventTick.Pre tick) {
        ((IMinecraft)mc).sb$setRightClickDelayTimer(0);
    }
}
