package com.ems.finance.service.FinanceService.external.sevices;

import com.ems.finance.service.FinanceService.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    public boolean isUserValid(String userId) {
        String userServiceUrl = "http://UserService/users/" + userId;

        try {
            ResponseEntity<User> response = restTemplate.getForEntity(userServiceUrl, User.class);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            return false;
        }
    }
}


