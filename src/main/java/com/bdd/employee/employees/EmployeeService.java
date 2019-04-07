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
    static final String INVALID_EMAIL = "Invalid Email.";
    static final String INVALID_DATE = "Invalid Date, expecting date yyyy-mm-dd.";
    static final String EMAIL_EXISTS = "Email exists, try another email.";
    static final String UNDEFINED = "Undefined Error, please try again.";
    static final String EMPLOYEE_NOT_EXIST ="Employee not exists";
    private EmployeeRepository employeeRepository;
    private EmployeeSender employeeSender;


    public EmployeeService(EmployeeRepository employeeRepository,  EmployeeSender employeeSender) {
        this.employeeRepository = employeeRepository;
        this.employeeSender = employeeSender;
    }

    public Result<Employee> addEmployee(Employee employee) {
        return saveEmployee(EventTypeEnum.added, employee);
    }

    private Result<Employee> saveEmployee(EventTypeEnum eventType, Employee employee) {
        if(!isValidEmail(employee.getEmail())) {
            return new Result<>(ErrorEnum.InvalidEmail, INVALID_EMAIL);
        }
        if(!isValidDate(employee.getBirthday())) {
            return new Result<>(ErrorEnum.InvalidDate, INVALID_DATE);
        }

        try {
            Result<Employee> result = new Result<>(employeeRepository.save(employee));

            sendMessage(eventType, result.getResult());

            return result;
        }catch(DataIntegrityViolationException integrityException) {
            return new Result<>(ErrorEnum.EmailExists, EMAIL_EXISTS);
        }catch(Exception e) {
            return new Result<>(ErrorEnum.Error, UNDEFINED);
        }
    }

    public Result<Employee> changeEmployee(Long employeeId, Employee employee) {
        if(employeeRepository.findById(employeeId).isPresent()) {
            employee.setUuid(employeeId);
            return saveEmployee(EventTypeEnum.updated, employee);
        }

        return new Result<>(ErrorEnum.EmployeeNotExist, EMPLOYEE_NOT_EXIST);
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
