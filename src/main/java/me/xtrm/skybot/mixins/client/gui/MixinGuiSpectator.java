package me.xtrm.skybot.mixins.client.gui;

import fr.shyrogan.post.EventBus;
import me.uwu.skybot.SkyBot;
import me.uwu.skybot.event.impl.EventRender;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.ScaledResolution;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiSpectator.class)
public class MixinGuiSpectator {
    @Unique
    private static final EventBus BUS = SkyBot.INSTANCE.getEventBus();

    @Inject(method = "renderTooltip", at = @At("RETURN"))
    public void renderTooltip(ScaledResolution sr, float partialTicks, CallbackInfo ci) {
        BUS.dispatch(new EventRender.R2D(sr, partialTicks));
    }
}