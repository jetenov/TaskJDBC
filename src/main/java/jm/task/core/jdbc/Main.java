package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();

        User aibat = new User("aibat", "jetenov", (byte) 24);
        User leo = new User("leo", "messi", (byte) 33);
        User luis = new User("luis", "suarez", (byte) 34);
        User neymar = new User("neymar", "junior", (byte) 29);

        userService.saveUser(aibat.getName(), aibat.getLastName(),aibat.getAge());
        userService.saveUser(leo.getName(), leo.getLastName(),leo.getAge());
        userService.saveUser(luis.getName(), luis.getLastName(),luis.getAge());
        userService.saveUser(neymar.getName(), neymar.getLastName(),neymar.getAge());

        List<User> list = userService.getAllUsers();
        for (User x:list) {
            System.out.println(x.toString());
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
