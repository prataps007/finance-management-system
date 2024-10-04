package com.ems.user.service.UserService.services;

import com.ems.user.service.UserService.entities.User;

import java.util.List;

public interface UserService {

    User registerUser(User user);

    User getUser(String userId);

    List<User> getAllUsers();

    // Method to check if the user exists based on the email
    boolean doesUserExist(String email);
}
