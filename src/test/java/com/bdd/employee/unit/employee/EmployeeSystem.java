package com.bdd.employee.unit.employee;

import com.bdd.employee.employees.*;
import com.bdd.employee.events.EmployeeSender;
import com.bdd.employee.facade.EventMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

public class EmployeeSystem extends com.bdd.employee.facade.EmployeeSystem {
    @Mock
    private EmployeeRepository employeeRepository;

    private EmployeeService employeeService;

    private EmployeeController employeeController;

    private Map<String, Employee> emailEmployeeMap = new HashMap<>();

    private long autoGenerateId = 0;

    private Map<Long, Employee> employeeMap = new HashMap<>();

    private EventMocks eventMocks = new EventMocks();

    public EmployeeSystem() {
    }
    @Override
    public Result<Employee> createEmployee(Employee employee) {
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        Mockito.when(employeeRepository.save(any())).thenAnswer(employeeCreateAnswer);

        EmployeeService employeeService = new EmployeeService(employeeRepository, eventMocks.createEmploySenderMock());
        employeeController = new EmployeeController(employeeService);

        ResponseEntity responseEntity = employeeController.addEmployee(employee);

        return getResult(responseEntity);
    }

    @Override
    public Result<Employee> updateEmployee(Employee employee) {
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        Mockito.when(employeeRepository.findById(any())).thenAnswer(employeeFindByIdAnswer);
        Mockito.when(employeeRepository.save(any())).thenAnswer(employeeUpdateAnswer);
        EmployeeService employeeService = new EmployeeService(employeeRepository, eventMocks.createEmploySenderMock());
        employeeController = new EmployeeController(employeeService);

        ResponseEntity responseEntity = employeeController.changeEmployee(employee.getUuid(), employee);

        return getResult(responseEntity);

    }

    @Override
    public ResponseEntity deleteEmployee(Long employeeId) {
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        Mockito.doAnswer(employeeDeleteAnswer).when(employeeRepository).deleteById(any());
        EmployeeService employeeService = new EmployeeService(employeeRepository, eventMocks.createEmploySenderMock());
        employeeController = new EmployeeController(employeeService);

        return employeeController.deleteEmployee(employeeId);
    }

    @Override
    public Result<Employee> getEmployee(Long employeeId) {
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        Mockito.when(employeeRepository.findById(any())).thenAnswer(employeeFindByIdAnswer);
        EmployeeService employeeService = new EmployeeService(employeeRepository, eventMocks.createEmploySenderMock());
        employeeController = new EmployeeController(employeeService);

        ResponseEntity responseEntity = employeeController.getEmployee(employeeId);
        return getResult(responseEntity);
    }

    @Override
    public Long getAutoGeneratedId() {
        return autoGenerateId;
    }

    public EventMocks getEventMocks() {
        return eventMocks;
    }

    private Answer<Employee> employeeCreateAnswer = new Answer<Employee>() {
        @Override
        public Employee answer(InvocationOnMock invocationOnMock) throws Throwable {
            Employee employee = invocationOnMock.getArgument(0);
            if(emailEmployeeMap.containsKey(employee.getEmail())) {
                throw new DataIntegrityViolationException("email error");
            }
            Employee newEmployee = copyEmployee(employee);
            autoGenerateId++;
            newEmployee.setUuid(autoGenerateId);
            emailEmployeeMap.put(employee.getEmail(), newEmployee);
            employeeMap.put(employee.getUuid(), employee);
            return newEmployee;
        }
    };
    private Answer<Employee> employeeUpdateAnswer = new Answer<Employee>() {
        @Override
        public Employee answer(InvocationOnMock invocationOnMock) throws Throwable {
            Employee employee = invocationOnMock.getArgument(0);
            if(emailEmployeeMap.containsKey(employee.getEmail())) {
                throw new DataIntegrityViolationException("email error");
            }
            Employee newEmployee = copyEmployee(employee);
            newEmployee.setUuid(employee.getUuid());
            emailEmployeeMap.put(employee.getEmail(), newEmployee);
            employeeMap.put(employee.getUuid(), employee);
            return newEmployee;
        }
    };
    private Answer<Optional<Employee>> employeeFindByIdAnswer = new Answer<Optional<Employee>>() {
        @Override
        public Optional<Employee> answer(InvocationOnMock invocationOnMock) throws Throwable {
            Long employeeId = invocationOnMock.getArgument(0);
            if(employeeId == null)
                return Optional.empty();

            Employee employee = employeeMap.getOrDefault(employeeId, null);
            if(employee != null)
                return Optional.of(employee);
            return Optional.empty();
        }
    };
    private Answer employeeDeleteAnswer = new Answer() {
        @Override
        public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
            Long employeeId = invocationOnMock.getArgument(0);
            if(employeeId == null)
                return null;

            Employee employee = employeeMap.getOrDefault(employeeId, null);
            if(employee == null)
                throw new EmptyResultDataAccessException("employee not found", 0);
            return null;
        }
    };

    private Employee copyEmployee(Employee employee) {
        Employee newEmployee = new Employee();
        newEmployee.setBirthday(employee.getBirthday());
        newEmployee.setDepartment(employee.getDepartment());
        newEmployee.setEmail(employee.getEmail());
        newEmployee.setFirstName(employee.getFirstName());
        newEmployee.setLastName(employee.getLastName());

        return newEmployee;
    }

    private Result<Employee> getResult(ResponseEntity responseEntity) {
        if(responseEntity.getStatusCode() == HttpStatus.CREATED) {
            return new Result<>((Employee)responseEntity.getBody());
        } else if(responseEntity.getStatusCode() == HttpStatus.OK) {
            return new Result<>((Employee)responseEntity.getBody());
        }else if(responseEntity.getStatusCode() == HttpStatus.NOT_FOUND) {
            return new Result<>(ErrorEnum.EmployeeNotExist, (String)responseEntity.getBody());
        }else if(responseEntity.getStatusCode() == HttpStatus.CONFLICT) {
            return new Result<>(ErrorEnum.EmailExists, (String)responseEntity.getBody());
        }else if(responseEntity.getStatusCode() == HttpStatus.EXPECTATION_FAILED) {
            return new Result<>(ErrorEnum.InvalidDate, (String)responseEntity.getBody());
        }else if(responseEntity.getStatusCode() == HttpStatus.PRECONDITION_FAILED) {
            return new Result<>(ErrorEnum.InvalidEmail, (String)responseEntity.getBody());
        }else if(responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST) {
            return new Result<>(ErrorEnum.Error, (String)responseEntity.getBody());
        }

        throw new RuntimeException("Invalid result");


    }


}
