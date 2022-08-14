package me.uwu.stonkshq.event.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import me.uwu.stonkshq.event.Cancellable;
import me.uwu.stonkshq.event.Event;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

@Data
@Cancellable
@EqualsAndHashCode(callSuper = true)
public class EventRender extends Event {
    private final float partialTicks;

    public static class R2D extends EventRender {
        @Getter
        private final ScaledResolution scaledResolution;

        public R2D(ScaledResolution scaledResolution, float partialTicks) {
            super(partialTicks);
            this.scaledResolution = scaledResolution;
        }

        public R2D(float partialTicks) {
            this(new ScaledResolution(Minecraft.getMinecraft()), partialTicks);
        }
    }

    public static class R3D extends EventRender {
        public R3D(float partialTicks) {
            super(partialTicks);
        }
    }
}
