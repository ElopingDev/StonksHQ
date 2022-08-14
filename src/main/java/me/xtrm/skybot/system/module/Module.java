package me.xtrm.skybot.system.module;

import fr.shyrogan.post.EventBus;
import me.uwu.skybot.SkyBot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.input.Keyboard;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Module {
    private static final EventBus BUS =
            SkyBot.INSTANCE.getEventBus();
    protected static final Minecraft mc =
            Minecraft.getMinecraft();
    protected static final FontRenderer fr =
            Minecraft.getMinecraft().fontRendererObj;

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
