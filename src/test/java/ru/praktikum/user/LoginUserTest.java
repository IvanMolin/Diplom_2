package ru.praktikum.user;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;

public class LoginUserTest {
    private User user;
    private UserClient userClient;
    private Credentials credentials;
    private String accessToken;

    @Before
    public void setUp(){
        userClient = new UserClient();
        user = UserGenerator.getRandomUser();
    }

    @After
    public void cleanUp(){
        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    @Description("Проверка что есть возможность авторизоваться под существующим пользователем")
    public void successfulUserAuthorization(){
        userClient.createUser(user);
        credentials = Credentials.from(user);
        ValidatableResponse response = userClient.loginUser(credentials);
        accessToken = response.extract().path("accessToken");
        response.assertThat().body("success", equalTo(true)).and().statusCode(200);
    }

    @Test
    @DisplayName("Логин с неверным email")
    @Description("Проверка что нет возможности авторизоваться с неверной почтой")
    public void authorizationErrorWithInvalidEmail(){
        ValidatableResponse responseCreate = userClient.createUser(user);
        accessToken = responseCreate.extract().path("accessToken");
        credentials = Credentials.from(user);
        credentials.setEmail("I1w3fd$%532$%3@gmail.com");
        ValidatableResponse responseLogin = userClient.loginUser(credentials);
        responseLogin.assertThat().body("success", equalTo(false))
                .and().body("message", equalTo("email or password are incorrect")).and().statusCode(401);
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    @Description("Проверка что нет возможности авторизоваться с неверным паролем")
    public void authorizationErrorWithInvalidPassword(){
        ValidatableResponse responseCreate = userClient.createUser(user);
        accessToken = responseCreate.extract().path("accessToken");
        credentials = Credentials.from(user);
        credentials.setPassword("1q2w3e$R");
        ValidatableResponse responseLogin = userClient.loginUser(credentials);
        responseLogin.assertThat().body("success", equalTo(false))
                .and().body("message", equalTo("email or password are incorrect")).and().statusCode(401);
    }
}
