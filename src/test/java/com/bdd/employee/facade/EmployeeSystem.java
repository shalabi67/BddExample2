package com.bdd.employee.facade;

import com.bdd.employee.departments.Department;
import com.bdd.employee.employees.Employee;
import com.bdd.employee.employees.Result;

public abstract class EmployeeSystem {
    public abstract Result<Employee> createEmployee(Employee employee);
    public abstract Long getAutoGeneratedId();

    public Employee getEmployee(String firstname, String lastname, String email, String birthday, String departmentId) {
        Employee employee = new Employee();
        employee.setLastName(lastname);
        employee.setFirstName(firstname);
        employee.setEmail(email);
        employee.setBirthday(birthday);
        Department exitngDepartment = new Department();
        exitngDepartment.setDepartmentId(Long.parseLong(departmentId));
        employee.setDepartment(exitngDepartment);

        return  employee;
    }


}