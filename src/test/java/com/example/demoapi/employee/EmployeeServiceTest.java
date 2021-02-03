package com.example.demoapi.employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    private final ModelMapper modelMapper = new ModelMapper();

    @Mock
    private EmployeeRepository employeeRepository;

    private EmployeeService employeeService;

    private Collection<Employee> employeeListMock;

    private  EmployeeDto employeeDtoMock;

    private Employee employeeMock;


    @BeforeEach
    void setUp() {

        this.employeeService = new EmployeeService(employeeRepository,modelMapper);
        this.employeeDtoMock = EmployeeDto.builder()
                .id(1L)
                .firstName("AA")
                .lastName("BB")
                .email("AA@hotmail.com")
                .build();

        this.employeeMock = Employee.builder()
                .id(1L)
                .firstName("AA")
                .lastName("BB")
                .email("AA@hotmail.com")
                .build();

        this.employeeListMock = Arrays.asList(employeeMock);


    }

    @Test
    void getEmployee() {
        List<EmployeeDto> expected = new ArrayList<>();
        expected.add(this.employeeDtoMock);

        when(this.employeeRepository.getEmployee()).thenReturn(this.employeeListMock);
        List<EmployeeDto> employee = this.employeeService.getEmployee();
        verify(this.employeeRepository, times(1)).getEmployee();
        assertThat(employee, equalTo(expected));
    }
}