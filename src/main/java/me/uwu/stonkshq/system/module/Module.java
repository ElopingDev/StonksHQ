package me.uwu.stonkshq.system.module;

import fr.shyrogan.post.EventBus;
import me.uwu.stonkshq.StonksHQ;
import me.uwu.stonkshq.api.ui.font.IFontRenderer;
import me.uwu.stonkshq.ui.font.FontManager;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Module {
    private static final EventBus BUS =
            StonksHQ.INSTANCE.getEventBus();
    protected static final Minecraft MC =
            Minecraft.getMinecraft();
    protected static final IFontRenderer MC_FONT =
            FontManager.INSTANCE.getFontRenderer("minecraft");
    protected static final IFontRenderer CLIENT_FONT =
            FontManager.INSTANCE.getFontRenderer("Honeybee", 17);

    private boolean state;

    public final void toggle() {
        this.setEnabled(!this.state);
    }

    public boolean isEnabled() {
        return this.state;
    }

    public void setEnabled(boolean state) {
        this.state = state;

        onToggle();

        if (state) {
            onEnable();
            onEnabled();
        } else {
            onDisable();
            onDisabled();
        }
    }

    protected void onToggle() {
    }

    protected void onEnable() {
    }

    protected void onDisable() {
    }

    private void onEnabled() {
        BUS.subscribe(this);
    }

    private void onDisabled() {
        BUS.unsubscribe(this);
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Metadata {
        String id();

        Category category();

        int keybind() default Keyboard.KEY_NONE;

        boolean defaultState() default false;
    }
}
