package com.compasso.api.events;

import com.compasso.api.domain.Customer;
import com.compasso.api.support.DomainFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerCreatedEventTest {

    @Test
    public void testGetEventType() {
        Customer customer = DomainFactory.createCustomer();
        CustomerCreatedEvent event =  CustomerCreatedEvent.create(customer);
        assertEquals(CustomerCreatedEvent.class, event.getType());
    }

}
