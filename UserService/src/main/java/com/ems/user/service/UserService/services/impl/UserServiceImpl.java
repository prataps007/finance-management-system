package com.ems.user.service.UserService.services.impl;

import com.ems.user.service.UserService.entities.Budget;
import com.ems.user.service.UserService.entities.User;
import com.ems.user.service.UserService.exceptions.ResourceNotFoundException;
import com.ems.user.service.UserService.external.service.FinanceServiceClient;
import com.ems.user.service.UserService.repositories.UserRepo;
import com.ems.user.service.UserService.services.UserService;
import com.netflix.discovery.converters.Auto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private FinanceServiceClient financeServiceClient;


    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


//    @Autowired
//    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        //kafkaTemplate.send("user-topic", user);

        return savedUser;
    }

    public User getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id not found: " + userId));

        // fetch budget of user from budget service
        //ArrayList forObject = restTemplate.getForObject("http://localhost:8082/budgets/user/db1bab0f-fd53-4db8-a487-d668a4c2743c", ArrayList.class);
        Budget[] budgetOfUser = financeServiceClient.getUserBudget(userId);

        logger.info("{}",budgetOfUser);

        assert budgetOfUser != null;

        List<Budget> budgets = Arrays.stream(budgetOfUser).toList();

        user.setBudgets(budgets);

        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean doesUserExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

}
