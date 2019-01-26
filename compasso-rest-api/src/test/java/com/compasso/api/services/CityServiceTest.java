package com.compasso.api.services;

import com.compasso.api.domain.City;
import com.compasso.api.domain.CityRepository;
import com.compasso.api.domain.State;
import com.compasso.api.domain.payload.CityPayload;
import com.compasso.api.events.EventDispatcher;
import com.compasso.api.exception.ResourceNotFoundException;
import com.compasso.api.exception.RulesNotSatisfiedException;
import com.compasso.api.service.StateService;
import com.compasso.api.service.impl.CityServiceImpl;
import com.compasso.api.support.DomainFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CityServiceTest {

    private CityServiceImpl cityService;
    private CityRepository cityRepositoryMock;
    private StateService stateServiceMock;
    private EventDispatcher eventDispatcher;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void initialize(){
        eventDispatcher = Mockito.spy(EventDispatcher.class);
        cityRepositoryMock = Mockito.mock(CityRepository.class);
        stateServiceMock = Mockito.mock(StateService.class);

        cityService = new CityServiceImpl();
        cityService.setCityRepository(cityRepositoryMock);
        cityService.setEventDispatcher(eventDispatcher);
        cityService.setStateService(stateServiceMock);
    }

    @Test
    public void whenCallsFindAllReturnsAllAvailableCities(){
        City city = DomainFactory.createCity("Floripa", "SC");
        when(cityRepositoryMock.findAll()).thenReturn(Arrays.asList(city));

        List<City> result = cityService.findAll();

        assertEquals("findAll should returns only one item", 1, result.size());
        City singleResult = result.get(0);
        assertEquals("City should be Floripa", city.getName(), singleResult.getName());
        assertEquals("State should be SC", city.getState(), singleResult.getState());
        verify(cityRepositoryMock, times(1)).findAll();
    }


    @Test
    public void whenCallsFindByIdReturnsTheSingleResource(){
        City city = DomainFactory.createCity("Floripa", "SC");

        when(cityRepositoryMock.findById("1010")).thenReturn(Optional.of(city));
        Optional<City> result = cityService.findById("1010");
        assertTrue("findById should returns the item", result.isPresent());
        assertEquals("State should be Floripa", city.getName(), result.get().getName());
        assertEquals("State should be SC", city.getState(), result.get().getState());
        verify(cityRepositoryMock, times(1)).findById("1010");
    }

    @Test
    public void whenCallsFindByQueryWithEmptyQueryCallFindAll(){
        City city = DomainFactory.createCity("Floripa", "SC");
        when(cityRepositoryMock.findAll()).thenReturn(Arrays.asList(city));

        Map<String,String> query = new HashMap<>();

        List<City> result = cityService.findByQuery(query);

        assertEquals("findByQuery should returns only one item", 1, result.size());
        City singleResult = result.get(0);
        assertEquals("City should be Floripa", city.getName(), singleResult.getName());
        assertEquals("State should be SC", city.getState(), singleResult.getState());

        verify(cityRepositoryMock, times(1)).findAll();
        verify(cityRepositoryMock, times(0)).findByNameLike(any());
        verify(cityRepositoryMock, times(0)).findByState(any());
        verify(cityRepositoryMock, times(0)).findByStateAndNameLike(any(), any());

    }

    @Test
    public void whenCallsFindByQueryWithFilteringByStateCallFindByState(){
        City city = DomainFactory.createCity("Floripa", "SC");
        when(cityRepositoryMock.findByState("SC")).thenReturn(Arrays.asList(city));

        Map<String,String> query = new HashMap<>();
        query.put("state", "SC");

        List<City> result = cityService.findByQuery(query);

        assertEquals("findByQuery should returns only one item", 1, result.size());
        City singleResult = result.get(0);
        assertEquals("City should be Floripa", city.getName(), singleResult.getName());
        assertEquals("State should be SC", city.getState(), singleResult.getState());

        verify(cityRepositoryMock, times(0)).findAll();
        verify(cityRepositoryMock, times(0)).findByNameLike(any());
        verify(cityRepositoryMock, times(1)).findByState("SC");
        verify(cityRepositoryMock, times(0)).findByStateAndNameLike(any(), any());

    }

    @Test
    public void whenCallsFindByQueryWithFilteringByNameCallFindByNameLike(){
        City city = DomainFactory.createCity("Floripa", "SC");
        when(cityRepositoryMock.findByNameLike("FLORIPA")).thenReturn(Arrays.asList(city));

        Map<String,String> query = new HashMap<>();
        query.put("name", "Floripa");

        List<City> result = cityService.findByQuery(query);

        assertEquals("findByQuery should returns only one item", 1, result.size());
        City singleResult = result.get(0);
        assertEquals("City should be Floripa", city.getName(), singleResult.getName());
        assertEquals("State should be SC", city.getState(), singleResult.getState());

        verify(cityRepositoryMock, times(0)).findAll();
        verify(cityRepositoryMock, times(1)).findByNameLike("FLORIPA");
        verify(cityRepositoryMock, times(0)).findByState(any());
        verify(cityRepositoryMock, times(0)).findByStateAndNameLike(any(), any());

    }

    @Test
    public void whenCallsFindByQueryWithFilteringByStateAndNameCallFindByStateAndNameLike(){
        City city = DomainFactory.createCity("Floripa", "SC");
        when(cityRepositoryMock.findByStateAndNameLike("SC","FLORIPA")).thenReturn(Arrays.asList(city));

        Map<String,String> query = new HashMap<>();
        query.put("name", "Floripa");
        query.put("state", "SC");


        List<City> result = cityService.findByQuery(query);

        assertEquals("findByQuery should returns only one item", 1, result.size());
        City singleResult = result.get(0);
        assertEquals("City should be Floripa", city.getName(), singleResult.getName());
        assertEquals("State should be SC", city.getState(), singleResult.getState());

        verify(cityRepositoryMock, times(0)).findAll();
        verify(cityRepositoryMock, times(0)).findByNameLike("Floripa");
        verify(cityRepositoryMock, times(0)).findByState(any());
        verify(cityRepositoryMock, times(1)).findByStateAndNameLike( "SC","FLORIPA");

    }

    @Test
    public void whenCallsFindByQueryWithFilteringByUnknownFieldsCallFindAll(){
        City city = DomainFactory.createCity("Floripa", "SC");
        when(cityRepositoryMock.findAll()).thenReturn(Arrays.asList(city));

        Map<String,String> query = new HashMap<>();
        query.put("filter", "Floripa");
        query.put("filter-two", "SC");


        List<City> result = cityService.findByQuery(query);

        assertEquals("findByQuery should returns only one item", 1, result.size());
        City singleResult = result.get(0);
        assertEquals("City should be Floripa", city.getName(), singleResult.getName());
        assertEquals("State should be SC", city.getState(), singleResult.getState());

        verify(cityRepositoryMock, times(1)).findAll();
        verify(cityRepositoryMock, times(0)).findByNameLike(any());
        verify(cityRepositoryMock, times(0)).findByState(any());
        verify(cityRepositoryMock, times(0)).findByStateAndNameLike( any(), any());

    }

    @Test
    public void throwExceptionTryingToSaveTheCityWithUnknownState(){
        when(stateServiceMock.findByShortname(anyString())).thenReturn(Optional.empty());
        expectedException.expect(RulesNotSatisfiedException.class);
        cityService.create(DomainFactory.createCityPayload());
    }

    @Test
    public void whenSavingAValidCityReturnsTheObjectFilled(){
        State state = DomainFactory.createState("Floripa", "SC");
        CityPayload payload = DomainFactory.createCityPayload();

        when(stateServiceMock.findByShortname(anyString())).thenReturn(Optional.of(state));
        City city = cityService.create(payload);

        assertNotNull("City shouldn't be null", city);

        verify(eventDispatcher, times(1)).dispatch(any());
        verify(cityRepositoryMock, times(1)).save(any());
    }

    @Test
    public void throwExceptionTryingToUpdateAnUnknownCity(){
        when(cityRepositoryMock.findById("10")).thenReturn(Optional.empty());
        expectedException.expect(ResourceNotFoundException.class);
        cityService.update("10", DomainFactory.createCityPayload());
        verify(cityRepositoryMock, times(0)).save(any());
        verify(stateServiceMock, times(0)).findByShortname(any());
        verify(cityRepositoryMock, times(1)).findById(any());

    }

    @Test
    public void throwExceptionTryingToUpdateTheCityToUnknownState(){
        City city = DomainFactory.createFullCity("Floripa", "SP");
        when(cityRepositoryMock.findById("10")).thenReturn(Optional.of(city));
        when(stateServiceMock.findByShortname(any())).thenReturn(Optional.empty());
        expectedException.expect(RulesNotSatisfiedException.class);
        cityService.update("10", DomainFactory.createCityPayload());
        verify(cityRepositoryMock, times(0)).save(any());
        verify(stateServiceMock, times(1)).findByShortname(any());

    }

    @Test
    public void successWhenUpdatingTheCityWithSameState(){
        CityPayload payload = DomainFactory.createCityPayload();
        City city = DomainFactory.createFullCity("Floripa", "SC");
        State state = DomainFactory.createState("Santa Catarina", "SC");

        when(cityRepositoryMock.findById("10")).thenReturn(Optional.of(city));
        when(stateServiceMock.findByShortname(any())).thenReturn(Optional.of(state));

        cityService.update("10", payload);

        verify(cityRepositoryMock, times(1)).save(any());
        verify(stateServiceMock, times(0)).findByShortname(any());
        verify(cityRepositoryMock, times(1)).findById(any());
    }

    @Test
    public void successWhenUpdatingTheCityWithDifferentState(){
        CityPayload payload = DomainFactory.createCityPayload();
        City city = DomainFactory.createFullCity("Floripa", "SP");
        State state = DomainFactory.createState("Santa Catarina", "SC");

        when(cityRepositoryMock.findById("10")).thenReturn(Optional.of(city));
        when(stateServiceMock.findByShortname(any())).thenReturn(Optional.of(state));

        cityService.update("10", payload);

        verify(cityRepositoryMock, times(1)).save(any());
        verify(stateServiceMock, times(1)).findByShortname(any());
        verify(cityRepositoryMock, times(1)).findById(any());
    }

}
