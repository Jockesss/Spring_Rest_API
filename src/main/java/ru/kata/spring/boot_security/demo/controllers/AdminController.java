package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@Controller
public class AdminController {

    private UserService userService;
    private RoleService roleService;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UserService userService, RoleService roleService,
                           RoleRepository roleRepository) {
        this.userService = userService;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
    }

    @GetMapping(value = "admin")
    public String viewUsers(Model model, Principal principal) {
        model.addAttribute("adminPage", userService.getUsersList());
        model.addAttribute("user", userService.loadUserByUsername(principal.getName()));
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin-page";
    }

    @GetMapping(value = "admin/new")
    public String getNewUserForm(Model model, Principal principal) {
        model.addAttribute("admin", userService.loadUserByUsername(principal.getName()));
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "add-new-user";
    }

    @PostMapping(value = "admin/new")
    public String addNewUser(@ModelAttribute("user") User user, @RequestParam("rolesSelected") Long[] rolesId,
                             BindingResult bindingResult) throws Exception {
        Set<Role> roles = new HashSet<>();
        for (Long roleId : rolesId) {
            roles.add(roleRepository.getById(roleId));
        }
        user.setRoles(roles);
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}")
    public String getFormUserUpdate(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "admin-page";
    }

    @PatchMapping("/admin/{id}")
    public String getUpdate(@ModelAttribute("_userForma") @Valid User user, BindingResult bindingResult, @PathVariable("id") Long id) {
        userService.updateUser(id, user, true);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("admin/{id}/page")
    public String getUserPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("userPage", userService.getUserById(id));
        return "user-page";
    }

    @GetMapping(value = "/index")
    public String index() {
        return "index";
    }

    private Collection<Role> getRoleChek() {
        Collection<Role> roleSet = roleService.getAllRoles();
        return roleSet;
    }
}
