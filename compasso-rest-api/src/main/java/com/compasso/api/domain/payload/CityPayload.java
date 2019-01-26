package com.compasso.api.domain.payload;

import javax.validation.constraints.NotNull;

public class CityPayload {

    @NotNull(message = "name might not be null")
    private String name;

    @NotNull(message = "state might not be null")

    private String state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
