package com.compasso.api.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends MongoRepository<State, String> {

    @Query("{ 'shortname' : ?0}")
    State findByShortname(String shortname);
}
