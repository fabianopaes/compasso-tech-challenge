package compasso.api;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;

public class Customer extends BaseTest{

    private String getCityId(){
        Map<String, String> cityPayload = new HashMap<>();
        cityPayload.put("name", "Nova Cidade");
        cityPayload.put("state", "RJ");
        return given()
                .contentType("application/json")
                .body(cityPayload)
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
    }


    @Test
    public void successWhenSavingValidCustomer(){
        String id = getCityId();

        Map<String, String> customerPayload = new HashMap<>();
        customerPayload.put("name", "Fabiano Paes");
        customerPayload.put("cityId", id);
        customerPayload.put("birthDate", "1988-02-24");
        customerPayload.put("gender", "MALE");

        given()
                .contentType("application/json")
                .body(customerPayload)
                .when()
                .post("/customers")
                .then()
                .body("name", equalTo("Fabiano Paes"))
                .body("cityId", equalTo(id))
                .body("birthDate", equalTo("1988-02-24T00:00:00Z"))
                .body("gender", equalTo("MALE"))
                .body(containsString("id"))
                .body(containsString("updatedAt"))
                .body(containsString("createdAt"))
                .assertThat()
                .statusCode(201);
    }

    @Test
    public void errorApplyingRequestWithInvalidCity(){
        Map<String, String> customerPayload = new HashMap<>();
        customerPayload.put("name", "Fabiano Paes");
        customerPayload.put("cityId", "1010");
        customerPayload.put("birthDate", "1988-02-24");
        customerPayload.put("gender", "MALE");

        given()
                .contentType("application/json")
                .body(customerPayload)
                .when()
                .post("/customers")
                .then()
                .body("type", equalTo("about:blank"))
                .body("title", equalTo("bad request"))
                .body("status", equalTo(400))
                .body("detail", equalTo("city not found"))
                .contentType("application/problem+json;charset=UTF-8")
                .assertThat()
                .statusCode(400).extract().response().asString();
    }

    @Test
    public void errorApplyingRequestWithInvalidGender(){
        String id = getCityId();

        Map<String, String> customerPayload = new HashMap<>();
        customerPayload.put("name", "Fabiano Paes");
        customerPayload.put("cityId", id);
        customerPayload.put("birthDate", "1988-02-24");
        customerPayload.put("gender", "S");

        given()
                .contentType("application/json")
                .body(customerPayload)
                .when()
                .post("/customers")
                .then()
                .body("type", equalTo("about:blank"))
                .body("title", equalTo("bad request"))
                .body("status", equalTo(400))
                .body("detail", equalTo("Invalid value for gender."))
                .contentType("application/problem+json;charset=UTF-8")
                .assertThat()
                .statusCode(400).extract().response().asString();

    }

    @Test
    public void errorApplyingRequestMissingName(){
        String id = getCityId();

        Map<String, String> customerPayload = new HashMap<>();
        customerPayload.put("cityId", id);
        customerPayload.put("birthDate", "1988-02-24");
        customerPayload.put("gender", "MALE");

        given()
                .contentType("application/json")
                .body(customerPayload)
                .when()
                .post("/customers")
                .then()
                .body("type", equalTo("about:blank"))
                .body("title", equalTo("bad request"))
                .body("status", equalTo(400))
                .body("detail", equalTo("you have applied a wrong request, please check the documentation"))
                .body("errors", hasItem("name might not be null"))
                .contentType("application/problem+json;charset=UTF-8")
                .assertThat()
                .statusCode(400).extract().response().asString();

    }


    @Test
    public void errorApplyingRequestMissingCity(){
        Map<String, String> customerPayload = new HashMap<>();
        customerPayload.put("name", "Fabiano");
        customerPayload.put("birthDate", "1988-02-24");
        customerPayload.put("gender", "MALE");

        given()
                .contentType("application/json")
                .body(customerPayload)
                .when()
                .post("/customers")
                .then()
                .body("type", equalTo("about:blank"))
                .body("title", equalTo("bad request"))
                .body("status", equalTo(400))
                .body("detail", equalTo("you have applied a wrong request, please check the documentation"))
                .body("errors", hasItem("cityId might not be null"))
                .contentType("application/problem+json;charset=UTF-8")
                .assertThat()
                .statusCode(400).extract().response().asString();
    }

    @Test
    public void errorApplyingRequestMissingGender(){
        String cityId = getCityId();
        Map<String, String> customerPayload = new HashMap<>();
        customerPayload.put("name", "Fabiano");
        customerPayload.put("birthDate", "1988-02-24");
        customerPayload.put("cityId", cityId);

        given()
                .contentType("application/json")
                .body(customerPayload)
                .when()
                .post("/customers")
                .then()
                .body("type", equalTo("about:blank"))
                .body("title", equalTo("bad request"))
                .body("status", equalTo(400))
                .body("detail", equalTo("you have applied a wrong request, please check the documentation"))
                .body("errors", hasItem("gender might not be null"))
                .contentType("application/problem+json;charset=UTF-8")
                .assertThat()
                .statusCode(400).extract().response().asString();

    }

    @Test
    public void errorApplyingRequestMissingBirthDate(){
        String cityId = getCityId();
        Map<String, String> customerPayload = new HashMap<>();
        customerPayload.put("name", "Fabiano");
        customerPayload.put("gender", "MALE");
        customerPayload.put("cityId", cityId);

        given()
                .contentType("application/json")
                .body(customerPayload)
                .when()
                .post("/customers")
                .then()
                .body("type", equalTo("about:blank"))
                .body("title", equalTo("bad request"))
                .body("status", equalTo(400))
                .body("detail", equalTo("you have applied a wrong request, please check the documentation"))
                .body("errors", hasItem("dateOfBirth might not be null"))
                .contentType("application/problem+json;charset=UTF-8")
                .assertThat()
                .statusCode(400).extract().response().asString();

    }

    @Test
    public void errorApplyingRequestMissingPayload(){
        Map<String, String> customerPayload = new HashMap<>();
        given()
                .contentType("application/json")
                .body(customerPayload)
                .when()
                .post("/customers")
                .then()
                .body("type", equalTo("about:blank"))
                .body("title", equalTo("bad request"))
                .body("status", equalTo(400))
                .body("detail", equalTo("you have applied a wrong request, please check the documentation"))
                .body("errors", hasItem("dateOfBirth might not be null"))
                .body("errors", hasItem("gender might not be null"))
                .body("errors", hasItem("cityId might not be null"))
                .body("errors", hasItem("name might not be null"))
                .body("errors", hasSize(4))
                .contentType("application/problem+json;charset=UTF-8")
                .assertThat()
                .statusCode(400).extract().response().asString();
    }

    @Test
    public void notFoundGettingCustomerByUnknownId(){
        given()
                .when()
                .get("/customers/1010" )
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
    public void successGettingCustomerById(){

        String id = getCityId();

        Map<String, String> customerPayload = new HashMap<>();
        customerPayload.put("name", "Fabiano Paes");
        customerPayload.put("cityId", id);
        customerPayload.put("birthDate", "1988-02-24");
        customerPayload.put("gender", "MALE");

        String customerId = given()
                .contentType("application/json")
                .body(customerPayload)
                .when()
                .post("/customers")
                .then()
                .body("name", equalTo("Fabiano Paes"))
                .body("cityId", equalTo(id))
                .body("birthDate", equalTo("1988-02-24T00:00:00Z"))
                .body("gender", equalTo("MALE"))
                .body(containsString("id"))
                .body(containsString("updatedAt"))
                .body(containsString("createdAt"))
                .assertThat()
                .statusCode(201).extract().path("id");

        given()
                .when()
                .get("/customers/" + customerId )
                .then()
                .body("name", equalTo("Fabiano Paes"))
                .body("cityId", equalTo(id))
                .body("birthDate", equalTo("1988-02-24T00:00:00Z"))
                .body("gender", equalTo("MALE"))
                .body(containsString("id"))
                .body(containsString("updatedAt"))
                .body(containsString("createdAt"))
                .assertThat()
                .statusCode(200);
    }


    @Test
    public void successUpdatingCustomerName(){
       String cityId = getCityId();

        Map<String, String> customerPayload = new HashMap<>();
        customerPayload.put("name", "Fabiano Paes");
        customerPayload.put("cityId", cityId);
        customerPayload.put("birthDate", "1988-02-24");
        customerPayload.put("gender", "MALE");

        String customerId = given()
                .contentType("application/json")
                .body(customerPayload)
                .when()
                .post("/customers")
                .then()
                .body("name", equalTo("Fabiano Paes"))
                .body("cityId", equalTo(cityId))
                .body("birthDate", equalTo("1988-02-24T00:00:00Z"))
                .body("gender", equalTo("MALE"))
                .body(containsString("id"))
                .body(containsString("updatedAt"))
                .body(containsString("createdAt"))
                .assertThat()
                .statusCode(201).extract().path("id");

        given()
                .when()
                .get("/customers/" + customerId )
                .then()
                .body("name", equalTo("Fabiano Paes"))
                .body("cityId", equalTo(cityId))
                .body("birthDate", equalTo("1988-02-24T00:00:00Z"))
                .body("gender", equalTo("MALE"))
                .body(containsString("id"))
                .body(containsString("updatedAt"))
                .body(containsString("createdAt"))
                .assertThat()
                .statusCode(200);

        customerPayload.put("name", "other name");

        given()
                .contentType("application/json")
                .body(customerPayload)
                .when()
                .put("/customers/" + customerId )
                .then()
                .assertThat()
                .statusCode(204);

        given()
                .when()
                .get("/customers/" + customerId )
                .then()
                .body("name", equalTo("other name"))
                .body("cityId", equalTo(cityId))
                .body("birthDate", equalTo("1988-02-24T00:00:00Z"))
                .body("gender", equalTo("MALE"))
                .body(containsString("id"))
                .body(containsString("updatedAt"))
                .body(containsString("createdAt"))
                .assertThat()
                .statusCode(200);
    }


    @Test
    public void errorUpdatingUnknownCustomer(){

        Map<String, String> customerPayload = new HashMap<>();
        customerPayload.put("name", "Fabiano Paes");
        customerPayload.put("cityId", "1010");
        customerPayload.put("birthDate", "1988-02-24");
        customerPayload.put("gender", "MALE");

        String response = given()
                .contentType("application/json")
                .body(customerPayload)
                .when()
                .put("/customers/10")
                .then()
                .body("type", equalTo("about:blank"))
                .body("title", equalTo("not found"))
                .body("status", equalTo(404))
                .body("detail", equalTo("Could not find the requested resource(s)"))
                .contentType("application/problem+json;charset=UTF-8")
                .assertThat()
                .statusCode(404).extract().response().asString();
    }

}
