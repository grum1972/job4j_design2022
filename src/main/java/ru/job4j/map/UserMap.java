package ru.job4j.map;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class UserMap {
    public static void main(String[] args) {
        User user1 = new User("Ivan", 5, new GregorianCalendar(1975, 12, 25));
        User user2 = new User("Ivan", 5, new GregorianCalendar(1975, 12, 25));
        Map<User, Object> map = new HashMap<>();
        map.put(user1, new Object());
        map.put(user2, new Object());
        for (User user : map.keySet()) {
            System.out.println(map.get(user));
        }
    }
}
