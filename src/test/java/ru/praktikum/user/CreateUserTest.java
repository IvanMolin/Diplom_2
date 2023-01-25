package ru.praktikum.user;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;

public class CreateUserTest {
    private User user;
    private UserClient userClient;
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
    @DisplayName("Создание уникального пользователя")
    @Description("Проверка что уникальный пользователь может быть создан")
    public void userCanBeCreated(){
        ValidatableResponse response = userClient.createUser(user);
        accessToken = response.extract().path("accessToken");
        response.assertThat().body("success", equalTo(true)).and().statusCode(200);
    }

    @Test
    @DisplayName("Создание пользователя который уже зарегистрирован")
    @Description("Проверка что нет возможности создать уже зарегистрированного пользователя")
    public void userCannotBeCreatedWithDuplicateLogin(){
        ValidatableResponse responseCreate = userClient.createUser(user);
        accessToken = responseCreate.extract().path("accessToken");
        ValidatableResponse responseCreateDouble = userClient.createUser(user);
        responseCreateDouble.assertThat().body("success", equalTo(false)).and().statusCode(403);
    }
}
