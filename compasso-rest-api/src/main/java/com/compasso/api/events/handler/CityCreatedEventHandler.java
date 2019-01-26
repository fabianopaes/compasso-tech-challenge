package com.compasso.api.events.handler;

import com.compasso.api.events.CityCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CityCreatedEventHandler implements Handler<CityCreatedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CityCreatedEvent.class);

    /**
     *  This is a dummy handler for a while, you can use it to send message to receipt.
     *
     *  At this point you use some async process to send to receipt, i.e some pub/sub way
     * @param event the {@link Event} object to be handled.
     */
    @Override
    public void onEvent(CityCreatedEvent event) {
        LOGGER.info("City '{}' has ben created", event.getName());
    }

}
