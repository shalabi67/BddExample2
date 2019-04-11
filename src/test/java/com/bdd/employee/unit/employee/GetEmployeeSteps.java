package com.bdd.employee.unit.employee;

import com.bdd.employee.employees.Employee;
import com.bdd.employee.employees.ErrorEnum;
import com.bdd.employee.employees.Result;
import com.bdd.employee.facade.EmployeeSystem;
import com.bdd.employee.facade.EventMocks;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import cucumber.api.junit.Cucumber;
import org.junit.Assert;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
public class GetEmployeeSteps {
    private EmployeeSystem employeeSystem;
    private Long employeeId;
    private EventMocks eventMocks;

    @Given("^Get Employee system is started and it has three employees 1, 2, 3$")
    public void get_employee_system_is_started_and_it_has_three_employees_1_2_3() throws Throwable {
        com.bdd.employee.unit.employee.EmployeeSystem tempSystem = new com.bdd.employee.unit.employee.EmployeeSystem();
        eventMocks = tempSystem.getEventMocks();
        employeeSystem = tempSystem;

        for(long i=1; i<4 ;i++) {
            Employee employee = eventMocks.getEmployee(i);
            employeeSystem.createEmployee(employee);
        }
    }

    @When("^user provides valid employee id to get a valid employee (.+)$")
    public void user_provides_valid_employee_id_to_get_a_valid_employee(String employeeid) throws Throwable {
        this.employeeId = Long.parseLong(employeeid);;
    }

    @When("^user provides an employee id to get a non exiting employee (.+)$")
    public void user_provides_an_employee_id_to_get_a_non_exiting_employee(String employeeid) throws Throwable {
        this.employeeId = Long.parseLong(employeeid);
    }

    @Then("^system gets employee$")
    public void system_gets_employee() throws Throwable {
        Result<Employee> employeeResult = employeeSystem.getEmployee(employeeId);
        Assert.assertNotNull(employeeResult.getResult());
        Assert.assertEquals(employeeId, employeeResult.getResult().getUuid());
    }

    @Then("^get employee system returns non exiting employee$")
    public void get_employee_system_returns_non_exiting_employee() throws Throwable {
        Result<Employee> employeeResult = employeeSystem.getEmployee(employeeId);
        Assert.assertEquals(ErrorEnum.EmployeeNotExist, employeeResult.getErrorNumber());
    }

}