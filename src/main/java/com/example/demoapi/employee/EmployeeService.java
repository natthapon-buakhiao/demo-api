package com.example.demoapi.employee;

import com.example.demoapi.exception.BadRequestException;
import com.example.demoapi.exception.ErrorCode;
import com.example.demoapi.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
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

    public int saveEmployee(EmployeeDto employeeDto) {

        return this.employeeRepository.saveEmployee(modelMapper.map(employeeDto,Employee.class));
    }


    @Transactional
    public void updateEmployee(EmployeeDto request) {

        log.info("updateEmployeeRequestModel: {}",request);
        if (this.employeeRepository.updateEmployee(modelMapper.map(request, Employee.class)) == 0) {
            throw new BadRequestException(ErrorCode.ERR_PUT.code, "employee ID not found");
        }

    }

    public void deleteEmployee(Long id) {
        log.info("deleteEmployeeRequestModel: {}", id);
        this.inquiryEmployee(id);
        this.employeeRepository.deleteEmployee(id);

    }
}
