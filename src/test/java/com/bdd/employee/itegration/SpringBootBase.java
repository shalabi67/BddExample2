package com.bdd.employee.itegration;

import com.bdd.employee.EmployeeApplication;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;

import java.time.Duration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= EmployeeApplication.class)
@AutoConfigureMockMvc
//@ActiveProfiles("test")
@ContextConfiguration(classes= EmployeeApplication.class)
public class SpringBootBase { //extends JpaDataBase {


    @Autowired
    protected MockMvc mockMvc;



}
