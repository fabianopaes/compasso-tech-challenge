package com.compasso.api.services;

import com.compasso.api.domain.City;
import com.compasso.api.domain.Customer;
import com.compasso.api.domain.CustomerRepository;
import com.compasso.api.domain.payload.CityPayload;
import com.compasso.api.domain.payload.CustomerPayload;
import com.compasso.api.events.EventDispatcher;
import com.compasso.api.exception.ResourceNotFoundException;
import com.compasso.api.exception.RulesNotSatisfiedException;
import com.compasso.api.service.CityService;
import com.compasso.api.service.impl.CustomerServiceImpl;
import com.compasso.api.support.DomainFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    private CustomerServiceImpl customerService;
    private CityService cityServiceMock;
    private CustomerRepository customerRepositoryMock;
    private EventDispatcher eventDispatcher;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void initialize(){
        customerService = new CustomerServiceImpl();

        eventDispatcher = Mockito.spy(EventDispatcher.class);
        cityServiceMock = Mockito.mock(CityService.class);
        customerRepositoryMock = Mockito.mock(CustomerRepository.class);

        customerService.setCityService(cityServiceMock);
        customerService.setCustomerRepository(customerRepositoryMock);
        customerService.setEventDispatcher(eventDispatcher);
    }

    @Test
    public void whenCallsFindAllReturnsAllAvailableCustomers(){
        Customer customer = DomainFactory.createCustomer();
        when(customerRepositoryMock.findAll()).thenReturn(Arrays.asList(customer));

        List<Customer> result = customerService.findAll();

        assertEquals("findAll should returns only one item", 1, result.size());
        Customer singleResult = result.get(0);
        assertEquals("Customer should customer-01", customer.getName(), singleResult.getName());
        assertEquals("City should be 1010", customer.getCityId(), singleResult.getCityId());
        assertEquals("Gender should be MALE", customer.getGender(), singleResult.getGender());

        verify(customerRepositoryMock, times(1)).findAll();
    }


    @Test
    public void whenCallsFindByIdReturnsTheSingleResource(){
        Customer customer = DomainFactory.createCustomer();
        when(customerRepositoryMock.findById("1010")).thenReturn(Optional.of(customer));

        Optional<Customer> result = customerService.findById("1010");

        assertTrue("findAll findById returns one item", result.isPresent());
        Customer singleResult = result.get();
        assertEquals("Customer should customer-01", customer.getName(), singleResult.getName());
        assertEquals("City should be 1010", customer.getCityId(), singleResult.getCityId());
        assertEquals("Gender should be MALE", customer.getGender(), singleResult.getGender());

        verify(customerRepositoryMock, times(1)).findById("1010");
    }

    @Test
    public void whenCallsFindByQueryWithEmptyQueryCallFindAll(){
        Customer customer = DomainFactory.createCustomer();
        when(customerRepositoryMock.findAll()).thenReturn(Arrays.asList(customer));

        Map<String,String> query = new HashMap<>();

        List<Customer> result = customerService.findByQuery(query);

        assertEquals("findAll should returns only one item", 1, result.size());
        Customer singleResult = result.get(0);
        assertEquals("Customer should customer-01", customer.getName(), singleResult.getName());
        assertEquals("City should be 1010", customer.getCityId(), singleResult.getCityId());
        assertEquals("Gender should be MALE", customer.getGender(), singleResult.getGender());

        verify(customerRepositoryMock, times(1)).findAll();
        verify(customerRepositoryMock, times(0)).findByNameLike(any());
        verify(customerRepositoryMock, times(0)).findById(any());

    }

    @Test
    public void whenCallsFindByQueryWithFilteringByNameCallFindByNameLike(){
        Customer customer = DomainFactory.createCustomer();
        when(customerRepositoryMock.findByNameLike(any())).thenReturn(Arrays.asList(customer));

        Map<String,String> query = new HashMap<>();
        query.put("name", "customer-01");


        List<Customer> result = customerService.findByQuery(query);

        assertEquals("findAll should returns only one item", 1, result.size());
        Customer singleResult = result.get(0);
        assertEquals("Customer should customer-01", customer.getName(), singleResult.getName());
        assertEquals("City should be 1010", customer.getCityId(), singleResult.getCityId());
        assertEquals("Gender should be MALE", customer.getGender(), singleResult.getGender());

        verify(customerRepositoryMock, times(0)).findAll();
        verify(customerRepositoryMock, times(1)).findByNameLike(any());
        verify(customerRepositoryMock, times(0)).findById(any());

    }


    @Test
    public void whenCallsFindByQueryWithFilteringByUnknownFieldsCallFindAll(){
        Customer customer = DomainFactory.createCustomer();
        when(customerRepositoryMock.findAll()).thenReturn(Arrays.asList(customer));

        Map<String,String> query = new HashMap<>();
        query.put("fake-key", "customer-01");


        List<Customer> result = customerService.findByQuery(query);

        assertEquals("findAll should returns only one item", 1, result.size());
        Customer singleResult = result.get(0);
        assertEquals("Customer should customer-01", customer.getName(), singleResult.getName());
        assertEquals("City should be 1010", customer.getCityId(), singleResult.getCityId());
        assertEquals("Gender should be MALE", customer.getGender(), singleResult.getGender());

        verify(customerRepositoryMock, times(1)).findAll();
        verify(customerRepositoryMock, times(0)).findByNameLike(any());
        verify(customerRepositoryMock, times(0)).findById(any());

    }

    @Test
    public void throwExceptionWhenValidatingPayloadWithUnknownCity(){
        when(cityServiceMock.findById(anyString())).thenReturn(Optional.empty());
        expectedException.expect(RulesNotSatisfiedException.class);
        customerService.validate(DomainFactory.customerPayload());
    }

    @Test
    public void throwExceptionWhenValidatingPayloadWithBirthDateGratherThanNow(){
        City city = DomainFactory.createCity("Floripa", "SC");
        when(cityServiceMock.findById(anyString())).thenReturn(Optional.of(city));
        CustomerPayload payload = DomainFactory.customerPayload();
        Date birth = Date.from(LocalDate
                .now().atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .plusDays(1)
                .toInstant());
        payload.setBirthDate(birth);
        expectedException.expect(RulesNotSatisfiedException.class);
        customerService.validate(payload);
    }


    @Test
    public void throwExceptionWhenValidatingPayloadWithUnknownGender(){
        City city = DomainFactory.createCity("Floripa", "SC");
        when(cityServiceMock.findById(anyString())).thenReturn(Optional.of(city));
        CustomerPayload payload = DomainFactory.customerPayload();
        payload.setGender("ANY");
        expectedException.expect(RulesNotSatisfiedException.class);
        customerService.validate(payload);
    }

    @Test
    public void throwExceptionWhenDeletingAnUnknownCustomer(){
        when(customerRepositoryMock.findById(any())).thenReturn(Optional.empty());
        expectedException.expect(ResourceNotFoundException.class);
        customerService.delete("40");
        verify(customerRepositoryMock, times(0)).delete(any());

    }

    @Test
    public void successWhenDeletingTheCustomer(){
        Customer customer = DomainFactory.createCustomer();
        when(customerRepositoryMock.findById(any())).thenReturn(Optional.of(customer));
        customerService.delete("40");
        verify(customerRepositoryMock, times(1)).delete(any());
    }

    @Test
    public void throwExceptionWhenSavingCustomerButValidateFails(){
        when(cityServiceMock.findById(anyString())).thenReturn(Optional.empty());
        expectedException.expect(RulesNotSatisfiedException.class);
        customerService.create(DomainFactory.customerPayload());
        verify(eventDispatcher, times(0)).dispatch(any());
        verify(customerRepositoryMock, times(0)).save(any());
    }

    @Test
    public void successWhenSavingCustomer(){
        City city = DomainFactory.createCity("Floripa", "SC");
        CustomerPayload payload = DomainFactory.customerPayload();

        when(cityServiceMock.findById(anyString())).thenReturn(Optional.of(city));

        Customer customer = customerService.create(payload);

        assertNotNull("Customer shouldn't be null", customer);

        verify(eventDispatcher, times(1)).dispatch(any());
        verify(customerRepositoryMock, times(1)).save(any());
    }



    //update

    @Test
    public void throwExceptionWhenUpdateCustomerButValidateFails(){
        Customer customer = DomainFactory.createCustomer();
        when(customerService.findById(anyString())).thenReturn(Optional.of(customer));
        when(cityServiceMock.findById(anyString())).thenReturn(Optional.empty());

        expectedException.expect(RulesNotSatisfiedException.class);
        customerService.update("1010",  DomainFactory.customerPayload("customer-02", "99"));

        verify(eventDispatcher, times(0)).dispatch(any());
        verify(customerRepositoryMock, times(0)).save(any());
    }

    @Test
    public void throwExceptionWhenUpdateUnknownCustomer(){
        when(customerService.findById(anyString())).thenReturn(Optional.empty());
        expectedException.expect(ResourceNotFoundException.class);
        customerService.update("1010",  DomainFactory.customerPayload("customer-02", "99"));
        verify(eventDispatcher, times(0)).dispatch(any());
        verify(customerRepositoryMock, times(0)).save(any());
        verify(cityServiceMock, times(0)).findById(any());

    }


    @Test
    public void successWhenUpdatingCustomer(){
        Customer customer = DomainFactory.createCustomer();
        City city = DomainFactory.createFullCity("Floripa", "SC");

        when(customerService.findById(anyString())).thenReturn(Optional.of(customer));
        when(cityServiceMock.findById(anyString())).thenReturn(Optional.of(city));

        customerService.update("1010",  DomainFactory.customerPayload("customer-02", "99"));

        verify(eventDispatcher, times(1)).dispatch(any());
        verify(customerRepositoryMock, times(1)).save(any());
    }



}
