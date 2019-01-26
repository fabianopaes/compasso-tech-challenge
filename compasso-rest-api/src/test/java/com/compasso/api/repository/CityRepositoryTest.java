package com.compasso.api.repository;

import com.compasso.api.config.MongoConfig;
import com.compasso.api.domain.City;
import com.compasso.api.domain.CityRepository;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoConfig.class)
public class CityRepositoryTest {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private MongoOperations mongoOps;

    @Before
    public void testSetup() {
        if (!mongoOps.collectionExists(City.class)) {
            mongoOps.createCollection(City.class);
        }
    }

    @After
    public void tearDown() {
        mongoOps.dropCollection(City.class);
    }

    @Test
    public void shouldBeAbleToRetrieveCityByNameLike(){

        City city = DomainFactory.createCity("City 01", "SC");
        cityRepository.save(city);

        List<City> got = cityRepository.findByNameLike("City");

        assertEquals("findByNameLike should returns only one item", 1, got.size());

        City fromDb = got.get(0);

        assertEquals(city.getId(), fromDb.getId());
        assertEquals(city.getName(), fromDb.getName());
        assertEquals(city.getState(), fromDb.getState());
        assertEquals(city.getCreatedAt(), fromDb.getCreatedAt());
        assertEquals(city.getVersion(),fromDb.getVersion());
    }


    @Test
    public void shouldBeAbleToRetrieveCityById(){

        City city = DomainFactory.createCity("City 01", "SC");
        cityRepository.save(city);

        Optional<City> fromDb = cityRepository.findById(city.getId());

        assertTrue("findById should returns one item", fromDb.isPresent());


        assertEquals(city.getId(), fromDb.get().getId());
        assertEquals(city.getName(), fromDb.get().getName());
        assertEquals(city.getState(), fromDb.get().getState());
        assertEquals(city.getCreatedAt(), fromDb.get().getCreatedAt());
        assertEquals(city.getVersion(),fromDb.get().getVersion());
    }

    @Test
    public void shouldBeAbleToRetrieveCityByState(){

        City city = DomainFactory.createCity("City 01", "SC");
        cityRepository.save(city);

        List<City> got = cityRepository.findByState("SC");

        assertEquals("findByState should returns only one item", 1, got.size());

        City fromDb = got.get(0);

        assertEquals(city.getId(), fromDb.getId());
        assertEquals(city.getName(), fromDb.getName());
        assertEquals(city.getState(), fromDb.getState());
        assertEquals(city.getCreatedAt(), fromDb.getCreatedAt());
        assertEquals(city.getVersion(),fromDb.getVersion());
    }

    @Test
    public void shouldBeAbleToRetrieveCityByStateAndNameLike(){

        City city = DomainFactory.createCity("City 01", "SC");
        cityRepository.save(city);

        List<City> got = cityRepository.findByStateAndNameLike("SC", "Cit");

        assertEquals("findByState should returns only one item", 1, got.size());

        City fromDb = got.get(0);

        assertEquals(city.getId(), fromDb.getId());
        assertEquals(city.getName(), fromDb.getName());
        assertEquals(city.getState(), fromDb.getState());
        assertEquals(city.getCreatedAt(), fromDb.getCreatedAt());
        assertEquals(city.getVersion(),fromDb.getVersion());
    }

    @Test
    public void shouldUpdateTheCity(){
        City city = DomainFactory.createCity("City 01", "SC");
        cityRepository.save(city);

        Optional<City> fromDbBefore = cityRepository.findById(city.getId());
        assertTrue("findById should returns one item", fromDbBefore.isPresent());
        City before = fromDbBefore.get();

        city.setName("just changed");
        cityRepository.save(city);

        Optional<City> fromDbAfter = cityRepository.findById(city.getId());
        City after = fromDbAfter.get();

        assertTrue("should have only one city", cityRepository.findAll().size() == 1 );
        assertEquals("should be the same city", before.getId(), after.getId());
        assertNotEquals("city name should have been changed", before.getName(), after.getName());
        assertTrue("should inc city version", after.getVersion() > before.getVersion());
    }


}
