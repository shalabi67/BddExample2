package com.bdd.employee.events;

import com.bdd.employee.configurations.QueueConfiguration;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = QueueConfiguration.QUEUE_NAME)
public class EmployeeReceiver {
    private EmployeeEventRepository employeeEventRepository;

    public EmployeeReceiver(EmployeeEventRepository employeeEventRepository) {
        this.employeeEventRepository = employeeEventRepository;
    }

    @RabbitHandler
    public void receive(EmployeeEvent employeeEvent) {
        employeeEventRepository.save(employeeEvent);
    }
}
