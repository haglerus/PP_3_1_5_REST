package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.utilities.UserWithPassword;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Map;
import java.util.Set;

@RestController
public class UsersRestController {

    UserService userService;

    public UsersRestController(@Autowired UserService userService) {
        this.userService = userService;
    }

    //    @ResponseBody
    @GetMapping(value = "/admin/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<User>> userGetList() {
        return new ResponseEntity<>(userService.listUsers(), HttpStatus.OK);
    }
    @GetMapping(value = "/admin/api/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> userGet(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @PostMapping(value = "/admin/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<User>> userAdd(@RequestBody User user) {
        userService.add(user);
        return new ResponseEntity<>(userService.listUsers(), HttpStatus.OK);
    }

    @PutMapping(value = "/admin/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<User>> userUpdate(@RequestBody UserWithPassword userEntry) {
        userService.update(userEntry.user, userEntry.password);
        return new ResponseEntity<>(userService.listUsers(), HttpStatus.OK);
    }

    @DeleteMapping (value = "/admin/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<User>> userDelete(@RequestBody Map.Entry<String, Long> userInfo)  {
        userService.delete(userInfo.getValue());
        return new ResponseEntity<>(userService.listUsers(), HttpStatus.OK);
    }
}
