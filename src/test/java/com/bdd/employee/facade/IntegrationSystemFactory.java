package com.bdd.employee.facade;

import com.bdd.employee.EmployeeSystemFactory;
import com.bdd.framework.Properties;

public class IntegrationSystemFactory extends EmployeeSystemFactory {
    @Override
    public DepartmentSystem createDepartmentSystem(Properties properties) {
        return  new com.bdd.employee.itegration.department.DepartmentSystem(properties.getMockMvc());
    }

    @Override
    public EmployeeSystem createEmployeeSystem(Properties properties) {
        return new com.bdd.employee.itegration.employee.EmployeeSystem(properties.getMockMvc());
    }

    @Override
    public EventsSystem createEventsSystem(Properties properties) {
        return new com.bdd.employee.itegration.events.EventsSystem(properties.getMockMvc());
    }
}
