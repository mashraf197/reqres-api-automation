package reqres.base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class BaseApi {

    private static RequestSpecification requestSpec;

    public static void init() {

        String API_Key = "reqres_d21c07f5589e4248bd4113eacadd499f";
        if (requestSpec != null) return;

        requestSpec = RestAssured
                .given()
                .baseUri("https://reqres.in")
                .basePath("/api")

                // Headers
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header(
                        "User-Agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                                "AppleWebKit/537.36 (KHTML, like Gecko) " +
                                "Chrome/120.0.0.0 Safari/537.36"
                )
                .header("x-api-key",API_Key)
                .log().all()

                // Allure
                .filter(new AllureRestAssured());
    }

    public static RequestSpecification spec() {
        if (requestSpec == null) {
            init();
        }
        return requestSpec;
    }
}
