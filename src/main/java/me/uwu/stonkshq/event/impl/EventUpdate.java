package me.uwu.stonkshq.event.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.uwu.stonkshq.event.Cancellable;
import me.uwu.stonkshq.event.Event;

@Data
@EqualsAndHashCode(callSuper = true)
public class EventUpdate extends Event {
    @Cancellable
    public static class Pre extends EventUpdate {}
    public static class Post extends EventUpdate {}
}
