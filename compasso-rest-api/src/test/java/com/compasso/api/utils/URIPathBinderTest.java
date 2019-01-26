package com.compasso.api.utils;

import com.compasso.api.config.EndpointConfig;
import org.junit.Test;

import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

public class URIPathBinderTest {

    @Test
    public void shouldReturnIdAtTheEndOfUrl() throws URISyntaxException {
        String id = "5aaf62a676df252c51cc632a";
        String path = "/cities/" + id;
        assertEquals(path,
                URIPathBinder.resourceLocationBuilder(EndpointConfig.CITIES_SINGLE_RESOURCE, id).getPath());
    }

}
