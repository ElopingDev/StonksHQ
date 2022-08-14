package me.uwu.stonkshq.system.module.impl.visual;

import fr.shyrogan.post.receiver.annotation.Subscribe;
import me.uwu.stonkshq.Consts;
import me.uwu.stonkshq.event.impl.EventRender;
import me.uwu.stonkshq.system.module.Category;
import me.uwu.stonkshq.system.module.Module;
import net.minecraft.client.gui.ScaledResolution;

@Module.Metadata(
        id = "hud",
        category = Category.VISUAL,
        defaultState = true
)
public class HUD extends Module {
    @Subscribe
    public void onRender(EventRender.R2D event) {
        ScaledResolution sr = event.getScaledResolution();
        String text = Consts.NAME;
        CLIENT_FONT.drawStringWithShadow(
                text,
                sr.getScaledWidth() - CLIENT_FONT.getStringWidth(text),
                sr.getScaledHeight() - CLIENT_FONT.getFontHeight(),
                0xFF5EE4FF
        );
    }
}
