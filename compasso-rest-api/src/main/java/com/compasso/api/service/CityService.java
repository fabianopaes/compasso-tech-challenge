package com.compasso.api.service;

import com.compasso.api.domain.City;
import com.compasso.api.domain.payload.CityPayload;
import com.compasso.api.exception.ResourceNotFoundException;
import com.compasso.api.exception.RulesNotSatisfiedException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CityService {

    Optional<City> findById(String id);

    List<City> findByQuery(Map<String, String> query);

    List<City> findAll();

    City create(CityPayload payload) throws RulesNotSatisfiedException;

    void update(String id, CityPayload payload) throws RulesNotSatisfiedException, ResourceNotFoundException;


}
