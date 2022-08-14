package me.uwu.stonkshq.event.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.uwu.stonkshq.event.Event;

@Data
@EqualsAndHashCode(callSuper = true)
public class EventKeyboard extends Event {
    private final int key;
}
