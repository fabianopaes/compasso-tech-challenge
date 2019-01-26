package com.compasso.api.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends MongoRepository<City, String> {

    @Query(value = "{'name': {$regex : ?0, $options: 'i'}}")
    List<City> findByNameLike(String name);

    @Query("{ 'state' : ?0}")
    List<City> findByState(String name);

    @Query("{ 'state' : ?0, 'name': {$regex: ?1, $options: 'i' }}")
    List<City> findByStateAndNameLike(String state, String name);

}
