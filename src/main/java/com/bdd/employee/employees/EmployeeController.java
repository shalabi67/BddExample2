package com.bdd.employee.employees;

import com.bdd.employee.departments.Department;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            ,@ApiResponse(code = 400, message = EmployeeService.UNDEFINED)
            ,@ApiResponse(code = 409, message = EmployeeService.EMAIL_EXISTS)
            ,@ApiResponse(code = 412, message = EmployeeService.INVALID_EMAIL + "or " + EmployeeService.INVALID_DATE)
    })
    @PostMapping("")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Result<Employee> result = employeeService.addEmployee(employee);
        if(result.getErrorNumber() == ErrorEnum.Success) {
            return new ResponseEntity<>(result.getResult(), HttpStatus.CREATED);
        }

        return getError(result);
    }

    @ApiOperation(value = "update employee", response= Employee.class, httpMethod="POST")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee updated.")
            ,@ApiResponse(code = 400, message = EmployeeService.UNDEFINED)
            ,@ApiResponse(code = 404, message = EmployeeService.EMPLOYEE_NOT_EXIST)
            ,@ApiResponse(code = 409, message = EmployeeService.EMAIL_EXISTS)
            ,@ApiResponse(code = 412, message = EmployeeService.INVALID_EMAIL + "or " + EmployeeService.INVALID_DATE)
    })
    @PutMapping("/{employeeId}")
    public ResponseEntity<Employee> changeEmployee(@PathVariable Long employeeId, @RequestBody Employee employee) {
        Result<Employee> result = employeeService.changeEmployee(employeeId, employee);
        if(result.getErrorNumber() == ErrorEnum.Success) {
            return new ResponseEntity<>(result.getResult(), HttpStatus.OK);
        }

        return getError(result);
    }

    @ApiOperation(value = "delete employee")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee deleted.")
            ,@ApiResponse(code = 404, message = EmployeeService.EMPLOYEE_NOT_EXIST)
    })
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long employeeId) {
        Result<Employee> result = employeeService.deleteEmployee(employeeId);
        if(result.getErrorNumber() == ErrorEnum.Success) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return getError(result);
    }

    private ResponseEntity getError(Result<Employee> result) {
        switch(result.getErrorNumber()) {
            case EmailExists: return new ResponseEntity(result.getErrorMessage(), HttpStatus.CONFLICT);
            case InvalidDate: return new ResponseEntity(result.getErrorMessage(), HttpStatus.EXPECTATION_FAILED);
            case InvalidEmail: return new ResponseEntity(result.getErrorMessage(), HttpStatus.PRECONDITION_FAILED);
            case EmployeeNotExist: return new ResponseEntity(result.getErrorMessage(), HttpStatus.NOT_FOUND);
            case Error:
            default: return new ResponseEntity(result.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
