package com.compasso.api.support;

import com.compasso.api.domain.City;
import com.compasso.api.domain.Customer;
import com.compasso.api.domain.Gender;
import com.compasso.api.domain.State;
import com.compasso.api.domain.payload.CityPayload;
import com.compasso.api.domain.payload.CustomerPayload;

import java.util.Date;

public class DomainFactory {

    public static State createState(String name, String shortName){
        State state = new State();
        state.setName(name);
        state.setShortname(shortName);
        return state;
    }

    public static City createCity(String name, String state){
        City city = new City();
        city.setName(name);
        city.setState(state);
        return city;
    }

    public static CityPayload createCityPayload(){
        CityPayload payload = new CityPayload();
        payload.setName("floripa");
        payload.setState("SC");
        return payload;
    }

    public static City createFullCity(String name, String state){
        City city = createCity(name, state);
        city.setCreatedAt(new Date());
        city.setUpdatedAt(city.getCreatedAt());
        city.setId("11");
        city.setVersion(1L);
        return city;
    }
    public static Customer createCustomer(){
        Customer customer = new Customer();
        customer.setCityId("101010");
        customer.setBirthDate(new Date());
        customer.setGender(Gender.MALE);
        customer.setName("customer-01");
        return customer;
    }

    public static CustomerPayload customerPayload(){
        CustomerPayload customer = new CustomerPayload();
        customer.setCityId("101010");
        customer.setBirthDate(new Date());
        customer.setGender(Gender.MALE.name());
        customer.setName("customer-01");
        return customer;
    }

    public static CustomerPayload customerPayload(String name, String cityId){
        CustomerPayload customer = new CustomerPayload();
        customer.setCityId(cityId);
        customer.setBirthDate(new Date());
        customer.setGender(Gender.MALE.name());
        customer.setName(name);
        return customer;
    }
}
