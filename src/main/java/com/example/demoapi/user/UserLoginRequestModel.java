package com.example.demoapi.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLoginRequestModel {

    private String userName;
    private String password;
}
