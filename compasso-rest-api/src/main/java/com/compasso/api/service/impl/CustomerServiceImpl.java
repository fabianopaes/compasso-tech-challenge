package com.compasso.api.service.impl;

import com.compasso.api.domain.City;
import com.compasso.api.domain.Customer;
import com.compasso.api.domain.CustomerRepository;
import com.compasso.api.domain.Gender;
import com.compasso.api.domain.payload.CustomerPayload;
import com.compasso.api.events.CustomerCreatedEvent;
import com.compasso.api.events.EventDispatcher;
import com.compasso.api.exception.ResourceNotFoundException;
import com.compasso.api.exception.RulesNotSatisfiedException;
import com.compasso.api.service.CityService;
import com.compasso.api.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private EventDispatcher eventDispatcher;

    private CustomerRepository customerRepository;

    private CityService cityService;

    @Autowired
    public void setEventDispatcher(EventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Autowired
    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

    @Override
    public Optional<Customer> findById(String id) {
        return customerRepository.findById(id);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findByQuery(Map<String, String> query) {
        if (query.containsKey("name")) {
            return customerRepository.findByNameLike(query.get("name").toUpperCase());
        }
        return findAll();
    }

    @Override
    public Customer create(CustomerPayload payload) {
        Customer customer  = new Customer();

        validate(payload);

        customer.setBirthDate(payload.getBirthDate());
        customer.setGender(Gender.get(payload.getGender()));
        customer.setName(payload.getName());
        customer.setCityId(payload.getCityId());

        customerRepository.save(customer);
        eventDispatcher.dispatch(CustomerCreatedEvent.create(customer));

        return customer;
    }

    @Override
    public void update(String id, CustomerPayload payload){
        Optional<Customer> customer = findById(id);
        if (! customer.isPresent()){
            throw new ResourceNotFoundException("customer not found", id);
        }

        validate(payload);

        customer.get().setBirthDate(payload.getBirthDate());
        customer.get().setGender(Gender.get(payload.getGender()));
        customer.get().setName(payload.getName());
        customer.get().setCityId(payload.getCityId());

        customerRepository.save(customer.get());
        eventDispatcher.dispatch(CustomerCreatedEvent.create(customer.get()));

    }

    @Override
    public void delete(String id) {
        Optional<Customer> customer = findById(id);
        if (! customer.isPresent()){
            throw new ResourceNotFoundException("customer not found", id);
        }

        customerRepository.delete(customer.get());
    }

    public void validate(CustomerPayload payload){
        Optional<City> city = cityService.findById(payload.getCityId());
        if (! city.isPresent()){
            throw new RulesNotSatisfiedException("city not found", payload.getCityId());
        }
        if (payload.getBirthDate().after(new Date())){
            throw new RulesNotSatisfiedException("date of birth might be less than current date", payload.getBirthDate().toString());
        }
        if (! Gender.contains(payload.getGender())){
            throw new RulesNotSatisfiedException("Invalid value for gender.", payload.getGender());
        }
    }
}
