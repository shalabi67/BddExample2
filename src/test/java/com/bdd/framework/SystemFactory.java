package com.bdd.framework;

import com.bdd.employee.facade.DepartmentSystem;
import com.bdd.employee.facade.IntegrationSystemFactory;
import com.bdd.employee.facade.UnitSystemFactory;

public class SystemFactory {
    private static TestingTypeEnum testingType;
    static void setTestingType(TestingTypeEnum testingType) {
        SystemFactory.testingType = testingType;
    }

    public static TestingTypeEnum getTestingType() {
        return testingType;
    }
}
