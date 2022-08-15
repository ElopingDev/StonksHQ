package me.uwu.stonkshq.system.module.impl.misc;

import fr.shyrogan.post.receiver.annotation.Subscribe;
import me.uwu.stonkshq.event.impl.EventPacket;
import me.uwu.stonkshq.event.impl.EventRender;
import me.uwu.stonkshq.event.impl.EventTick;
import me.uwu.stonkshq.event.impl.EventUpdate;
import me.uwu.stonkshq.system.module.Category;
import me.uwu.stonkshq.system.module.Module;
import me.uwu.stonkshq.utils.SkyBlockUtils;
import net.minecraft.util.ChatComponentText;

@Module.Metadata(id = "x", category = Category.FARMING)
public class TestModule extends Module {
    @Override
    public void onEnable() {
        super.onEnable();
        SkyBlockUtils.IslandType islandType = SkyBlockUtils.getCurrentIslandType(MC);
        MC.thePlayer.addChatMessage(new ChatComponentText(islandType.toString()));
        System.out.println(MC.thePlayer.posX + " " + MC.thePlayer.posY + " " + MC.thePlayer.posZ);
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
