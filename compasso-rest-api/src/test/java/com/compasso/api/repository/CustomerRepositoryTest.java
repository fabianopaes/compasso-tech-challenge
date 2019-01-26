package com.compasso.api.repository;

import com.compasso.api.config.MongoConfig;
import com.compasso.api.domain.Customer;
import com.compasso.api.domain.CustomerRepository;
import com.compasso.api.support.DomainFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoConfig.class)
public class CustomerRepositoryTest {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MongoOperations mongoOps;

    @Before
    public void testSetup() {
        if (!mongoOps.collectionExists(Customer.class)) {
            mongoOps.createCollection(Customer.class);
        }
    }

    @After
    public void tearDown() {
        mongoOps.dropCollection(Customer.class);
    }


    @Test
    public void shouldBeAbleToRetrieveCustomerByNameLike(){

        Customer customer = DomainFactory.createCustomer();
        customerRepository.save(customer);

        List<Customer> got = customerRepository.findByNameLike("customer");

        assertEquals("findByNameLike should returns only one item", 1, got.size());

        Customer fromDb = got.get(0);

        assertEquals(customer.getId(), fromDb.getId());
        assertEquals(customer.getName(), fromDb.getName());
        assertEquals(customer.getCreatedAt(), fromDb.getCreatedAt());
        assertEquals(customer.getVersion(),fromDb.getVersion());
    }


    @Test
    public void shouldBeAbleToRetrieveCustomerById(){

        Customer customer = DomainFactory.createCustomer();
        customerRepository.save(customer);

        Optional<Customer> fromDb = customerRepository.findById(customer.getId());

        assertTrue("findById should returns one item", fromDb.isPresent());


        assertEquals(customer.getId(), fromDb.get().getId());
        assertEquals(customer.getName(), fromDb.get().getName());
        assertEquals(customer.getCreatedAt(), fromDb.get().getCreatedAt());
        assertEquals(customer.getVersion(),fromDb.get().getVersion());
    }



    @Test
    public void shouldUpdateTheCustomer(){
        Customer customer = DomainFactory.createCustomer();
        customerRepository.save(customer);

        Optional<Customer> fromDbBefore = customerRepository.findById(customer.getId());
        assertTrue("findById should returns one item", fromDbBefore.isPresent());
        Customer before = fromDbBefore.get();

        customer.setName("just changed");
        customerRepository.save(customer);

        Optional<Customer> fromDbAfter = customerRepository.findById(customer.getId());
        Customer after = fromDbAfter.get();

        assertTrue("should have only one city", customerRepository.findAll().size() == 1 );
        assertEquals("should be the same city", before.getId(), after.getId());
        assertNotEquals("customer name should have been changed", before.getName(), after.getName());
        assertTrue("should inc city version", after.getVersion() > before.getVersion());
    }
}
