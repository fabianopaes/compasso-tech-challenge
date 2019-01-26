package com.compasso.api.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GenderTest {


    @Test
    public void ReturnTrueWhenCheckingByAValidGender(){
        assertTrue("Should find the gender", Gender.contains("MALE"));
    }

    @Test
    public void ReturnTrueWhenCheckingByAValidGenderWhenCaseInsensitive(){
        assertTrue("Should find the gender", Gender.contains("male"));
    }

    @Test
    public void ReturnTheGenderWhenGettingAValidOne(){
        assertEquals("Should return the gender", Gender.MALE, Gender.get("MALE"));
    }

    @Test
    public void ReturnTheGenderWhenGettingAValidOneOnWhenCaseInsensitive(){
        assertEquals("Should return the gender",  Gender.MALE, Gender.get("male"));
    }

}
