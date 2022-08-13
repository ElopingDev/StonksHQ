package me.xtrm.skybot.mixins.client;

import me.uwu.skybot.SkyBot;
import me.uwu.skybot.event.impl.EventKeyboard;
import me.uwu.skybot.event.impl.EventTick;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Shadow
    public GuiScreen currentScreen;

    @Inject(
            method = "runTick",
            at = @At(
                    "HEAD"
            ),
            cancellable = true
    )
    private void onTick(CallbackInfo callbackInfo) {
        EventTick eventTick = new EventTick();
        SkyBot.INSTANCE.getEventBus().dispatch(eventTick);
        if (eventTick.isCancelled()) callbackInfo.cancel();
    }

    @Inject(
            method = "runTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Minecraft;dispatchKeypresses()V",
                    shift = At.Shift.AFTER
            )
    )
    private void onKey(CallbackInfo ci) {
        if (currentScreen == null && Keyboard.getEventKeyState()) {
            SkyBot.INSTANCE.getEventBus().dispatch(
                    new EventKeyboard(
                            Keyboard.getEventKey() == 0
                                    ? Keyboard.getEventCharacter() + 256
                                    : Keyboard.getEventKey()
                    )
            );
        }
    }
}
