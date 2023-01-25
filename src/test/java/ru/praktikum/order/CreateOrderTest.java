package ru.praktikum.order;

import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.user.User;
import ru.praktikum.user.UserClient;
import ru.praktikum.user.UserGenerator;
import static org.hamcrest.CoreMatchers.equalTo;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;

public class CreateOrderTest {
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


    @Test
    @DisplayName("Создание заказа под авторизованным клиентом")
    @Description("Проверяем что заказ с корректными ингридиентами создается")
    public void creatingAnOrderWithAuthorizationWithIngredients(){
        ValidatableResponse responseCreateUser = userClient.createUser(user);
        accessToken = responseCreateUser.extract().path("accessToken");
        ValidatableResponse responseCreateOrder = orderClient.createOrderWithAuthorization(accessToken, order);
        responseCreateOrder.assertThat().body("success", equalTo(true)).and().statusCode(200);
        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Создание заказа без авторизации под клиентом")
    @Description("Проверка что заказ с корректными ингридиентами создается")
    public void creatingAnOrderWithoutAuthorizationWithIngredients(){
        ValidatableResponse responseCreateOrder = orderClient.createOrderWithoutAuthorization(order);
        responseCreateOrder.assertThat().body("success", equalTo(true)).and().statusCode(200);
    }


}
