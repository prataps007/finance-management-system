package com.ems.user.service.UserService.controllers;

import com.ems.user.service.UserService.entities.User;
import com.ems.user.service.UserService.exceptions.UserAlreadyExistsException;
import com.ems.user.service.UserService.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity<User> register(@RequestBody User user) {

        // Check if user already exists by email
        if (userService.doesUserExist(user.getEmail())) {
            throw new UserAlreadyExistsException("User already exists with email: " + user.getEmail());
        }

        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        User user1 = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);

    }

    @GetMapping("{userId}")
    @CircuitBreaker(name = "expenseBreaker", fallbackMethod = "expenseBreakerFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    // fallback method for circuitbreaker
    public ResponseEntity<User> expenseBreakerFallback(String userId, Exception ex){
        logger.info("Fallback is executed because service is down : " , ex.getMessage());

        User user = User.builder()
                .email("dummy@gmail.com")
                .name("Dummy")
                .role("This is dummy user because some service is down")
                .userId("141234")
                .build();

        return new ResponseEntity<>(user,HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }
}
