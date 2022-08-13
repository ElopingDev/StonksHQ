package me.uwu.skybot.event.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.uwu.skybot.event.Event;

@Data
@EqualsAndHashCode(callSuper = true)
public class EventKeyboard extends Event {
    private final int key;
}
