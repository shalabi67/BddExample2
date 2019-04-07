package com.bdd.employee.unit.employee;

import com.bdd.employee.employees.Employee;
import com.bdd.employee.events.EmployeeEvent;
import com.bdd.employee.events.EventTypeEnum;
import com.bdd.employee.facade.EmployeeSystem;
import com.bdd.employee.facade.EventMocks;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.And;
import cucumber.api.junit.Cucumber;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RunWith(Cucumber.class)
public class DeleteEmployeeSteps {
    private EmployeeSystem employeeSystem;
    private Long employeeId;
    private EventMocks eventMocks;

    @Given("^Delete Employee system is started and it has three employees$")
    public void delete_employee_system_is_started_and_it_has_three_employees() throws Throwable {
        com.bdd.employee.unit.employee.EmployeeSystem tempSystem = new com.bdd.employee.unit.employee.EmployeeSystem();
        eventMocks = tempSystem.getEventMocks();
        employeeSystem = tempSystem;

        for(long i=1; i<4 ;i++) {
            Employee employee = eventMocks.getEmployee(i);
            employeeSystem.createEmployee(employee);
        }
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
        List<EmployeeEvent> employeeEvents = eventMocks.getEmployeeEvents(employeeId);

        Assert.assertEquals(2,employeeEvents.size());
        Assert.assertEquals(EventTypeEnum.deleted, employeeEvents.get(1).getEventType());

    }


    @Then("^delete system returns non exiting employee$")
    public void delete_system_returns_non_exiting_employee() throws Throwable {
        ResponseEntity responseEntity = employeeSystem.deleteEmployee(employeeId);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }


}