package com.compasso.api.services;

import com.compasso.api.domain.State;
import com.compasso.api.domain.StateRepository;
import com.compasso.api.service.impl.StateServiceImpl;
import com.compasso.api.support.DomainFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class StateServiceTest {

    private StateServiceImpl stateService;
    private StateRepository stateRepositoryMock;

    @Before
    public void initialize(){
        stateRepositoryMock = Mockito.mock(StateRepository.class);
        stateService = new StateServiceImpl();
        stateService.setStateRepository(stateRepositoryMock);
    }

    @Test
    public void whenCallsFindAllReturnsAllAvailableStates(){
        State state = DomainFactory.createState("Santa Catarina", "SC");
        when(stateRepositoryMock.findAll()).thenReturn(Arrays.asList(state));
        List<State> result = stateService.findAll();
        assertEquals("findAll should returns onlny one item", 1, result.size());
        State singleState = result.get(0);
        assertEquals("State should be Santa Catarina", state.getName(), singleState.getName());
        assertEquals("State should be SC", state.getShortname(), singleState.getShortname());
        verify(stateRepositoryMock, times(1)).findAll();
    }


    @Test
    public void whenCallsFindByIdReturnsTheSingleResource(){
        State state = DomainFactory.createState("Santa Catarina", "SC");
        when(stateRepositoryMock.findById("1010")).thenReturn(Optional.of(state));
        Optional<State> result = stateService.findById("1010");
        assertTrue("findById should returns the item", result.isPresent());
        assertEquals("State should be Santa Catarina", state.getName(), result.get().getName());
        assertEquals("State should be SC", state.getShortname(), result.get().getShortname());
        verify(stateRepositoryMock, times(1)).findById("1010");
    }

    @Test
    public void whenCallsFindByShortNameReturnsTheSingleResource(){
        State state = DomainFactory.createState("Santa Catarina", "SC");
        when(stateRepositoryMock.findByShortname("SC")).thenReturn(state);
        Optional<State> result = stateService.findByShortname("SC");
        assertTrue("findById should returns the item", result.isPresent());
        assertEquals("State should be Santa Catarina", state.getName(), result.get().getName());
        assertEquals("State should be SC", state.getShortname(), result.get().getShortname());
        verify(stateRepositoryMock, times(1)).findByShortname("SC");
    }


}
