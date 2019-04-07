package com.bdd.employee.itegration.employee;

import com.bdd.employee.employees.Employee;
import com.bdd.employee.employees.ErrorEnum;
import com.bdd.employee.employees.Result;
import com.bdd.employee.facade.EmployeeSystem;
import com.bdd.employee.itegration.SpringBootBase;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@Ignore
public class CreateEmployeeSteps  {
    EmployeeSystem employeeSystem;
    Employee employee;

    @Autowired
    MockMvc mockMvc;

    @Given("^Employee system is started and it has three department 1, 2, and 3$")
    public void employee_system_is_started_and_it_has_three_department_1_2_and_3() throws Throwable {
        employeeSystem = new com.bdd.employee.itegration.employee.EmployeeSystem(mockMvc);
    }

    @When("^user provides valid employee information to create a valid employee (.+), (.+), (.+), (.+), and (.+)$")
    public void user_provides_valid_employee_information_to_create_a_valid_employee_and(String firstname, String lastname, String email, String birthday, String department) throws Throwable {
        employee = employeeSystem.getEmployee(firstname, lastname, email, birthday, department);
    }

    @When("^user provides invalid email with employee information to create an employee (.+), (.+), (.+), (.+), and (.+)$")
    public void user_provides_invalid_email_with_employee_information_to_create_an_employee_and(String firstname, String lastname, String email, String birthday, String department) throws Throwable {
        employee = employeeSystem.getEmployee(firstname, lastname, email, birthday, department);
    }

    @When("^user provides exiting email with employee information to create an employee (.+), (.+), (.+), (.+), and (.+)$")
    public void user_provides_exiting_email_with_employee_information_to_create_an_employee_and(String firstname, String lastname, String email, String birthday, String department) throws Throwable {
        employee = employeeSystem.getEmployee(firstname, lastname, email, birthday, department);
    }

    @When("^user provides invalid birthday with employee information to create an employee (.+), (.+), (.+), (.+), and (.+)$")
    public void user_provides_invalid_birthday_with_employee_information_to_create_an_employee_and(String firstname, String lastname, String email, String birthday, String department) throws Throwable {
        employee = employeeSystem.getEmployee(firstname, lastname, email, birthday, department);
    }

    @Then("^system creates employee and provide an auto increment employee uuid$")
    public void system_creates_employee_and_provide_an_auto_increment_employee_uuid() throws Throwable {
        Result<Employee> result = employeeSystem.createEmployee(this.employee);
        Assert.assertNotNull(result.getResult());
        Employee employee = result.getResult();
        Assert.assertNotNull(employee.getUuid());

        Assert.assertEquals(this.employee.getBirthday(), employee.getBirthday());
        Assert.assertEquals(this.employee.getEmail(), employee.getEmail());
        Assert.assertEquals(this.employee.getFirstName(), employee.getFirstName());
        Assert.assertEquals(this.employee.getLastName(), employee.getLastName());
    }
    @And("add employee event will be recorded")
    public void addEmployeeEventWillBeRecorded() {
        //TODO: write test for this part.
    }

    @Then("^system returns invalid email information$")
    public void system_returns_invalid_email_information() throws Throwable {
        Result<Employee> result = employeeSystem.createEmployee(this.employee);
        Assert.assertEquals(employee.getEmail(), ErrorEnum.InvalidEmail, result.getErrorNumber());
    }

    @Then("^system returns exiting email information$")
    public void system_returns_exiting_email_information() throws Throwable {
        employeeSystem.createEmployee(this.employee);
        Result<Employee> result = employeeSystem.createEmployee(this.employee);
        Assert.assertEquals(employee.getEmail(), ErrorEnum.EmailExists, result.getErrorNumber());
    }

    @Then("^system returns invalid birthday$")
    public void system_returns_invalid_birthday() throws Throwable {
        Result<Employee> result = employeeSystem.createEmployee(this.employee);
        Assert.assertEquals(employee.getBirthday(), ErrorEnum.InvalidDate, result.getErrorNumber());
    }
}