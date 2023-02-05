package ru.praktikum.order;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.user.User;
import ru.praktikum.user.UserClient;
import ru.praktikum.user.UserGenerator;
import static org.hamcrest.CoreMatchers.equalTo;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;

public class GetOrderTest {
    private User user;
    private UserClient userClient;
    private OrderClient orderClient;
    private Order order;
    private String accessToken;

    @Before
    public void setUp(){
        userClient = new UserClient();
        user = UserGenerator.getRandomUser();
        orderClient = new OrderClient();
        order = OrderGenerator.getDefaultOrder();
    }

    @After
    public void cleanUp(){
        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Получение заказа авторизованного пользователя")
    @Description("Проверка что есть возможность получить заказ под авторизованным пользователем")
    public void getOrderFromAnAuthorizedUser(){
        ValidatableResponse responseCreateUser = userClient.createUser(user);
        accessToken = responseCreateUser.extract().path("accessToken");
        orderClient.createOrderWithAuthorization(accessToken, order);
        ValidatableResponse responseOrder = orderClient.getUserOrder(accessToken);
        responseOrder.assertThat().body("success", equalTo(true)).and().statusCode(200);
    }

    @Test
    @DisplayName("Получение заказа неавторизованного пользователя")
    @Description("Проверка что нет возможности получить заказ без авторизации пользователя в системе")
    public void getOrderFromAnUnauthorizedUser(){
        ValidatableResponse responseCreateUser = userClient.createUser(user);
        accessToken = responseCreateUser.extract().path("accessToken");
        orderClient.createOrderWithAuthorization(accessToken, order);
        ValidatableResponse responseOrder = orderClient.getUserOrder("");
        responseOrder.assertThat().body("success", equalTo(false))
                .and().body("message", equalTo("You should be authorised")).and().statusCode(401);
    }
}
