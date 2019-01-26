package com.compasso.api.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

    @Query(value = "{'name': {$regex : ?0, $options: 'i'}}")
    List<Customer> findByNameLike(String name);
}
