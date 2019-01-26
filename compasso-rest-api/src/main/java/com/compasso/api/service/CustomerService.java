package com.compasso.api.service;

import com.compasso.api.domain.Customer;
import com.compasso.api.domain.payload.CustomerPayload;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CustomerService {

    Optional<Customer> findById(String id);

    List<Customer> findAll();

    List<Customer> findByQuery(Map<String, String>query);

    Customer create(CustomerPayload customer);

    void update(String ig, CustomerPayload customer);

    void delete(String id);
}
