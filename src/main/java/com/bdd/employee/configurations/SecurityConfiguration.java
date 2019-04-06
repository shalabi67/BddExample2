package com.bdd.employee.configurations;

import com.bdd.employee.departments.DepartmentController;
import com.bdd.employee.employees.EmployeeController;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String SWAGGER_ROLE = "SWAGGER";
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String DEPARTMENT_ROLE = "DEPARTMENT";
    private static final String EMPLOYEE_ROLE = "EMPLOYEE";
    public static final String ADMIN_USER = "admin";
    public static final String ADMIN_PASSWORD = "admin";
    // Authentication : User --> Roles
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        //TODO: NoOpPasswordEncoder is not sucure and deprecated use BCryptPasswordEncoder, Pbkdf2PasswordEncoder, SCryptPasswordEncoder.
        //NoOpPasswordEncoder is used for example and it is not secure
        auth.inMemoryAuthentication().passwordEncoder(org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance())
                .withUser("department").password("department")
                .roles(DEPARTMENT_ROLE)

                .and()
                .withUser("employee").password("employee")
                .roles(EMPLOYEE_ROLE)

                .and().withUser(ADMIN_USER).password(ADMIN_PASSWORD)
                .roles(EMPLOYEE_ROLE, DEPARTMENT_ROLE, ADMIN_ROLE, SWAGGER_ROLE);
    }

    // Authorization : Role -> Access
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .authorizeRequests()

                .antMatchers(DepartmentController.URL + "/**")
                .hasRole(DEPARTMENT_ROLE)

                .antMatchers(EmployeeController.URL + "/**")
                .hasRole(EMPLOYEE_ROLE)

                .antMatchers("/**")
                .hasRole(ADMIN_ROLE)

                .and()
                .csrf().disable().headers().frameOptions().disable();
    }

}
