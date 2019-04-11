package com.bdd.employee;

import com.bdd.employee.facade.*;
import com.bdd.framework.Properties;
import com.bdd.framework.SystemFactory;
import com.bdd.framework.TestingTypeEnum;

public abstract class EmployeeSystemFactory  {
    private static EmployeeSystemFactory systemFactory = null;
    public static EmployeeSystemFactory create() {
        if(systemFactory != null) {
            return systemFactory;
        }

        systemFactory = EmployeeSystemFactory.createSystemFactory(SystemFactory.getTestingType());
        return systemFactory;
    }
    public abstract DepartmentSystem createDepartmentSystem(Properties properties);
    public abstract EmployeeSystem createEmployeeSystem(Properties properties);
    public abstract EventsSystem createEventsSystem(Properties properties);

    private static EmployeeSystemFactory createSystemFactory(TestingTypeEnum testingTypeEnum) {
        switch (testingTypeEnum) {
            case IntegrationTesting: return new IntegrationSystemFactory();
            case UnitTesting: return new UnitSystemFactory();
            default: return null;
        }

    }
}
