package com.bdd.employee.events;

import com.bdd.employee.employees.Employee;
import com.bdd.employee.employees.EmployeeController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventController {
    public static final String EVENTS_URL = "/events";
    private EmployeeEventRepository employeeEventRepository;

    public EventController(EmployeeEventRepository employeeEventRepository) {
        this.employeeEventRepository = employeeEventRepository;
    }

    @GetMapping(EmployeeController.URL + "/{employeeId}" + EVENTS_URL)
    public List<EmployeeEvent> getEvents(@PathVariable Long employeeId) {
        return employeeEventRepository.findAllByEmployeeIdOrderByCreationDate(employeeId);
    }
}
