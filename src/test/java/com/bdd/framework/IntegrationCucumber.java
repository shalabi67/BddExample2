package com.bdd.framework;

import cucumber.api.junit.Cucumber;
import org.junit.runners.model.InitializationError;

public class IntegrationCucumber extends Cucumber {
    public IntegrationCucumber(Class clazz) throws InitializationError {
        super(clazz);
        //SystemFactory.create(TestingTypeEnum.IntegrationTesting);
        SystemFactory.setTestingType(TestingTypeEnum.IntegrationTesting);
    }
}
