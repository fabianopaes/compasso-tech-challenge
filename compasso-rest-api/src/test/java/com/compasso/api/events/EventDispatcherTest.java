package com.compasso.api.events;

import com.compasso.api.events.handler.CityCreatedEventHandler;
import com.compasso.api.support.DomainFactory;
import org.junit.Test;

import java.util.Date;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;


public class EventDispatcherTest {

  @Test
  public void testEventDriverPattern() {

    EventDispatcher dispatcher = spy(new EventDispatcher());

    CityCreatedEvent event =  CityCreatedEvent.create("florianopolis", "124323in4up12n4u12", new Date());

    CityCreatedEventHandler handler = spy(new CityCreatedEventHandler());

    dispatcher.registerHandler(CityCreatedEvent.class, handler);

    dispatcher.dispatch(event);
    verify(handler).onEvent(event);
    verify(dispatcher).dispatch(event);

  }

}
