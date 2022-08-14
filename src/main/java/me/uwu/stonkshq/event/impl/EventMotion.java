package me.uwu.stonkshq.event.impl;

import lombok.*;
import me.uwu.stonkshq.event.Cancellable;
import me.uwu.stonkshq.event.Event;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EventMotion extends Event {
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private boolean ground;
    private boolean sprinting;
    private boolean sneaking;

    @Cancellable
    public static class Pre extends EventMotion {
        public Pre(double posX, double posY, double posZ, float yaw, float pitch, boolean onGround, boolean sprinting, boolean sneaking) {
            super(posX, posY, posZ, yaw, pitch, onGround, sprinting, sneaking);
        }
    }

    public static class Post extends EventMotion {
        @Getter @Setter private boolean silent;
        public Post(double posX, double posY, double posZ, float yaw, float pitch, boolean onGround, boolean sprinting, boolean sneaking) {
            super(posX, posY, posZ, yaw, pitch, onGround, sprinting, sneaking);
            this.silent = true;
        }
    }
}
