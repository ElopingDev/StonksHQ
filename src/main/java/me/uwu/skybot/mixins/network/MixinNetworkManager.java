package me.uwu.skybot.mixins.network;

import fr.shyrogan.post.EventBus;
import io.netty.channel.ChannelHandlerContext;
import me.uwu.skybot.SkyBot;
import me.uwu.skybot.event.impl.EventPacket;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class MixinNetworkManager {
    @Unique
    private static final EventBus BUS = SkyBot.INSTANCE.getEventBus();

    @Inject(
            method = "channelRead0*",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/Packet;processPacket(Lnet/minecraft/network/INetHandler;)V",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    private void packetReceived(
            ChannelHandlerContext p_channelRead0_1_,
            Packet<?> packet,
            CallbackInfo ci
    ) {
        EventPacket event = new EventPacket.Receive(packet);
        BUS.dispatch(event);

        if (event.isCancelled()) ci.cancel();
    }

    @Inject(
            method = "sendPacket(Lnet/minecraft/network/Packet;)V",
            at = @At(
                    "HEAD"
            ),
            cancellable = true
    )
    private void sendPacket(Packet<?> packetIn, CallbackInfo ci) {
        EventPacket event = new EventPacket.Send(packetIn);
        BUS.dispatch(event);

        if (event.isCancelled()) ci.cancel();
    }
}