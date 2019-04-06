package com.bdd.employee.departments;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(DepartmentController.URL)
@Api("Departments API")
public class DepartmentController {
    public static final String URL = "/departments";
    private DepartmentRepository departmentRepository;  //notice no need in this case for a service, there is no business logic. later on if that is needed we can refactor.

    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @ApiOperation(value = "create department", response=Department.class, httpMethod="POST")
    @PostMapping("")
    public ResponseEntity<Department> addDepartment(@RequestBody Department department) {
        Department newDepartment = departmentRepository.save(department);
        return new ResponseEntity<>(newDepartment, HttpStatus.CREATED);
    }
}
