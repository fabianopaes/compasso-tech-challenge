package com.compasso.api.events;

import com.compasso.api.domain.Customer;
import com.compasso.api.support.DomainFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerUpdatedEventTest {

    @Test
    public void testGetEventType() {
        Customer customer = DomainFactory.createCustomer();
        CustomerUpdatedEvent event =  CustomerUpdatedEvent.create(customer);
        assertEquals(CustomerUpdatedEvent.class, event.getType());
    }

}
