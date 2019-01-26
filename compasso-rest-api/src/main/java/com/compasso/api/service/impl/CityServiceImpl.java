package com.compasso.api.service.impl;

import com.compasso.api.domain.City;
import com.compasso.api.domain.CityRepository;
import com.compasso.api.domain.State;
import com.compasso.api.domain.payload.CityPayload;
import com.compasso.api.events.CityCreatedEvent;
import com.compasso.api.events.EventDispatcher;
import com.compasso.api.exception.ResourceNotFoundException;
import com.compasso.api.exception.RulesNotSatisfiedException;
import com.compasso.api.service.CityService;
import com.compasso.api.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    private EventDispatcher eventDispatcher;

    private CityRepository cityRepository;

    private StateService stateService;

    @Autowired
    public void setStateService(StateService stateService) {
        this.stateService = stateService;
    }

    @Autowired
    public void setEventDispatcher(EventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }

    @Autowired
    public void setCityRepository(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public Optional<City> findById(String id) {
        return cityRepository.findById(id);
    }

    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }


    @Override
    public List<City> findByQuery(Map<String, String> query) {
        if (query.isEmpty()) {
            return findAll();
        }

        if (query.containsKey("state") && query.containsKey("name")){
            return cityRepository.findByStateAndNameLike(
                    query.get("state").toUpperCase(),
                    query.get("name").toUpperCase()
            );
        }

        if (query.containsKey("state")){
            return cityRepository.findByState(query.get("state").toUpperCase());
        }

        if (query.containsKey("name")){
            return cityRepository.findByNameLike(query.get("name").toUpperCase());
        }

        return findAll();
    }

    @Override
    public City create(CityPayload payload) throws RulesNotSatisfiedException {
        City city = new City();
        Optional<State> state = stateService.findByShortname(payload.getState());
        if (! state.isPresent()) {
            throw new RulesNotSatisfiedException("state not found", payload.getState());
        }

        city.setName(payload.getName());
        city.setState(state.get().getShortname());

        cityRepository.save(city);

        // Since the city has been created we trigger an event that could be catch and handle as any need
        eventDispatcher.dispatch(CityCreatedEvent.create(city.getName(), city.getId(), city.getCreatedAt()));

        return city;
    }

    @Override
    public void update(String id, CityPayload payload) throws RulesNotSatisfiedException, ResourceNotFoundException {
        Optional<City> city = findById(id);
        if (! city.isPresent()) {
            throw new ResourceNotFoundException("city was not found", id);
        }

        if (! payload.getState().equalsIgnoreCase(city.get().getState())){
            Optional<State> state = stateService.findByShortname(payload.getState());
            if (! state.isPresent()) {
                throw new RulesNotSatisfiedException("state not found", payload.getState());
            }
            city.get().setState(state.get().getShortname());

        }

        city.get().setName(payload.getName());
        cityRepository.save(city.get());
    }


}
