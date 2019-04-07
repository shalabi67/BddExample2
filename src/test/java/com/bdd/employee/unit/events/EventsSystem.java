package com.bdd.employee.unit.events;

import com.bdd.employee.employees.Employee;
import com.bdd.employee.events.EmployeeEvent;
import com.bdd.employee.events.EmployeeEventRepository;
import com.bdd.employee.events.EventController;
import com.bdd.employee.facade.EventMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

public class EventsSystem implements com.bdd.employee.facade.EventsSystem {
    @Mock
    private EmployeeEventRepository employeeEventRepository;

    private EventController eventController;

    EventMocks eventMocks;

    public EventsSystem() {
        employeeEventRepository = Mockito.mock(EmployeeEventRepository.class);
        Mockito.when(employeeEventRepository.findAllByEmployeeIdOrderByCreationDate(any())).thenAnswer(getEventAnswer);

        eventController = new EventController(employeeEventRepository);

        eventMocks = new EventMocks();
    }

    @Override
    public List<EmployeeEvent> getEvents(long employeeId) {
        return eventController.getEvents(employeeId);
    }

    private Answer<List<EmployeeEvent>> getEventAnswer = new Answer<List<EmployeeEvent>>() {
        @Override
        public List<EmployeeEvent> answer(InvocationOnMock invocationOnMock) throws Throwable {
            Long employeeId = invocationOnMock.getArgument(0);
            eventMocks.initializeEmployeeEvents(employeeId);

            return eventMocks.getEmployeeEvents(employeeId);
        }
    };
}
