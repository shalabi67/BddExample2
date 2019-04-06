package com.bdd.employee.employees;

import com.bdd.employee.departments.Department;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EmployeeController.URL)
@Api("Employee API")
public class EmployeeController {
    public static final String URL = "/employees";

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @ApiOperation(value = "create employee", response= Employee.class, httpMethod="POST")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Employee created.")
            ,@ApiResponse(code = 400, message = "It is a bad request some undefined error happened.")
            ,@ApiResponse(code = 409, message = "Email exists.")
            ,@ApiResponse(code = 412, message = "Invalid email or invalid date.")
    })
    @PostMapping("")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Result<Employee> result = employeeService.addEmployee(employee);
        if(result.getErrorNumber() == ErrorEnum.Success) {
            return new ResponseEntity<>(result.getResult(), HttpStatus.CREATED);
        }

        return getError(result);
    }

    private ResponseEntity getError(Result<Employee> result) {
        switch(result.getErrorNumber()) {
            case EmailExists: return new ResponseEntity(result.getErrorMessage(), HttpStatus.CONFLICT);
            case InvalidDate: return new ResponseEntity(result.getErrorMessage(), HttpStatus.EXPECTATION_FAILED);
            case InvalidEmail: return new ResponseEntity(result.getErrorMessage(), HttpStatus.PRECONDITION_FAILED);
            case Error:
            default: return new ResponseEntity(result.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
