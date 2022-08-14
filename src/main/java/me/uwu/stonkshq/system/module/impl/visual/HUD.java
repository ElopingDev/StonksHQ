package me.uwu.stonkshq.system.module.impl.visual;

import fr.shyrogan.post.receiver.annotation.Subscribe;
import me.uwu.stonkshq.Consts;
import me.uwu.stonkshq.event.impl.EventRender;
import me.uwu.stonkshq.system.module.Category;
import me.uwu.stonkshq.system.module.Module;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;

@Module.Metadata(
        id = "hud",
        category = Category.VISUAL,
        defaultState = true
)
public class HUD extends Module {
    private static final int MAIN_COLOR = 0xFFFF5EE4;

    @Subscribe
    public void onRender(EventRender.R2D event) {
        ScaledResolution sr = event.getScaledResolution();
        @SuppressWarnings("ConstantConditions") String text = ""
                + EnumChatFormatting.BOLD
                + Consts.NAME
                + EnumChatFormatting.GRAY
                + " - "
                + EnumChatFormatting.WHITE
                + "ver "
                + EnumChatFormatting.RESET
                + Consts.VERSION
                + EnumChatFormatting.GRAY
                + " - "
                + EnumChatFormatting.WHITE
                + "build "
                + EnumChatFormatting.RESET
                + Consts.BUILD.substring(0, Math.min(8, Consts.BUILD.length()));
        int x = sr.getScaledWidth() - (CLIENT_FONT.getStringWidth(text) + 4);
        int y = sr.getScaledHeight() - (CLIENT_FONT.getFontHeight() + 2);
        Gui.drawRect(x - 3, y - 3, sr.getScaledWidth(), sr.getScaledHeight(), 0xBF000000);
        Gui.drawRect(x - 4, y - 4, x - 3, sr.getScaledHeight(), MAIN_COLOR);
        Gui.drawRect(x - 4, y - 4, sr.getScaledWidth(), y - 3, MAIN_COLOR);
        CLIENT_FONT.drawStringWithShadow(text, x, y, MAIN_COLOR);
    }
}
