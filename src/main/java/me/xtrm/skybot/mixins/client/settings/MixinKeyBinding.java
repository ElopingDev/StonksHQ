package me.xtrm.skybot.mixins.client.settings;

import me.xtrm.skybot.accessor.IKeyBinding;
import net.minecraft.client.settings.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(KeyBinding.class)
public class MixinKeyBinding implements IKeyBinding {
    @Shadow
    public boolean pressed;

    @Override
    public boolean skybot$isPressed() {
        return pressed;
    }

    @Override
    public void sb$setPressed(boolean bool) {
        pressed = bool;
    }
}
