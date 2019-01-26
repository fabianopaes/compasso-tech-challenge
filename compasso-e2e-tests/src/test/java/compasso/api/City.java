package compasso.api;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;

public class City extends BaseTest{

    @Test
    public void successWhenSavingValidCity(){
        Map<String, String> payload = new HashMap<>();
        payload.put("name", "Floripa");
        payload.put("state", "SC");

        given()
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/cities")
                .then()
                .body("name", equalTo("FLORIPA"))
                .body("state", equalTo("SC"))
                .body(containsString("id"))
                .body(containsString("updatedAt"))
                .body(containsString("createdAt"))
                .assertThat()
                .statusCode(201);
    }


    @Test
    public void errorApplyingRequestWithInvalidState(){
        Map<String, String> payload = new HashMap<>();
        payload.put("name", "Floripa");
        payload.put("state", "SS");

        given()
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/cities")
                .then()
                .body("type", equalTo("about:blank"))
                .body("title", equalTo("bad request"))
                .body("status", equalTo(400))
                .body("detail", equalTo("state not found"))
                .contentType("application/problem+json;charset=UTF-8")
                .assertThat()
                .statusCode(400);
    }


    @Test
    public void errorApplyingRequestMissingName(){
        Map<String, String> payload = new HashMap<>();
        payload.put("state", "SC");
        given()
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/cities")
                .then()
                .body("type", equalTo("about:blank"))
                .body("title", equalTo("bad request"))
                .body("status", equalTo(400))
                .body("detail", equalTo("you have applied a wrong request, please check the documentation"))
                .body("errors", hasItem("name might not be null"))
                .contentType("application/problem+json;charset=UTF-8")
                .assertThat()
                .statusCode(400);
    }


    @Test
    public void errorApplyingRequestMissingState(){
        Map<String, String> payload = new HashMap<>();
        payload.put("name", "Floripa");
        given()
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/cities")
                .then()
                .body("type", equalTo("about:blank"))
                .body("title", equalTo("bad request"))
                .body("status", equalTo(400))
                .body("detail", equalTo("you have applied a wrong request, please check the documentation"))
                .body("errors", hasItem("state might not be null"))
                .contentType("application/problem+json;charset=UTF-8")
                .assertThat()
                .statusCode(400);
    }

    @Test
    public void notFoundGettingCityByUnknownId(){
        given()
                .when()
                .get("/cities/1010" )
                .then()
                .body("type", equalTo("about:blank"))
                .body("title", equalTo("not found"))
                .body("status", equalTo(404))
                .body("detail", equalTo("Could not find the requested resource(s)"))
                .contentType("application/problem+json;charset=UTF-8")
                .assertThat()
                .statusCode(404);
    }


    @Test
    public void successGettingCityById(){

        Map<String, String> payload = new HashMap<>();
        payload.put("name", "Nova Cidade");
        payload.put("state", "RJ");

        String id = given()
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/cities")
                .then()
                .body("name", equalTo("NOVA CIDADE"))
                .body("state", equalTo("RJ"))
                .body(containsString("id"))
                .body(containsString("updatedAt"))
                .body(containsString("createdAt"))
                .assertThat()
                .statusCode(201).extract().path("id");

        given()
                .when()
                .get("/cities/" + id )
                .then()
                .body("name", equalTo("NOVA CIDADE"))
                .body("state", equalTo("RJ"))
                .body(containsString("id"))
                .body(containsString("updatedAt"))
                .body(containsString("createdAt"))
                .assertThat()
                .statusCode(200);
    }


    @Test
    public void successUpdatingCityName(){

        Map<String, String> payload = new HashMap<>();
        payload.put("name", "first name");
        payload.put("state", "RJ");

        String id = given()
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/cities")
                .then()
                .body("name", equalTo("FIRST NAME"))
                .body("state", equalTo("RJ"))
                .body(containsString("id"))
                .body(containsString("updatedAt"))
                .body(containsString("createdAt"))
                .assertThat()
                .statusCode(201).extract().path("id");

        given()
                .when()
                .get("/cities/" + id )
                .then()
                .body("name", equalTo("FIRST NAME"))
                .body("state", equalTo("RJ"))
                .body(containsString("id"))
                .body(containsString("updatedAt"))
                .body(containsString("createdAt"))
                .assertThat()
                .statusCode(200);

        payload.put("name", "other name");

        given()
                .contentType("application/json")
                .body(payload)
                .when()
                .put("/cities/" + id )
                .then()
                .assertThat()
                .statusCode(204);

        given()
                .when()
                .get("/cities/" + id )
                .then()
                .body("name", equalTo("OTHER NAME"))
                .body("state", equalTo("RJ"))
                .body(containsString("id"))
                .body(containsString("updatedAt"))
                .body(containsString("createdAt"))
                .assertThat()
                .statusCode(200);
    }


    @Test
    public void errorUpdatingUnknownCity(){

        Map<String, String> payload = new HashMap<>();
        payload.put("name", "first name");
        payload.put("state", "RJ");

        given()
                .contentType("application/json")
                .body(payload)
                .when()
                .put("/cities/1010"  )
                .then()
                .body("type", equalTo("about:blank"))
                .body("title", equalTo("not found"))
                .body("status", equalTo(404))
                .body("detail", equalTo("Could not find the requested resource(s)"))
                .contentType("application/problem+json;charset=UTF-8")
                .assertThat()
                .statusCode(404);

    }



}
