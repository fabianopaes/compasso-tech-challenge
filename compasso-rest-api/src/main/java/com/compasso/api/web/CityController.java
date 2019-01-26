package com.compasso.api.web;

import com.compasso.api.config.EndpointConfig;
import com.compasso.api.domain.City;
import com.compasso.api.domain.payload.CityPayload;
import com.compasso.api.domain.resource.ResponseProblem;
import com.compasso.api.exception.RulesNotSatisfiedException;
import com.compasso.api.service.CityService;
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

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
public class CityController {

    private CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @RequestMapping(value = EndpointConfig.CITIES_COLLECTION, method = POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> create(@Valid @RequestBody CityPayload request, Errors errors)
            throws RulesNotSatisfiedException, URISyntaxException {

        if (errors.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8)
                    .body(ResponseProblem.badRequest(errors));
        }

        City city = cityService.create(request);

        return ResponseEntity
                .created(URIPathBinder.resourceLocationBuilder(EndpointConfig.CITIES_SINGLE_RESOURCE, city.getId()))
                .body(city);
    }

    @RequestMapping(value = EndpointConfig.CITIES_SINGLE_RESOURCE, method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> get(@PathVariable(value="id") String id){
        Optional<City> city = cityService.findById(id);
        if(city.isPresent()){
            return new ResponseEntity<>(city.get(), HttpStatus.OK);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE);
        return new ResponseEntity<>(ResponseProblem.notFound(), headers,
                HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = EndpointConfig.CITIES_COLLECTION, method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> list(@RequestParam Map<String, String> queryParameters) throws URISyntaxException {
            return ResponseEntity
                .ok()
                .body(cityService.findByQuery(queryParameters));
    }


    @RequestMapping(value = EndpointConfig.CITIES_SINGLE_RESOURCE, method = PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> update(@PathVariable(value="id") String id, @Valid @RequestBody CityPayload payload, Errors errors)
            throws RulesNotSatisfiedException, URISyntaxException {

        if (errors.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8)
                    .body(ResponseProblem.badRequest(errors));
        }

        cityService.update(id, payload);

        return ResponseEntity.noContent().build();
    }

}
