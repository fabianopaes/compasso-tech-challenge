package com.compasso.api.utils;

import java.net.URI;
import java.net.URISyntaxException;

public class URIPathBinder {
    
    public static URI resourceLocationBuilder(String path, String id) throws URISyntaxException {
        return new URI(path.replace("{id}",id));
    }
}
