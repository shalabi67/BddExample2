package com.bdd.employee.itegration.events;

import com.bdd.employee.employees.Employee;
import com.bdd.employee.events.EmployeeEvent;
import com.bdd.employee.facade.EventsSystem;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@Ignore
public class EventSteps {
    private EventsSystem eventsSystem;
    Employee employee;

    @Autowired
    MockMvc mockMvc;

    @Given("^Events system is started$")
    public void events_system_is_started() throws Throwable {
        eventsSystem = new com.bdd.employee.itegration.events.EventsSystem(mockMvc);
    }

    @When("^user provided an employee id (.+)$")
    public void user_provided_an_employee_id(String employeeid) throws Throwable {
        employee = new Employee();
        employee.setUuid(Long.parseLong(employeeid));
    }

    @When("^user requested to get events for a non exiting employee (.+)$")
    public void user_requested_to_get_events_for_a_non_exiting_employee(String employeeid) throws Throwable {
        employee = new Employee();
        employee.setUuid(Long.parseLong(employeeid));
    }

    @Then("^system returns all events realted to that employee sorted by event creation date$")
    public void system_returns_all_events_realted_to_that_employee_sorted_by_event_creation_date() throws Throwable {
        List<EmployeeEvent> employeeEvents = eventsSystem.getEvents(employee.getUuid());

        Assert.assertNotNull(employeeEvents);
        Assert.assertNotEquals(0, employeeEvents.size());
    }

    @Then("^system returns an empty list$")
    public void system_returns_an_empty_list() throws Throwable {
        List<EmployeeEvent> employeeEvents = eventsSystem.getEvents(employee.getUuid());

        Assert.assertNotNull(employeeEvents);
        Assert.assertEquals(0, employeeEvents.size());
    }

}