package com.bdd.employee.itegration.employee;

import com.bdd.employee.configurations.SecurityConfiguration;
import com.bdd.employee.employees.Employee;
import com.bdd.employee.employees.EmployeeController;
import com.bdd.employee.employees.ErrorEnum;
import com.bdd.employee.employees.Result;
import com.bdd.employee.employees.JsonMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.Base64Utils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class EmployeeSystem extends com.bdd.employee.facade.EmployeeSystem {
    private MockMvc mockMvc;

    public EmployeeSystem(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Override
    public Result<Employee> createEmployee(Employee employee) {
        JsonMapper<Employee> jsonMapper = new JsonMapper<>();
        MvcResult mvcResult = null;
        try {
            mvcResult = this.mockMvc
                    .perform(
                            post(EmployeeController.URL)
                                    .header(HttpHeaders.AUTHORIZATION,
                                            "Basic " + Base64Utils.encodeToString((SecurityConfiguration.ADMIN_USER + ":" + SecurityConfiguration.ADMIN_PASSWORD).getBytes()))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(jsonMapper.toString(employee))
                                    .accept(MediaType.APPLICATION_JSON)
                    )
                    .andReturn();
            String content = mvcResult.getResponse().getContentAsString();
            int status = mvcResult.getResponse().getStatus();
            switch (status) {
                case 201: {
                    Employee employeeResult = jsonMapper.toObject(content, Employee.class);
                    return new Result<>(employeeResult);
                }
                case 409: return new Result<>(ErrorEnum.EmailExists, content);
                case 417: return new Result<>(ErrorEnum.InvalidDate, content);
                case 412: return new Result<>(ErrorEnum.InvalidEmail, content);
                case 400:
                default: return new Result<>(ErrorEnum.Error, content);
            }
        } catch (Exception e) {
            return new Result<>(ErrorEnum.Error, e.getMessage());
        }



    }

    @Override
    public Result<Employee> updateEmployee(Employee employee) {
        JsonMapper<Employee> jsonMapper = new JsonMapper<>();
        MvcResult mvcResult = null;
        try {
            mvcResult = this.mockMvc
                    .perform(
                            put(EmployeeController.URL + "/" + employee.getUuid())
                                    .header(HttpHeaders.AUTHORIZATION,
                                            "Basic " + Base64Utils.encodeToString((SecurityConfiguration.ADMIN_USER + ":" + SecurityConfiguration.ADMIN_PASSWORD).getBytes()))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(jsonMapper.toString(employee))
                                    .accept(MediaType.APPLICATION_JSON)
                    )
                    .andReturn();
            String content = mvcResult.getResponse().getContentAsString();
            int status = mvcResult.getResponse().getStatus();
            switch (status) {
                case 200: {
                    Employee employeeResult = jsonMapper.toObject(content, Employee.class);
                    return new Result<>(employeeResult);
                }
                case 409: return new Result<>(ErrorEnum.EmailExists, content);
                case 417: return new Result<>(ErrorEnum.InvalidDate, content);
                case 412: return new Result<>(ErrorEnum.InvalidEmail, content);
                case 404: return new Result<>(ErrorEnum.EmployeeNotExist, content);
                case 400:
                default: return new Result<>(ErrorEnum.Error, content);
            }
        } catch (Exception e) {
            return new Result<>(ErrorEnum.Error, e.getMessage());
        }
    }

    @Override
    public ResponseEntity deleteEmployee(Long employeeId) {
        JsonMapper<Employee> jsonMapper = new JsonMapper<>();
        MvcResult mvcResult = null;
        try {
            mvcResult = this.mockMvc
                    .perform(
                            delete(EmployeeController.URL + "/" + employeeId)
                                    .header(HttpHeaders.AUTHORIZATION,
                                            "Basic " + Base64Utils.encodeToString((SecurityConfiguration.ADMIN_USER + ":" + SecurityConfiguration.ADMIN_PASSWORD).getBytes()))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                    )
                    .andReturn();
            String content = mvcResult.getResponse().getContentAsString();
            int status = mvcResult.getResponse().getStatus();
            switch (status) {
                case 200: {
                    Employee employeeResult = jsonMapper.toObject(content, Employee.class);
                    return new ResponseEntity(HttpStatus.OK);
                }
                case 404: return new ResponseEntity(HttpStatus.NOT_FOUND);
                case 400:
                default: return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Long getAutoGeneratedId() {
        return null;
    }
}
