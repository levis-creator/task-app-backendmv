package com.micosoft.taskappbackendmv.users;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor

public class UserController {
    @Autowired
    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUsers(user);
    }


    @GetMapping("{id}")
    public Optional<User> getSingleUser(@PathVariable("id") String id) {
        return userService.getSingleUser(id);
    }

    @DeleteMapping("{id}")
    public String deleteUser(@PathVariable("id") String id) {
        return userService.deleteUser(id);
    }

    @PutMapping("{id}")
    public User updateUser(@PathVariable("id") String id, User user) {
        return userService.updateUser(id, user);
    }
}
