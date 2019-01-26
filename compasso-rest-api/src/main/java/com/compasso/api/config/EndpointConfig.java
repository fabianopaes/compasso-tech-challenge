package com.compasso.api.config;

public interface EndpointConfig {

    String STATES_COLLECTION = "/states";
    String STATES_SINGLE_RESOURCE = "/states/{id}";

    String CITIES_COLLECTION = "/cities";
    String CITIES_SINGLE_RESOURCE = "/cities/{id}";

    String CUSTOMERS_COLLECTION = "/customers";
    String CUSTOMERS_SINGLE_RESOUCE = "/customers/{id}";
}
