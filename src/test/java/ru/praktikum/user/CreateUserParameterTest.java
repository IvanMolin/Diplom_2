package ru.praktikum.user;

import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.hamcrest.CoreMatchers.equalTo;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;

@RunWith(Parameterized.class)
public class CreateUserParameterTest {
    private User user;
    private UserClient userClient;
    private String message;
    private int errorCode;


    public CreateUserParameterTest(User user, String message, int errorCode) {
        this.user = user;
        this.message = message;
        this.errorCode = errorCode;
    }

    @Parameterized.Parameters
    public static Object[][] getDataUserTest(){
        return new Object[][]{
                {UserGenerator.getUserWithoutEmail(), "Email, password and name are required fields", 403},
                {UserGenerator.getUserWithoutPassword(), "Email, password and name are required fields", 403},
                {UserGenerator.getUserWithoutName(), "Email, password and name are required fields", 403},
        };
    }

    @Before
    public void setUp(){
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Создание пользователя без заполнения одного из обязательных полей")
    @Description("Проверка что нет возможности создания пользователя без заполнения Email/Password/Name")
    public void cannotCreatedUserNoData(){
        ValidatableResponse response = userClient.createUser(user);
        response.assertThat().body("success", equalTo(false)).and().body("message", equalTo(message)).and().statusCode(errorCode);
    }
}
