package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
public class UsersController {

    private final UserService userService;
    private final RoleService roleService;

    public UsersController(@Autowired UserService userService, @Autowired RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/")
    public String indexPage(ModelMap model, Authentication authentication) {
        if (authentication.getAuthorities().contains(roleService.getRole(1L))) {
            model.addAttribute("users", userService.listUsers());
        }
        List<Role> roles = roleService.listRoles();
        model.addAttribute("roles", roles);
        return "index";
    }
}
