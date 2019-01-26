package com.compasso.api.events.handler;

import com.compasso.api.events.Event;

/**
 * This interface can be implemented to handle different types of messages.
 * Every handler is responsible for a single type of message
 * @param <E> Handler can handle events of type E
 */
public interface Handler<E extends Event> {

    /**
     * The onEvent method should implement and handle behavior related to the event.
     * @param event the {@link Event} object to be handled.
     */
    void onEvent(E event);

}
