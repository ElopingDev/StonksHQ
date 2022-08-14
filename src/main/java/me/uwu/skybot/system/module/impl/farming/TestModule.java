package me.uwu.skybot.system.module.impl.farming;

import fr.shyrogan.post.receiver.annotation.Subscribe;
import me.uwu.skybot.event.impl.EventPacket;
import me.uwu.skybot.event.impl.EventRender;
import me.uwu.skybot.event.impl.EventTick;
import me.uwu.skybot.event.impl.EventUpdate;
import me.uwu.skybot.system.module.Category;
import me.uwu.skybot.system.module.Module;

@Module.Metadata(id = "x", category = Category.FARMING)
public class TestModule extends Module {
    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Subscribe
    public void onUpdate(EventTick e) {

    }

    @Subscribe
    public void onPre(EventUpdate.Pre e) {

    }

    @Subscribe
    public void onPre(EventUpdate.Post e) {

    }

    @Subscribe
    public void onRender2D(EventRender.R2D e) {

    }

    @Subscribe
    public void onRender3D(EventRender.R3D e) {

    }


    @Subscribe
    public void onPacket(EventPacket.Receive e) {

    }
}
