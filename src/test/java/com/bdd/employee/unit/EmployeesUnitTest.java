package com.bdd.employee.unit;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "features",
        glue = "com.bdd.employee.unit",
        plugin = {
        "pretty",
        "html:target/reports",
        "json:target/reports/cucumber.json"
})
public class EmployeesUnitTest {
}
