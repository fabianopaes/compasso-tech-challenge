package com.compasso.api.events;

import com.compasso.api.domain.Customer;

public class CustomerUpdatedEvent  extends AbstractEvent {

    private Customer customer;

    public static CustomerUpdatedEvent create(Customer customer) {
        CustomerUpdatedEvent event = new CustomerUpdatedEvent();
        event.customer = customer;
        return event;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}