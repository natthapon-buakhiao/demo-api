package com.example.demoapi.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String userName;
    private String email;
    private int countInvalid;
    private String lockUser;
}
