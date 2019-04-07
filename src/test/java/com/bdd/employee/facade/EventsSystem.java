package com.bdd.employee.facade;

import com.bdd.employee.events.EmployeeEvent;

import java.util.List;

public interface EventsSystem {
    List<EmployeeEvent> getEvents(long employeeId);
}
