package com.bdd.employee.events;

import com.bdd.employee.employees.Employee;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeSender {
    private RabbitTemplate template;
    private Queue queue;

    public EmployeeSender(RabbitTemplate template, Queue queue) {
        this.template = template;
        this.queue = queue;
    }

    public void send(EmployeeEvent employeeEvent) {
        this.template.convertAndSend(queue.getName(), employeeEvent);
    }
}
