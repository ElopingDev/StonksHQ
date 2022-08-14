package me.uwu.stonkshq.mixin.impl.client.gui;

import fr.shyrogan.post.EventBus;
import me.uwu.stonkshq.StonksHQ;
import me.uwu.stonkshq.event.impl.EventRender;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public class MixinGuiIngame {
    @Unique
    private static final EventBus BUS = StonksHQ.INSTANCE.getEventBus();

    @Inject(
            method = "renderTooltip",
            at = @At("RETURN"),
            cancellable = true
    )
    public void renderHotbar(ScaledResolution sr, float partialTicks, CallbackInfo ci) {
        EventRender eventRender = new EventRender.R2D(sr, partialTicks);
        BUS.dispatch(eventRender);
        if (eventRender.isCancelled())
            ci.cancel();
    }
}