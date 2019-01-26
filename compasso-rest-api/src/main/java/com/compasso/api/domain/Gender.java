package com.compasso.api.domain;

import java.util.Arrays;

public enum Gender {

    MALE, FEMALE;

    public static boolean contains(String gender){
        return Arrays.stream(Gender.values()).anyMatch((g) -> g.name().equalsIgnoreCase(gender));
    }

    public static Gender get(String gender) {
        return Gender.valueOf(gender.toUpperCase());
    }
}
