package com.bdd.employee.itegration;

import org.junit.ClassRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;

//@DataJpaTest
@ContextConfiguration(initializers = {JpaDataBase.Initializer.class})
public class JpaDataBase {


    @ClassRule
    public static MySQLContainer mySQLContainer =
            (MySQLContainer) new MySQLContainer("mysql/mysql-server")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");
    // .withStartupTimeout(Duration.ofSeconds(600));

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + mySQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + mySQLContainer.getUsername(),
                    "spring.datasource.password=" + mySQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
