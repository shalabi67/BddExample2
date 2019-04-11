package com.bdd.employee.EmployeeTestingSteps;

import com.bdd.framework.IntegrationCucumber;
import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(IntegrationCucumber.class)
@CucumberOptions(features = "features",
        glue = {"com.bdd.framework", "com.bdd.employee.EmployeeTestingSteps"},
        plugin = {
        "pretty",
        "html:target/reports",
        "json:target/reports/cucumber.json"
})
public class EmployeesIT {
}
