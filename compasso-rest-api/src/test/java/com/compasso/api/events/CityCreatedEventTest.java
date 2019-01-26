package com.compasso.api.events;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class CityCreatedEventTest {

    @Test
    public void testGetEventType() {
        CityCreatedEvent event =  CityCreatedEvent.create("florianopolis", "124323in4up12n4u12", new Date());
        assertEquals(CityCreatedEvent.class, event.getType());
    }
}
