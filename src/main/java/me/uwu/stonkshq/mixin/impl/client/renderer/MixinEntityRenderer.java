package me.uwu.stonkshq.mixin.impl.client.renderer;

import fr.shyrogan.post.EventBus;
import me.uwu.stonkshq.StonksHQ;
import me.uwu.stonkshq.event.impl.EventRender;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {
    @Unique
    private static final EventBus BUS = StonksHQ.INSTANCE.getEventBus();

    @Inject(
            method = "renderWorldPass",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand:Z",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    private void renderWorldPass(int pass, float partialTicks, long finishTimeNano, CallbackInfo ci) {
        EventRender eventRender = new EventRender.R3D(partialTicks);
        BUS.dispatch(eventRender);
        if (eventRender.isCancelled())
            ci.cancel();
    }
}
