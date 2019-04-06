package com.bdd.employee.events;

import com.bdd.employee.employees.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeEventRepository extends JpaRepository<EmployeeEvent, Long> {
    List<EmployeeEvent> findAllByEmployeeOrderByCreationDate(Employee employee);
}
