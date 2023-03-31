package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
public class Util implements CommandLineRunner {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public Util(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Transactional
    @Override
    public void run(String... args) {
        Role ROLE_ADMIN = new Role("ROLE_ADMIN");
        roleService.addRole(ROLE_ADMIN);
        Role ROLE_USER = new Role("ROLE_USER");
        roleService.addRole(ROLE_USER);

        User user = new User();
        user.setFirstname("Andrew");
        user.setLastname("Kim");
        user.setAge("32");
        user.setEmail("admin@mail.ru");
        user.setPassword("qwe123");
        user.setRoles(new HashSet<>(Arrays.asList(ROLE_ADMIN, ROLE_USER)));
        userService.addUser(user);
    }
}