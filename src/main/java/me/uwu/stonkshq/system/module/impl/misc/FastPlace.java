package me.uwu.stonkshq.system.module.impl.misc;

import fr.shyrogan.post.receiver.annotation.Subscribe;
import me.uwu.stonkshq.event.impl.EventTick;
import me.uwu.stonkshq.mixin.accessor.IMinecraft;
import me.uwu.stonkshq.system.module.Category;
import me.uwu.stonkshq.system.module.Module;

@Module.Metadata(
        id = "fast-place",
        category = Category.MISCELLANEOUS
)
public class FastPlace extends Module {
    @Subscribe
    public void onTick(EventTick.Pre tick) {
        ((IMinecraft) MC).sb$setRightClickDelayTimer(0);
    }
}
