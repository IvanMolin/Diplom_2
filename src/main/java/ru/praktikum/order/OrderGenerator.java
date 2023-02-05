package ru.praktikum.order;


import org.aspectj.weaver.ast.Or;

import java.util.ArrayList;
import java.util.Arrays;

public class OrderGenerator {
    public static Order getDefaultOrder(){
        ArrayList<String> defaultListOrder = new ArrayList<>();
        defaultListOrder.add("61c0c5a71d1f82001bdaaa6d");
        defaultListOrder.add("61c0c5a71d1f82001bdaaa6f");
        defaultListOrder.add("61c0c5a71d1f82001bdaaa71");
        defaultListOrder.add("61c0c5a71d1f82001bdaaa73");
        return new Order(defaultListOrder);
    }

    public static Order getOrderInvalidIngredientHash(){
        ArrayList<String> invalidListOrder = new ArrayList<>();
        invalidListOrder.add("61c1c1a11d1182001bdaaa6d");
        return new Order(invalidListOrder);
    }

    public static Order getOrderWithoutIngredients(){
        ArrayList<String> emptyListOrder = new ArrayList<>();
        return new Order(emptyListOrder);
    }
}
