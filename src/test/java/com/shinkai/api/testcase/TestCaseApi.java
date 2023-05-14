package com.shinkai.api.testcase;

import com.shinkai.api.BaseApi;
import com.shinkai.api.endpoint.TestCaseEndPoint;

import static com.shinkai.helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public class TestCaseApi extends BaseApi {

    public TestCaseDetailsResponseDto createTestCase(CreateTestCaseRequestDto testCase) {
        return with()
                .filter(withCustomTemplates())
                .given().spec(defaultRequestSpec)
                .body(testCase)
                .when()
                .post(TestCaseEndPoint.CREATE)
                .then()
                .statusCode(200)
                .extract().as(TestCaseDetailsResponseDto.class);
    }

    public void deleteTestCase(int testCaseId) {
        with().filter(withCustomTemplates())
                .spec(defaultRequestSpec)
                .pathParam("id", testCaseId)
                .when()
                .delete(TestCaseEndPoint.DELETE)
                .then()
                .statusCode(anyOf(is(200), is(202), is(204)));
    }

}

