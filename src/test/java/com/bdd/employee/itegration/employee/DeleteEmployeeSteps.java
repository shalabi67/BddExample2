package com.bdd.employee.itegration.employee;

import com.bdd.employee.employees.Employee;
import com.bdd.employee.events.EmployeeEvent;
import com.bdd.employee.events.EventTypeEnum;
import com.bdd.employee.facade.EmployeeSystem;
import com.bdd.employee.facade.EventMocks;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@RunWith(Cucumber.class)
public class DeleteEmployeeSteps {
    EmployeeSystem employeeSystem;
    Long employeeId;

    @Autowired
    MockMvc mockMvc;

    @Given("^Delete Employee system is started and it has three employees$")
    public void delete_employee_system_is_started_and_it_has_three_employees() throws Throwable {
        employeeSystem = new com.bdd.employee.itegration.employee.EmployeeSystem(mockMvc);
    }

    @When("^user provides valid employee information to delete a valid employee (.+)$")
    public void user_provides_valid_employee_information_to_delete_a_valid_employee(String employeeid) throws Throwable {
        this.employeeId = Long.parseLong(employeeid);
    }

    @When("^user provides valid employee information to delete a non exiting employee (.+)$")
    public void user_provides_valid_employee_information_to_delete_a_non_exiting_employee(String employeeid) throws Throwable {
        this.employeeId = Long.parseLong(employeeid);
    }

    @Then("^system deletes employee$")
    public void system_deletes_employee_and_provide_an_auto_increment_employee_uuid() throws Throwable {
        ResponseEntity responseEntity = employeeSystem.deleteEmployee(employeeId);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    @And("^delete employee event will be recorded$")
    public void delete_employee_event_will_be_recorded() throws Throwable {
    }


    @Then("^delete system returns non exiting employee$")
    public void delete_system_returns_non_exiting_employee() throws Throwable {
        ResponseEntity responseEntity = employeeSystem.deleteEmployee(employeeId);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }


}