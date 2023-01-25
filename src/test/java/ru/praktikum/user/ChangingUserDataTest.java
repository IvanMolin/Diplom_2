package ru.praktikum.user;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;

public class ChangingUserDataTest {
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
    @DisplayName("Изменение данных авторизованного пользователя")
    @Description("Проверка что есть возможность изменить данные авторизованного пользователя")
    public void successfulChangeUserDataWithAuthorization(){
        ValidatableResponse responseCreate = userClient.createUser(user);
        accessToken = responseCreate.extract().path("accessToken");
        user.setEmail("Bek123$shenev$@yandex.ru");
        user.setPassword("1q2w3e$R%T");
        user.setName("Evgeniy061097$");
        ValidatableResponse responseChanging = userClient.updateDataUser(accessToken, user);
        responseChanging.assertThat().body("success", equalTo(true)).and().statusCode(200);
    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    @Description("Проверка что нет возможности  изменить данные пользователя без авторизации")
    public void errorChangingDataWithoutAuthorization(){
        ValidatableResponse responseCreate = userClient.createUser(user);
        accessToken = responseCreate.extract().path("accessToken");
        user.setEmail("Bek123$shenev$@yandex.ru");
        user.setPassword("1q2w3e$R%T");
        user.setName("Evgeniy061097$");
        ValidatableResponse responseChanging = userClient.updateDataUser("", user);
        responseChanging.assertThat().body("success", equalTo(false))
                .and().body("message", equalTo("You should be authorised")).and().statusCode(401);
    }
}
