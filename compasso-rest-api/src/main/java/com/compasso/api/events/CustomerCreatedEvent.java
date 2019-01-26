package com.compasso.api.events;

import com.compasso.api.domain.Customer;

public class CustomerCreatedEvent extends AbstractEvent {

    private Customer customer;

    public static CustomerCreatedEvent create(Customer customer) {
        CustomerCreatedEvent event = new CustomerCreatedEvent();
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
