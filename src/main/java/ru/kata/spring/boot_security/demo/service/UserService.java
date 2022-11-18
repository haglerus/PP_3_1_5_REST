package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Set;

public interface UserService extends UserDetailsService {
    void add(User user);

    User getUser(Long id);

    Set<User> listUsers();

    void update(User user, String password);

    void delete(Long id);

    void addRoles(User user, Long[] rolesIds);
}
