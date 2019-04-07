package com.bdd.employee.events;


import com.bdd.employee.employees.Employee;
import com.bdd.employee.employees.JsonMapper;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class EmployeeEvent implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long eventId;
    private EventTypeEnum eventType;

    private Long employeeId;

    private String employeeJson;

    @Column(insertable = false, updatable = false, columnDefinition = "datetime NULL DEFAULT CURRENT_TIMESTAMP")
    private Date creationDate;

    public EmployeeEvent() {
    }

    public EmployeeEvent(EventTypeEnum eventType, Long employeeId, String employeeJson) {
        this.eventType = eventType;
        this.employeeId = employeeId;
        this.employeeJson = employeeJson;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public EventTypeEnum getEventType() {
        return eventType;
    }

    public void setEventType(EventTypeEnum eventType) {
        this.eventType = eventType;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeJson() {
        return employeeJson;
    }

    public void setEmployeeJson(String employeeJson) {
        this.employeeJson = employeeJson;
    }

    /*
    public Employee getEmployee() {
        JsonMapper<Employee> jsonMapper = new JsonMapper<>();
        Employee employee = jsonMapper.toObject(getEmployeeJson(), Employee.class);

        return employee;
    }

    public void setEmployee(Employee employee) {
        JsonMapper<Employee> jsonMapper = new JsonMapper<>();
        this.employeeId = employee.getUuid();
        this.employeeJson = jsonMapper.toString(employee);
    }
    */
}
