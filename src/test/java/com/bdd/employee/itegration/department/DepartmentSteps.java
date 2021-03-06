package com.bdd.employee.itegration.department;

import com.bdd.employee.departments.Department;
import com.bdd.employee.facade.DepartmentSystem;
import com.bdd.employee.itegration.JpaDataBase;
import com.bdd.employee.itegration.SpringBootBase;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.junit.Ignore;

@Ignore
public class DepartmentSteps extends SpringBootBase {
    DepartmentSystem departmentSystem;
    Department department;

    @Given("^departments system is started$")
    public void startSystem() throws Throwable {
        departmentSystem = new com.bdd.employee.itegration.department.DepartmentSystem(mockMvc);
    }

    @When("^user provides valid department name (.+)$")
    public void user_provides_department_name(String departmentname) throws Throwable {
        department = new Department();
        department.setDepartmentName(departmentname);
    }

    @When("^user provides department name which exists in the system (.+)$")
    public void user_provides_department_name_which_exists_in_the_system(String departmentname) throws Throwable {
        throw new PendingException();
    }

    @When("^user provides department name which is invalid (.+)$")
    public void user_provides_department_name_which_is_invalid(String departmentname) throws Throwable {
        throw new PendingException();
    }

    @Then("^system creates department and provide an auto increment department id$")
    public void system_creates_department_and_provide_an_auto_increment_department_id() throws Throwable {
        Department newDepartment = departmentSystem.createDepartment(department);

        Assert.assertNotNull(newDepartment);
        Assert.assertNotNull(newDepartment.getDepartmentId());
        Assert.assertNotEquals(new Long(0), newDepartment.getDepartmentId());
        Assert.assertEquals(this.department.getDepartmentName(), newDepartment.getDepartmentName());

    }

    @Then("^system returns an error department exists$")
    public void system_returns_an_error_department_exists() throws Throwable {
        throw new PendingException();
    }

    @Then("^system returns an error invalid department name$")
    public void system_returns_an_error_invalid_department_name() throws Throwable {
        throw new PendingException();
    }

}