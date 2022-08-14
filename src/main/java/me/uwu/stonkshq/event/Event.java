package me.uwu.stonkshq.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public abstract class Event {
    private boolean cancelled;

    public void setCancelled(boolean cancelled) {
        if (!isCancellable())
            throw new UnsupportedOperationException();
        this.cancelled = cancelled;
    }

    public final boolean isCancellable() {
        Class<?> clazz = this.getClass();
        while (clazz != Object.class) {
            if (clazz.getDeclaredAnnotation(Cancellable.class) != null)
                return true;
            clazz = clazz.getSuperclass();
        }
        return false;
    }
}
