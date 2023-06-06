package com.divacademy.springbootdivacademy.controller;


import com.divacademy.springbootdivacademy.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController {

    private List<User> users = new ArrayList<>();
    private Long sequence = 0L;


    @PostMapping
    public void addUser(@RequestBody User user) {
        user.setId(sequence++);
        users.add(user);
    }

    @GetMapping
    public List<User> getUsers() {
        return users;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return users.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

    }

    @GetMapping("/by-name")
    public User getUserByName(@RequestParam String name) {
        return users.stream()
                .filter(s -> Objects.equals(s.getName(), name))
                .findFirst()
                .orElseThrow(RuntimeException::new);

    }

    @PutMapping("{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        User oldUser = users.stream()
                .filter(s -> s.getName().equals(id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        if(oldUser !=null){
            oldUser.setName(user.getName());
            oldUser.setSurname(user.getSurname());
        }
        return oldUser;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable Long id) {
        users.removeIf(user -> user.getId().equals(id));
    }
}
