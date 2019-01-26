package com.compasso.api.service;

import com.compasso.api.domain.State;

import java.util.List;
import java.util.Optional;

public interface StateService {

    Optional<State> findById(String id);

    List<State> findAll();

    Optional<State> findByShortname(String shortname);

}
