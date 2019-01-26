package com.compasso.api.domain.payload;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class CustomerPayload {

    @NotNull(message = "name might not be null")
    private String name;

    @NotNull(message = "cityId might not be null")
    private String cityId;

    @NotNull(message = "dateOfBirth might not be null")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthDate;

    @NotNull(message = "gender might not be null")
    private String gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
