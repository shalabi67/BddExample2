package com.bdd.employee.steps.employee;

import com.bdd.employee.EmployeeSystemFactory;
import com.bdd.employee.employees.Employee;
import com.bdd.employee.employees.ErrorEnum;
import com.bdd.employee.employees.Result;
import com.bdd.employee.facade.EmployeeSystem;
import com.bdd.framework.Properties;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;


public class UpdateEmployeeSteps {
    private EmployeeSystem employeeSystem;
    private Employee employee;

    @Autowired
    MockMvc mockMvc;

    @Given("^Employee system is started and it has three employees 1, 2, and 3$")
    public void employee_system_is_started_and_it_has_three_department_1_2_and_3() throws Throwable {
        Properties properties = new Properties();
        properties.setMockMvc(mockMvc);

        employeeSystem = EmployeeSystemFactory.create().createEmployeeSystem(properties);
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