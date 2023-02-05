package ru.praktikum.user;

import java.util.Random;

public class UserGenerator {
    public static User getRandomUser(){
        return new User("UserEmail" + new Random().nextInt(500) + "@yandex.ru",
                "1q2w3e"+ new Random(500),
                "UserName"+ new Random(500));
    }

    public static User getUserWithoutEmail(){
        return new User("", "1q2w3e$R", "UserName");
    }

    public static User getUserWithoutPassword(){
        return new User("UserEmail123@yandex.ru", "", "UserName");
    }

    public static User getUserWithoutName(){
        return new User("UserEmail123@yandex.ru", "1q2w3e$R", "");
    }

}
