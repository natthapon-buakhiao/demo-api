package com.example.demoapi.user;

import com.example.demoapi.employee.Employee;
import com.example.demoapi.employee.EmployeeDto;
import com.example.demoapi.exception.ErrorCode;
import com.example.demoapi.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserLoginService {

    private final ModelMapper modelMapper;
    private final UserLoginRepository userLoginRepository;


    public void userLogin(UserLoginRequestModel requestModel) {

        int count = 0;
        User userByUserName = new User();
        User user = new User();

        try {
            userByUserName = this.userLoginRepository.inquiryUserByUserName(requestModel).orElseThrow(() -> new NotFoundException(ErrorCode.ERR_NOT_FOUND.code, "user not found"));

            user = this.userLoginRepository.inquiryUser(requestModel).orElseThrow(() -> new NotFoundException(ErrorCode.ERR_NOT_FOUND.code, "Invalid password"));

            if(userByUserName.getCountInvalid() == 2 && userByUserName.getUserName().equals(user.getUserName())) {
                this.userLoginRepository.UpdateCountUser(requestModel.getId(),count);
            }
        } catch (NotFoundException e) {
            if(e.getMessage().equals("Invalid password")) {
                count = userByUserName.getCountInvalid() +1;
                if(count == 3) {
                    this.userLoginRepository.lockUser(requestModel.getId());
                    throw new NotFoundException("lock user");
                }
                this.userLoginRepository.UpdateCountUser(requestModel.getId(),count);
            }
            log.info("count : {}",userByUserName.getCountInvalid());

            throw new NotFoundException(e.getMessage());
        }


    }
}
