package com.bdd.employee.facade;

import com.bdd.employee.configurations.QueueConfiguration;
import com.bdd.employee.departments.Department;
import com.bdd.employee.employees.Employee;
import com.bdd.employee.employees.JsonMapper;
import com.bdd.employee.events.*;
import org.jetbrains.annotations.NotNull;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class EventMocks {
    static long count = 0;
    private Map<Long, List<EmployeeEvent>> employeeEventMap = new HashMap<>(); //EmployeeId TO List<EmployeeEvents>
    public  List<EmployeeEvent> getEmployeeEvents(Long employeeId) {
        return employeeEventMap.getOrDefault(employeeId, new ArrayList<>());
    }

    public void initializeEmployeeEvents(long employeeId) {
        if(employeeId <= 0)
            return;

        addEventToMap(employeeId, createEvent(EventTypeEnum.added, employeeId));
        addEventToMap(employeeId, createEvent(EventTypeEnum.updated, employeeId));
        addEventToMap(employeeId, createEvent(EventTypeEnum.updated, employeeId));
        addEventToMap(employeeId, createEvent(EventTypeEnum.updated, employeeId));
        addEventToMap(employeeId, createEvent(EventTypeEnum.deleted, employeeId));
    }

    public EmployeeSender createEmploySenderMock() {
        Queue queue = Mockito.mock(Queue.class);
        Mockito.when(queue.getName()).thenReturn(QueueConfiguration.QUEUE_NAME);

        RabbitTemplate template = Mockito.mock(RabbitTemplate.class);
        Mockito.doAnswer(senderAnswer).when(template).convertAndSend(anyString(), any(EmployeeEvent.class));

        return new EmployeeSender(template, queue);
    }

    private Answer<Void> senderAnswer = new Answer<Void>() {
        @Override
        public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
            EmployeeEvent employeeEvent = invocationOnMock.getArgument(1);
            EmployeeReceiver employeeReceiver = createEmployeeReceiverMock();
            employeeReceiver.receive(employeeEvent);

            return null;
        }
    };

    private EmployeeReceiver createEmployeeReceiverMock() {
        EmployeeEventRepository employeeEventRepository = Mockito.mock(EmployeeEventRepository.class);
        Mockito.when(employeeEventRepository.save(any())).thenAnswer(receiveAnswer);

        return new EmployeeReceiver(employeeEventRepository);
    }
    private void addEventToMap(long employeeId, EmployeeEvent employeeEvent) {
        List<EmployeeEvent> employeeEvents = employeeEventMap.getOrDefault(employeeId, new ArrayList<>());
        employeeEvents.add(employeeEvent);
        employeeEventMap.put(employeeId, employeeEvents);
    }
    private Answer<EmployeeEvent> receiveAnswer = new Answer<EmployeeEvent>() {
        @Override
        public EmployeeEvent answer(InvocationOnMock invocationOnMock) throws Throwable {
            EmployeeEvent employeeEvent = invocationOnMock.getArgument(0);

            employeeEvent.setCreationDate(new Date());
            Long employeeId = employeeEvent.getEmployeeId();

            addEventToMap(employeeId, employeeEvent);

            return employeeEvent;
        }
    };

    private EmployeeEvent createEvent(EventTypeEnum type, long employeeId) {
        Employee employee = getEmployee(employeeId);
        JsonMapper<Employee> jsonMapper = new JsonMapper<>();


        EmployeeEvent employeeEvent = new EmployeeEvent();
        employeeEvent.setCreationDate(new Date());
        employeeEvent.setEventType(type);
        employeeEvent.setEmployeeId(employeeId);
        employeeEvent.setEmployeeJson(jsonMapper.toString(employee));
        employeeEvent.setEventId(++count);

        return employeeEvent;
    }

    @NotNull
    public Employee getEmployee(long employeeId) {
        Department department = new Department();
        department.setDepartmentId(employeeId);
        department.setDepartmentName("Name" + employeeId);

        Employee employee = new Employee();
        employee.setUuid(employeeId);
        employee.setDepartment(department);
        employee.setBirthday("1999-01-02");
        employee.setEmail("email" + employeeId + "@gmil.com");
        employee.setFirstName("firstName" + employeeId);
        employee.setLastName("lastName" + employeeId);
        return employee;
    }

}
