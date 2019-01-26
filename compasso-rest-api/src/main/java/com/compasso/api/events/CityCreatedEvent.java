package com.compasso.api.events;

import java.util.Date;

public class CityCreatedEvent extends AbstractEvent {

    private String name;
    private String id;
    private Date datetime;


    public static CityCreatedEvent create(String name, String id, Date datetime) {
        CityCreatedEvent event = new CityCreatedEvent();
        event.name = name;
        event.id = id;
        event.datetime = datetime;
        return event;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Date getDatetime() {
        return datetime;
    }
}
