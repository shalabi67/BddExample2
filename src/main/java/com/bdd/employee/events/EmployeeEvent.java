package com.bdd.employee.events;


import com.bdd.employee.employees.Employee;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class EmployeeEvent implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long eventId;
    private EventTypeEnum eventType;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "uuid", nullable = true)
    private Employee employee;

    @Column(insertable = false, updatable = false, columnDefinition = "datetime NULL DEFAULT CURRENT_TIMESTAMP")
    private Date creationDate;

    public EmployeeEvent() {
    }

    public EmployeeEvent(EventTypeEnum eventType, Employee employee) {
        this.eventType = eventType;
        this.employee = employee;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
