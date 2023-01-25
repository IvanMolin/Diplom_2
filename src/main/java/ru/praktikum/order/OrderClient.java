package ru.praktikum.order;

import io.restassured.response.ValidatableResponse;
import ru.praktikum.client.Client;
import static io.restassured.RestAssured.given;
import io.qameta.allure.Step;

public class OrderClient extends Client{

    private static final String ORDER_PATH = "/api/orders";

    @Step("Создание заказа с авторизацией")
    public ValidatableResponse createOrderWithAuthorization(String accessToken, Order order){
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }
    @Step("Создание заказа без авторизации")
    public ValidatableResponse createOrderWithoutAuthorization(Order order){
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }
    @Step("Получение заказа пользователя")
    public ValidatableResponse getUserOrder(String accessToken){
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .get(ORDER_PATH)
                .then();
    }



}
