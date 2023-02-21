package api_tests;

import com.google.gson.Gson;
import models.Counts;
import models.Project;
import models.ProjectResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ApiTest {

    private static final String TOKEN = "1e5c35f94b1a30f113ab2a0dbf7cf6b212e36a0ff7d8c58d12ce2a1fc2ec8491";
    private static final String BASE_URL = "https://api.qase.io/v1";

    @Test
    public void verifyGetDefectsRequestReturnsBodyData() {
        given().pathParam("code", "QA22")
                .header("Token", TOKEN)
                .when().log().all()
                .get(BASE_URL + "/defect/{code}")
                .then()
                .log().all()
                .statusCode(200)
                .body("status", equalTo(true));
    }

    @Test
    public void verifyPostDefectsRequestReturnsBodyData() {
        given().pathParam("code", "QA22")
                .header("Token", TOKEN)
                .header("Content-Type", "application/json")
                .body("{\"title\": \"QWE\", \"actual_result\": \"zxc\", \"severity\": 3 }")
                .when().log().all()
                .post(BASE_URL + "/defect/{code}")
                .then()
                .log().all()
                .statusCode(200)
                .body("status", equalTo(true));
    }

    @Test
    public void createNewProject() {
        given()
                .header("Token", TOKEN)
                .header("Content-Type", "application/json")
                .body("{\"code\": \"QAHW\", \"title\": \"QASE Homework\"}")
                .when().log().all()
                .post(BASE_URL + "/project")
                .then().log().all()
                .statusCode(200)
                .body("status", equalTo(true), "result.code", equalTo("QAHW"));
    }

    @Test
    public void getAllProjects() {
        String responseBody = "{\"status\":true,\"result\":{\"total\":5,\"filtered\":5,\"count\":5,\"entities\":[{\"title\":\"Demo Project\",\"code\":\"DEMO\",\"counts\":{\"cases\":10,\"suites\":3,\"milestones\":2,\"runs\":{\"total\":0,\"active\":0},\"defects\":{\"total\":0,\"open\":0}}},{\"title\":\"QASE\",\"code\":\"QA\",\"counts\":{\"cases\":1,\"suites\":0,\"milestones\":0,\"runs\":{\"total\":0,\"active\":0},\"defects\":{\"total\":2,\"open\":2}}},{\"title\":\"Qase_DEMO\",\"code\":\"QA22\",\"counts\":{\"cases\":0,\"suites\":0,\"milestones\":0,\"runs\":{\"total\":0,\"active\":0},\"defects\":{\"total\":2,\"open\":2}}},{\"title\":\"Qase_Diploma\",\"code\":\"QD\",\"counts\":{\"cases\":0,\"suites\":0,\"milestones\":0,\"runs\":{\"total\":0,\"active\":0},\"defects\":{\"total\":0,\"open\":0}}},{\"title\":\"QASE Homework\",\"code\":\"QAHW\",\"counts\":{\"cases\":0,\"suites\":0,\"milestones\":0,\"runs\":{\"total\":0,\"active\":0},\"defects\":{\"total\":0,\"open\":0}}}]}}";
        //File file = new File(System.getProperty("user.dir") + "/src/test/resources/responseBody.json");
        given()
                .header("Token", TOKEN)
                .when().log().all()
                .get(BASE_URL + "/project")
                .then()
                .log().all()
                .statusCode(200)
                .body(equalTo(responseBody));
    }


    @Test
    public void getProjectByCode() {
        Gson gson = new Gson();
        ProjectResponse expectedResponse = ProjectResponse.builder()
                .setStatus(true)
                .setResult(Project.builder()
                        .setCode("QA22")
                        .setTitle("Qase_DEMO")
                        .setCounts(Counts.builder()
                                .setCases(0)
                                .setSuites(0).setMilestones(0).setRuns(Counts.Runs.builder()
                                        .setTotal(0)
                                        .setActive(0).build())
                                .setDefects(Counts.Defects.builder()
                                        .setTotal(2)
                                        .setOpen(2).build())
                                .build())
                        .build())
                .build();

        String responseString = given().pathParam("code", "QA22")
                .header("Token", TOKEN)
                .when().log().all()
                .get(BASE_URL + "/project/{code}")
                .then()
                .log().all()
                //.statusCode(200)
                //.body(equalTo(gson.toJson(expectedResponse)));
                .extract().body().asString();

        Assert.assertEquals(gson.fromJson(responseString, ProjectResponse.class), expectedResponse);
    }

    @Test
    public void deleteProject() {
        given()
                .header("Token", TOKEN)
                .header("Content-Type", "application/json")
                .body("{\"code\": \"QAD\", \"title\": \"Project for deleting\"}")
                .when().log().all()
                .post(BASE_URL + "/project")
                .then().log().all()
                .statusCode(200)
                .body("status", equalTo(true), "result.code", equalTo("QAD"));
        given()
                .pathParam("code", "QAD")
                .header("Token", TOKEN)
                .header("Content-Type", "application/json")
                .body("{\"code\": \"QAD\"}")
                .when().log().all()
                .delete(BASE_URL + "/project/{code}")
                .then().log().all()
                .statusCode(200)
                .body("status", equalTo(true));
    }

}
