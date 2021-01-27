package com.example.demoapi.employee;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")

@RequiredArgsConstructor
@Slf4j
public class EmployeeController {


    private final EmployeeService employeeService;


    @GetMapping()
    public ResponseEntity<?> getEmployees(){

        return ResponseEntity.ok(this.employeeService.getEmployee());
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable Long id) throws Exception{

        log.info("getEmployee id: {}",id);

        return ResponseEntity.ok(this.employeeService.inquiryEmployee(id));
    }

    @PostMapping("/insert")
    public ResponseEntity<?> saveEmployee(@RequestBody EmployeeDto requestModel) throws Exception {

        this.employeeService.saveEmployee(requestModel);
        return ResponseEntity.created(null).build();
    }

//    @PostMapping("/update")
//    public ResponseEntity<ResponseModel> updateEmployee(@RequestBody EmployeeRequestUpdateModel request) throws Exception {
//
//        return ResponseEntity.ok(this.employeeService.updateEmployee(request));
//    }

    @PutMapping("/update")
    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeDto requestModel) throws Exception {

      this.employeeService.updateEmployee(requestModel);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updatePatchEmployee(@RequestBody EmployeeDto requestModel) throws Exception {

        this.employeeService.updateEmployee(requestModel);
        return  ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id)  {

        this.employeeService.deleteEmployee(id);
        return  ResponseEntity.noContent().build();
    }
}
