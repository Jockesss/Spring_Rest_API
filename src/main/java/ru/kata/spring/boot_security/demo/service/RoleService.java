package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entities.Role;
import java.util.List;
import java.util.Optional;

public interface RoleService {
    Optional<Role> findById(long id);

    List<Role> getAllRoles();

    void addRole(Role role);

}
