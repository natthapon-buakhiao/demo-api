package com.example.demoapi.user;

import com.example.demoapi.employee.EmployeeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")

@RequiredArgsConstructor
@Slf4j
public class UserLoginController {

    private final UserLoginService userLoginService;

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody UserLoginRequestModel requestModel) {

        this.userLoginService.userLogin(requestModel);
        return ResponseEntity.ok("login success");
    }
}
