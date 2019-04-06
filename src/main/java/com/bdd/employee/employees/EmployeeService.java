package com.bdd.employee.employees;

import com.bdd.employee.events.EmployeeEvent;
import com.bdd.employee.events.EmployeeSender;
import com.bdd.employee.events.EventTypeEnum;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private EmployeeSender employeeSender;


    public EmployeeService(EmployeeRepository employeeRepository,  EmployeeSender employeeSender) {
        this.employeeRepository = employeeRepository;
        this.employeeSender = employeeSender;
    }

    public Result<Employee> addEmployee(Employee employee) {
        if(!isValidEmail(employee.getEmail())) {
            return new Result<>(ErrorEnum.InvalidEmail, "Invalid Email.");
        }
        if(!isValidDate(employee.getBirthday())) {
            return new Result<>(ErrorEnum.InvalidDate, "Invalid Date, expecting date yyyy-mm-dd.");
        }

        try {
            Result<Employee> result = new Result<>(employeeRepository.save(employee));

            sendMessage(EventTypeEnum.added, result.getResult());

            return result;
        }catch(DataIntegrityViolationException integrityException) {
            return new Result<>(ErrorEnum.EmailExists, "Email exists, try another email.");
        }catch(Exception e) {
            return new Result<>(ErrorEnum.Error, "Undefined Error, please try again.");
        }
    }

    private void sendMessage(EventTypeEnum eventType, Employee employee) {
        EmployeeEvent employeeEvent = new EmployeeEvent(eventType, employee);
        employeeSender.send(employeeEvent);
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        return pattern.matcher(email).matches();
    }

    private boolean isValidDate(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDate = format.parse(date);
            String newDate = format.format(birthDate);
            return date.equals(newDate);
        } catch (ParseException e) {
            return false;
        }
    }
}
