package com.bdd.employee.facade;

import com.bdd.employee.EmployeeSystemFactory;
import com.bdd.framework.Properties;

public class UnitSystemFactory extends EmployeeSystemFactory {
    @Override
    public DepartmentSystem createDepartmentSystem(Properties properties) {
        return new com.bdd.employee.unit.department.DepartmentSystem();
    }

    @Override
    public EmployeeSystem createEmployeeSystem(Properties properties) {
        return new com.bdd.employee.unit.employee.EmployeeSystem();
    }

    @Override
    public EventsSystem createEventsSystem(Properties properties) {
        return new com.bdd.employee.unit.events.EventsSystem();
    }
}
