package com.compasso.api.service.impl;

import com.compasso.api.domain.State;
import com.compasso.api.domain.StateRepository;
import com.compasso.api.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class StateServiceImpl implements StateService {

    private StateRepository stateRepository;

    @Autowired
    public void setStateRepository(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    public Optional<State> findById(String id) {
        return stateRepository.findById(id);
    }

    @Override
    public List<State> findAll() {
        return stateRepository.findAll();
    }

    @Override
    public Optional<State> findByShortname(String shortname) {
        return Optional.ofNullable(stateRepository.findByShortname(shortname));
    }

}
