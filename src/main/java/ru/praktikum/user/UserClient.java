package ru.praktikum.user;

import io.restassured.response.ValidatableResponse;
import ru.praktikum.client.Client;
import static io.restassured.RestAssured.given;
import io.qameta.allure.Step;

public class UserClient extends Client {
    private static final String CREATE_PATH = "/api/auth/register";
    private static final String LOGIN_PATH = "/api/auth/login";
    private static final String DELETE_AND_UPDATE_PATH = "/api/auth/user";

    @Step("Создание пользователя")
    public ValidatableResponse createUser(User user){
        return given()
                .spec(getSpec())
                .body(user)
                .when()
                .post(CREATE_PATH)
                .then();
    }
    @Step("Авторизация пользователя")
    public ValidatableResponse loginUser(Credentials credentials){
        return given()
                .spec(getSpec())
                .body(credentials)
                .when()
                .post(LOGIN_PATH)
                .then();
    }
    @Step("Изменение данных пользователя")
    public ValidatableResponse updateDataUser(String accessToken, User user){
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(DELETE_AND_UPDATE_PATH)
                .then();
    }
    @Step("Удаление пользователя")
    public ValidatableResponse deleteUser(String accessToken){
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .delete(DELETE_AND_UPDATE_PATH)
                .then();
    }
}
