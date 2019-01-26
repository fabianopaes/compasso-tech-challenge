package com.compasso.api.events;

public class AbstractEvent implements Event {

    /**
     * Returns the event type as a {@link Class} object
     * In this example, this method is used by the {@link EventDispatcher} to
     * dispatch events depending on their type.
     *
     * @return the AbstractEvent type as a {@link Class}.
     */
    public Class<? extends Event> getType() {
        return getClass();
    }
}