package me.uwu.stonkshq.event.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.uwu.stonkshq.event.Cancellable;
import me.uwu.stonkshq.event.Event;
import net.minecraft.network.Packet;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Cancellable
public class EventPacket extends Event {
    private Packet<?> packet;

    public static class Receive extends EventPacket {
        public Receive(Packet<?> packet) {
            super(packet);
        }
    }

    public static class Send extends EventPacket {
        public Send(Packet<?> packet) {
            super(packet);
        }
    }
}
