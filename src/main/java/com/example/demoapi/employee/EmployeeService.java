package com.example.demoapi.employee;

import com.example.demoapi.exception.BadRequestException;
import com.example.demoapi.exception.ErrorCode;
import com.example.demoapi.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final  EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public List<EmployeeDto> getEmployee() {

        return employeeRepository.getEmployee().stream()
                .map(employee -> modelMapper.map(employee,EmployeeDto.class))
                .collect(Collectors.toList());
    }

    public EmployeeDto inquiryEmployee(Long id) {

        Employee employee = this.employeeRepository.inquiryEmployee(id).orElseThrow(() -> new NotFoundException(ErrorCode.ERR_NOT_FOUND.code, "employee not found"));
        return modelMapper.map(employee, EmployeeDto.class);

    }

    public ResponseModel insertEmployee(EmployeeRequestModel request) throws Exception {

        log.info("insertEmployeeRequestModel: {}",request);

        try {
            this.employeeRepository.insertEmployee(request);


        } catch (Exception e) {
            throw new Exception(e.getMessage());
//            return ResponseModel.builder()
//                    .message(e.getMessage())
//                    .build();
        }

        return ResponseModel.builder()
                .code("200")
                .message("Success")
                .build();
    }
    public int saveEmployee(EmployeeDto employeeDto) {

        return this.employeeRepository.saveEmployee(modelMapper.map(employeeDto,Employee.class));
    }

//    public ResponseModel updateEmployee(EmployeeRequestUpdateModel request) throws Exception {
//
//
//        log.info("updateEmployeeRequestModel: {}",request);
//
//        try {
//            Optional<Employee> employee = Optional.of(this.employeeRepository.inquiryEmployee(request.getId()))
//                    .orElseThrow(() -> new Exception("not found"));
//
//            if(!employee.isEmpty()) {
//                this.employeeRepository.updateEmployee(request);
//            } else {
//                throw new Exception("not found");
//            }
//
//        } catch (Exception e) {
//            throw new Exception(e.getMessage());
//        }
//
//        return ResponseModel.builder()
//                .code("200")
//                .message("Success")
//                .build();
//
//    }

    @Transactional
    public void updateEmployee(EmployeeDto request) throws Exception {

        log.info("updateEmployeeRequestModel: {}",request);
        if (this.employeeRepository.updateEmployee(modelMapper.map(request, Employee.class)) == 0) {
            throw new BadRequestException(ErrorCode.ERR_PUT.code, "employee ID not found");
        }

    }

  /*  public int updatePatchEmployee(EmployeeDto request) throws Exception {

        log.info("updatePatchEmployeeRequestModel: {}",request);
        return this.employeeRepository.updateEmployee(request);


    }*/

    public void deleteEmployee(Long id) {
        log.info("deleteEmployeeRequestModel: {}", id);
        this.inquiryEmployee(id);
        this.employeeRepository.deleteEmployee(id);

    }
}
