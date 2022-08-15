package me.uwu.stonkshq.mixin.impl.client.gui;

import gg.essential.api.EssentialAPI;
import me.uwu.stonkshq.StonksHQ;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.EnumChatFormatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiDisconnected.class)
public class MixinGuiDisconnected extends MixinGuiScreen {
    private static final String IP = "eu.hypixel.net";

    @Shadow
    @Final
    private GuiScreen parentScreen;

    @Inject(
            method = "initGui",
            at = @At(
                    value = "RETURN"
            )
    )
    public void hookButtons(CallbackInfo callbackInfo) {
        if (StonksHQ.INSTANCE.isInitialized() && StonksHQ.INSTANCE.getConfig().autoReconnect) {
            EssentialAPI.getNotifications().push(
                    "Auto-Reconnect",
                    "Reconnecting to " + EnumChatFormatting.LIGHT_PURPLE + IP
            );

            mc.displayGuiScreen(
                    new GuiConnecting(
                            this.parentScreen,
                            this.mc,
                            mc.getCurrentServerData() == null
                                    ? new ServerData("Hypixel", IP, false)
                                    : mc.getCurrentServerData()
                    )
            );
        }
    }
}
