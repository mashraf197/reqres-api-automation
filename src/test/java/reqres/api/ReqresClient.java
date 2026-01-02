package reqres.api;

import io.restassured.response.Response;
import reqres.base.BaseApi;
import reqres.models.CreateUserRequest;
import reqres.models.UpdateUserRequest;

import static io.restassured.RestAssured.given;

public class ReqresClient {

    public Response createUser(CreateUserRequest body) {
        return given()
                .spec(BaseApi.spec())
                .body(body)
                .when()
                .post("/users")
                .then()
                .extract()
                .response();
    }

    public Response updateUser(String id, UpdateUserRequest body) {
        return given()
                .spec(BaseApi.spec())
                .body(body)
                .when()
                .put("/users/" + id)
                .then()
                .extract()
                .response();
    }

    public Response getUser(String id) {
        return given()
                .spec(BaseApi.spec())
                .when()
                .get("/users/" + id)
                .then()
                .extract()
                .response();
    }

    public Response deleteUser(String id) {
        return given()
                .spec(BaseApi.spec())
                .when()
                .delete("/users/" + id)
                .then()
                .extract()
                .response();
    }
}
