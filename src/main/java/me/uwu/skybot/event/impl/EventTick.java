package me.uwu.skybot.event.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.uwu.skybot.event.Cancellable;
import me.uwu.skybot.event.Event;

@Data
@EqualsAndHashCode(callSuper = true)
@Cancellable
public class EventTick extends Event {
}
