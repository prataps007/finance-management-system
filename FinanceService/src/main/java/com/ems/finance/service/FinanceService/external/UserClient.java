package com.ems.finance.service.FinanceService.external;

import com.ems.finance.service.FinanceService.entities.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "UserService")
public interface UserClient {

    @GetMapping("/users/{userId}")
    User getUserById(@PathVariable("userId") String userId);
}

