package com.example.demoapi.employee;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ResponseModel {
    private String message;
    private String code;
}
