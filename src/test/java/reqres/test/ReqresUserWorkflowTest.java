package reqres.test;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import reqres.api.ReqresClient;
import reqres.base.BaseApi;
import reqres.models.CreateUserRequest;
import reqres.models.UpdateUserRequest;

import static org.testng.Assert.*;

@Epic("ReqRes API")
@Feature("User Management Workflow")
public class ReqresUserWorkflowTest {

    private final ReqresClient client = new ReqresClient();

    private String userId;
    private final String initialJob = "QA Engineer";
    private final String updatedJob = "Senior QA Automation Engineer";

    @BeforeClass
    public void setup() {
        BaseApi.init();
    }

    @Test(priority = 1, description = "Create user and capture id")
    @Story("Create user")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateUser() {

        Response createRes = client.createUser(
                new CreateUserRequest("Mohamed", initialJob)
        );

        System.out.println("Create Status: " + createRes.statusCode());
        System.out.println("Create Body: " + createRes.asString());

        assertEquals(createRes.statusCode(), 201, "Create user should return 201");

        userId = createRes.jsonPath().getString("id");
        assertNotNull(userId, "id should not be null");
        assertTrue(userId.trim().length() > 0, "id should not be empty");
    }

    @Test(priority = 2, description = "Update user job")
    @Story("Update user")
    @Severity(SeverityLevel.CRITICAL)
    public void testUpdateUser() {

        Response updateRes = client.updateUser(
                userId,
                new UpdateUserRequest("Mohamed", updatedJob)
        );

        System.out.println("Update Status: " + updateRes.statusCode());
        System.out.println("Update Body: " + updateRes.asString());

        assertEquals(updateRes.statusCode(), 200, "Update user should return 200");
        assertEquals(updateRes.jsonPath().getString("job"), updatedJob, "Job should be updated");
        assertNotNull(updateRes.jsonPath().getString("updatedAt"), "updatedAt should exist");
    }

    @Test(priority = 3, description = "Get user and verify update")
    @Story("Verify update via GET")
    @Severity(SeverityLevel.NORMAL)
    public void testGetUserVerifyUpdate() {

        Response getRes = client.getUser(userId);

        System.out.println("Get Status: " + getRes.statusCode());
        System.out.println("Get Body: " + getRes.asString());

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(
                getRes.statusCode() == 200);

        if (getRes.statusCode() == 200) {
            softAssert.assertNotNull(
                    getRes.jsonPath().get("data"),
                    "GET 200 should contain data object"
            );
        }

        softAssert.assertAll();
    }

    @Test(priority = 4, description = "Delete user")
    @Story("Delete user")
    @Severity(SeverityLevel.CRITICAL)
    public void testDeleteUser() {

        Response deleteRes = client.deleteUser(userId);

        System.out.println("Delete Status: " + deleteRes.statusCode());
        System.out.println("Delete Body: " + deleteRes.asString());

        assertEquals(deleteRes.statusCode(), 204, "Delete should return 204");
    }

    @Test(priority = 5, description = "Get user after delete and verify not found")
    @Story("Verify deletion")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetUserAfterDelete() {

        Response getAfterDelete = client.getUser(userId);

        System.out.println("Get After Delete Status: " + getAfterDelete.statusCode());
        System.out.println("Get After Delete Body: " + getAfterDelete.asString());

        assertEquals(getAfterDelete.statusCode(), 404, "User should not be found after deletion");
    }
}
