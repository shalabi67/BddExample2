package com.bdd.employee.unit.employee;

import com.bdd.employee.employees.Employee;
import com.bdd.employee.employees.ErrorEnum;
import com.bdd.employee.employees.Result;
import com.bdd.employee.events.EmployeeEvent;
import com.bdd.employee.events.EventTypeEnum;
import com.bdd.employee.facade.EmployeeSystem;
import com.bdd.employee.facade.EventMocks;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.And;
import cucumber.api.junit.Cucumber;
import org.junit.Assert;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(Cucumber.class)
public class UpdateEmployeeSteps {
    private EmployeeSystem employeeSystem;
    private Employee employee;
    private Employee updateEmployee;
    private EventMocks eventMocks;

    @Given("^Employee system is started and it has three employees 1, 2, and 3$")
    public void employee_system_is_started_and_it_has_three_department_1_2_and_3() throws Throwable {
        com.bdd.employee.unit.employee.EmployeeSystem tempSystem = new com.bdd.employee.unit.employee.EmployeeSystem();
        eventMocks = tempSystem.getEventMocks();
        employeeSystem = tempSystem;

        //add three employees
        for(long i=1; i<4 ;i++) {
            Employee employee = eventMocks.getEmployee(i);
            employeeSystem.createEmployee(employee);
        }

    }

    @When("^user provides valid employee information to update a valid employee (.+) (.+), (.+), (.+), (.+), and (.+)$")
    public void user_provides_valid_employee_information_to_update_a_valid_employee_and(String employeeid, String firstname, String lastname, String email, String birthday, String department) throws Throwable {
        employee = employeeSystem.getEmployee(firstname, lastname, email, birthday, department);
        employee.setUuid(Long.parseLong(employeeid));
    }

    @Then("^system updates employee$")
    public void system_updates_employee() throws Throwable {
        Result<Employee> result = employeeSystem.updateEmployee(this.employee);
        Assert.assertNotNull(result.getResult());
    }

    @And("^update employee event will be recorded$")
    public void update_employee_event_will_be_recorded() throws Throwable {
        List<EmployeeEvent> employeeEvents = eventMocks.getEmployeeEvents(employee.getUuid());

        Assert.assertEquals(2,employeeEvents.size());
        Assert.assertEquals(EventTypeEnum.updated, employeeEvents.get(1).getEventType());
    }


    @When("^user provides invalid email with employee information to update an employee (.+) (.+), (.+), (.+), (.+), and (.+)$")
    public void user_provides_invalid_email_with_employee_information_to_update_an_employee_and(String employeeid, String firstname, String lastname, String email, String birthday, String department) throws Throwable {
        employee = employeeSystem.getEmployee(firstname, lastname, email, birthday, department);
        employee.setUuid(Long.parseLong(employeeid));
    }

    @Then("employee update system returns invalid email information")
    public void employeeUpdateSystemReturnsInvalidEmailInformation() {
        Result<Employee> result = employeeSystem.updateEmployee(this.employee);
        Assert.assertEquals(employee.getEmail(), ErrorEnum.InvalidEmail, result.getErrorNumber());
    }

    @When("^user provides exiting email with employee information to update an employee (.+) (.+), (.+), (.+), (.+), and (.+)$")
    public void user_provides_exiting_email_with_employee_information_to_update_an_employee_and(String employeeid, String firstname, String lastname, String email, String birthday, String department) throws Throwable {
        employee = employeeSystem.getEmployee(firstname, lastname, email, birthday, department);
        employee.setUuid(Long.parseLong(employeeid));
    }

    @Then("^employee update system returns exiting email information$")
    public void employee_update_system_returns_exiting_email_information() throws Throwable {
        employeeSystem.createEmployee(this.employee);
        Result<Employee> result = employeeSystem.updateEmployee(this.employee);
        Assert.assertEquals(employee.getEmail(), ErrorEnum.EmailExists, result.getErrorNumber());
    }

    @When("^user provides invalid birthday with employee information to update an employee (.+) (.+), (.+), (.+), (.+), and (.+)$")
    public void user_provides_invalid_birthday_with_employee_information_to_update_an_employee_and(String employeeid, String firstname, String lastname, String email, String birthday, String department) throws Throwable {
        employee = employeeSystem.getEmployee(firstname, lastname, email, birthday, department);
        employee.setUuid(Long.parseLong(employeeid));
    }

    @Then("^employee update system returns invalid birthday$")
    public void employee_update_system_returns_invalid_birthday() throws Throwable {
        Result<Employee> result = employeeSystem.updateEmployee(this.employee);
        Assert.assertEquals(employee.getBirthday(), ErrorEnum.InvalidDate, result.getErrorNumber());
    }
}