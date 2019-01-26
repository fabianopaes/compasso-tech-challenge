package com.compasso.api.events.handler;

import com.compasso.api.events.CityCreatedEvent;
import com.compasso.api.events.CustomerCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerCreatedEventHandler implements Handler<CustomerCreatedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerCreatedEvent.class);

    /**
     *  This is a dummy handler for a while, you can use it to send message to receipt.
     *
     *  At this point you use some async process to send to receipt, i.e some pub/sub way
     * @param event the {@link Event} object to be handled.
     */
    @Override
    public void onEvent(CustomerCreatedEvent event) {
        LOGGER.info("Customer '{}' has ben created", event.getCustomer());
    }
}
