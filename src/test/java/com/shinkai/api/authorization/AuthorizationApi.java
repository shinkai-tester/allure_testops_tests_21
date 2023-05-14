package com.shinkai.api.authorization;

import com.shinkai.api.endpoint.AuthEndPoint;
import com.shinkai.config.Auth;

import static com.shinkai.helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;

public class AuthorizationApi {

    private static final String ALLURE_TESTOPS_SESSION = "ALLURE_TESTOPS_SESSION";

    public static AuthorizationResponseDto getAuthorization() {
        AuthorizationResponseDto authorizationResponse = with()
                .filter(withCustomTemplates())
                .log().all()
                .formParam("grant_type", "apitoken")
                .formParam("scope", "openid")
                .formParam("token", Auth.config.apiToken())
                .when()
                .post(AuthEndPoint.OAUTH_TOKEN)
                .then()
                .statusCode(200)
                .extract().as(AuthorizationResponseDto.class);
        return authorizationResponse;
    }

    public static String getAuthorizationCookie() {
        String xsrfToken = getAuthorization().getJti();
        return with().filter(withCustomTemplates())
                .log().all()
                .header("X-XSRF-TOKEN", xsrfToken)
                .header("Cookie", "XSRF-TOKEN=" + xsrfToken)
                .formParam("username", Auth.config.usernameAllure())
                .formParam("password", Auth.config.passwordAllure())
                .when()
                .post(AuthEndPoint.LOGIN)
                .then()
                .statusCode(200).extract().response()
                .getCookie(ALLURE_TESTOPS_SESSION);
    }

}
