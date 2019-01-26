package compasso.api;

import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;

public class State extends BaseTest {


    @Test
    public void shouldReturnAListOfStates() {
        given().
                when().
                get("/states").
                then().
                assertThat().
                statusCode(200);
    }

}
