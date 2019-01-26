package com.compasso.api;

import com.compasso.api.events.CityCreatedEvent;
import com.compasso.api.events.CustomerCreatedEvent;
import com.compasso.api.events.CustomerUpdatedEvent;
import com.compasso.api.events.EventDispatcher;
import com.compasso.api.events.handler.CityCreatedEventHandler;
import com.compasso.api.events.handler.CustomerCreatedEventHandler;
import com.compasso.api.events.handler.CustomerUpdatedEventHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
@EnableDiscoveryClient
public class AppInitializer {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(AppInitializer.class, args);

        context.getBean(EventDispatcher.class)
                .registerHandler(CityCreatedEvent.class, new CityCreatedEventHandler());
        context.getBean(EventDispatcher.class)
                .registerHandler(CustomerCreatedEvent.class, new CustomerCreatedEventHandler());
        context.getBean(EventDispatcher.class)
                .registerHandler(CustomerUpdatedEvent.class, new CustomerUpdatedEventHandler());
    }

}
