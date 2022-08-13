package me.uwu.skybot.event.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.uwu.skybot.event.Cancellable;
import me.uwu.skybot.event.Event;
import net.minecraft.network.Packet;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Cancellable
public class EventPacket extends Event {
    private Packet<?> packet;

    public static class Recieve extends EventPacket {
        public Recieve(Packet<?> packet) {
            super(packet);
        }
    }

    public static class Send extends EventPacket {
        public Send(Packet<?> packet) {
            super(packet);
        }
    }
}
