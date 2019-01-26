package com.compasso.api.repository;

import com.compasso.api.config.MongoConfig;
import com.compasso.api.domain.State;
import com.compasso.api.domain.StateRepository;
import com.compasso.api.support.DomainFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoConfig.class)
public class StateRepositoryTest {

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private MongoOperations mongoOps;

    @Before
    public void testSetup() {
        if (!mongoOps.collectionExists(State.class)) {
            mongoOps.createCollection(State.class);
        }
    }

    @After
    public void tearDown() {
        mongoOps.dropCollection(State.class);
    }

    @Test
    public void shouldBeAbleToRetrieveStateByShortName(){
        State state = DomainFactory.createState("other state", "OS");
        stateRepository.save(state);
        State fromDb = stateRepository.findByShortname("OS");
        assertEquals(state.getId(), fromDb.getId());
        assertEquals(state.getName(), fromDb.getName());
        assertEquals(state.getShortname(), fromDb.getShortname());
        assertEquals(state.getCreatedAt(), fromDb.getCreatedAt());
        assertEquals(state.getVersion(),fromDb.getVersion());
    }

}
