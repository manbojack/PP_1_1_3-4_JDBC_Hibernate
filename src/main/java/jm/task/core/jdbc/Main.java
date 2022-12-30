package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.service.UserService;

public class Main {
    private final static UserService userService = new UserServiceImpl();

    public static void main(String[] args) {
        userService.createUsersTable();

        userService.saveUser("Homer", "Simpson", (byte) 36);
        userService.saveUser("March", "Simpson", (byte) 34);
        userService.saveUser("Liza", "Simpson", (byte) 10);
        userService.saveUser("Bart", "Simpson", (byte) 8);

        userService.getAllUsers().forEach(System.out::println);

        userService.cleanUsersTable();

        userService.dropUsersTable();

    }
}
