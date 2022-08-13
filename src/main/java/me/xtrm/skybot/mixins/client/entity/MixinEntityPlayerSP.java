package me.xtrm.skybot.mixins.client.entity;

import fr.shyrogan.post.EventBus;
import me.uwu.skybot.SkyBot;
import me.uwu.skybot.event.impl.EventMotion;
import me.uwu.skybot.event.impl.EventUpdate;
import me.xtrm.skybot.mixins.entity.MixinEntity;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends MixinEntity {
    @Unique
    private static final EventBus BUS = SkyBot.INSTANCE.getEventBus();

    @Unique
    private double cachedX;
    @Unique
    private double cachedY;
    @Unique
    private double cachedZ;
    @Unique
    private float cachedRotationPitch;
    @Unique
    private float cachedRotationYaw;
    @Unique
    private boolean cachedGround;
    @Unique
    private boolean cachedSprint;
    @Unique
    private boolean cachedSneak;

    @Inject(
            method = "onUpdateWalkingPlayer",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onUpdateWalkingPlayerPre(CallbackInfo ci) {
        cachedX = posX;
        cachedY = posY;
        cachedZ = posZ;

        cachedRotationYaw = rotationYaw;
        cachedRotationPitch = rotationPitch;

        cachedGround = onGround;
        cachedSprint = isSprinting();
        cachedSneak = isSneaking();

        EventMotion event = new EventMotion.Pre(
                posX,
                posY,
                posZ,
                rotationYaw,
                rotationPitch,
                onGround,
                isSprinting(),
                isSneaking()
        );

        BUS.dispatch(event);
        if (event.isCancelled()) {
            ci.cancel();
            return;
        }

        posX = event.getX();
        posY = event.getY();
        posZ = event.getZ();
        rotationYaw = event.getYaw();
        rotationPitch = event.getPitch();
        onGround = event.isGround();
        if (cachedSprint != event.isSprinting()) {
            setSprinting(!cachedSprint);
        }
        if (cachedSneak != event.isSneaking()) {
            setSneaking(!cachedSneak);
        }
    }

    @Inject(
            method = "onUpdateWalkingPlayer",
            at = @At("RETURN")
    )
    private void onUpdateWalkingPlayerPost(CallbackInfo ci) {
        EventMotion.Post event = new EventMotion.Post(
                posX,
                posY,
                posZ,
                rotationYaw,
                rotationPitch,
                onGround,
                isSprinting(),
                isSneaking()
        );

        BUS.dispatch(event);
        if (!event.isSilent())
            return;

        posX = cachedX;
        posY = cachedY;
        posZ = cachedZ;
        rotationYaw = cachedRotationYaw;
        rotationPitch = cachedRotationPitch;
        onGround = cachedGround;
        setSprinting(cachedSprint);
        setSneaking(cachedSneak);
    }

    @Inject(
            method = "onUpdate",
            at = @At("HEAD"),
            cancellable = true
    )
    public void onUpdatePre(CallbackInfo callbackInfo) {
        if (this.worldObj.isBlockLoaded(new BlockPos(this.posX, 0.0, this.posZ))) {
            EventUpdate eventUpdate = new EventUpdate.Pre();
            BUS.dispatch(eventUpdate);
            if (eventUpdate.isCancelled())
                callbackInfo.cancel();
        }
    }

    @Inject(
            method = "onUpdate",
            at = @At("RETURN")
    )
    public void onUpdatePost(CallbackInfo callbackInfo) {
        BUS.dispatch(new EventUpdate.Post());
    }
}
