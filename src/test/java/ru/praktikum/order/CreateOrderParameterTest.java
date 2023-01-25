package ru.praktikum.order;

import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.hamcrest.CoreMatchers.equalTo;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;

@RunWith(Parameterized.class)
public class CreateOrderParameterTest {
    private OrderClient orderClient;
    private Order order;
    private String message;
    private int errorCode;

    public CreateOrderParameterTest(Order order, String message, int errorCode) {
        this.order = order;
        this.message = message;
        this.errorCode = errorCode;
    }

    @Parameterized.Parameters
    public static Object[][] getDataUserTest(){
        return new Object[][]{
                {OrderGenerator.getOrderWithoutIngredients(), "Ingredient ids must be provided", 400},
                {OrderGenerator.getOrderInvalidIngredientHash(), "One or more ids provided are incorrect", 400},
        };
    }

    @Before
    public void setUp(){
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Создание некорректного заказа")
    @Description("Проверка что нет возможности создания пустого заказа/заказа с неверным хешем ингредиентов")
    public void creatingAnInvalidOrder(){
        ValidatableResponse responseCreateOrder = orderClient.createOrderWithoutAuthorization(order);
        responseCreateOrder.assertThat().body("success", equalTo(false))
                .and().body("message", equalTo(message)).and().statusCode(errorCode);
    }

}
