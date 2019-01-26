package com.compasso.api.web;

import com.compasso.api.config.EndpointConfig;
import com.compasso.api.domain.Customer;
import com.compasso.api.domain.payload.CustomerPayload;
import com.compasso.api.domain.resource.ResponseProblem;
import com.compasso.api.service.CustomerService;
import com.compasso.api.utils.URIPathBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(value = EndpointConfig.CUSTOMERS_COLLECTION, method = POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> create(@Valid @RequestBody CustomerPayload request, Errors errors) throws URISyntaxException {
        if (errors.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8)
                    .body(ResponseProblem.badRequest(errors));
        }

        Customer customer = customerService.create(request);

        return ResponseEntity
                .created(URIPathBinder.resourceLocationBuilder(EndpointConfig.CITIES_SINGLE_RESOURCE, customer.getId()))
                .body(customer);
    }

    @RequestMapping(value = EndpointConfig.CUSTOMERS_SINGLE_RESOUCE, method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> get(@PathVariable(value="id") String id){
        Optional<Customer> customer = customerService.findById(id);
        if(customer.isPresent()){
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE);
        return new ResponseEntity<>(ResponseProblem.notFound(), headers,
                HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = EndpointConfig.CUSTOMERS_COLLECTION, method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> list(@RequestParam Map<String, String> queryParameters) throws URISyntaxException {
        return ResponseEntity
                .ok()
                .body(customerService.findByQuery(queryParameters));
    }


    @RequestMapping(value = EndpointConfig.CUSTOMERS_SINGLE_RESOUCE, method = PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> put(@PathVariable(value="id") String id, @Valid @RequestBody CustomerPayload request, Errors errors)
            throws URISyntaxException {
        if (errors.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8)
                    .body(ResponseProblem.badRequest(errors));
        }

        customerService.update(id, request);

        return ResponseEntity.noContent().build();
    }


    @RequestMapping(value = EndpointConfig.CUSTOMERS_SINGLE_RESOUCE, method = DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> delete(@PathVariable(value="id") String id){

        customerService.delete(id);

        return ResponseEntity.noContent().build();
    }


}
