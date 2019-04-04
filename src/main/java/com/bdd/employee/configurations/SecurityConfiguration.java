package com.bdd.employee.configurations;

import com.bdd.employee.departments.DepartmentController;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String SWAGGER_ROLE = "SWAGGER";
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String DEPARTMENT_ROLE = "DEPARTMENT";
    // Authentication : User --> Roles
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        //TODO: NoOpPasswordEncoder is not sucure and deprecated use BCryptPasswordEncoder, Pbkdf2PasswordEncoder, SCryptPasswordEncoder.
        //NoOpPasswordEncoder is used for example and it is not secure
        auth.inMemoryAuthentication().passwordEncoder(org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance())
                .withUser("department").password("department")
                .roles(DEPARTMENT_ROLE)
                .and()
                .withUser("swagger").password("swagger")
                .roles(SWAGGER_ROLE, DEPARTMENT_ROLE)
                .and().withUser("admin").password("admin")
                .roles(DEPARTMENT_ROLE, ADMIN_ROLE, SWAGGER_ROLE);
    }

    // Authorization : Role -> Access
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(DepartmentController.URL + "/**")
                .hasRole(DEPARTMENT_ROLE)
                .antMatchers("/**")
                .hasRole(ADMIN_ROLE)
                .and()
                .csrf().disable().headers().frameOptions().disable();
    }

}
