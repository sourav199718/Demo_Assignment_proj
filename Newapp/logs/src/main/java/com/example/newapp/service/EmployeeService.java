package com.example.newapp.service;

import com.example.newapp.entity.Employee;
import com.example.newapp.exception.ResourceNotFoundException;
import com.example.newapp.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    private static final String EMPLOYEE_NOT_FOUND_MSG = "Employee with ID %d not found";

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        logger.info("Fetching all employees");
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Employee ID must not be null");
        }

        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(EMPLOYEE_NOT_FOUND_MSG, id)));
    }

    public Employee saveEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }

        validateEmployee(employee);

        try {
            return employeeRepository.save(employee);
        } catch (DataIntegrityViolationException e) {
            throw new EmployeeAlreadyExistsException("Employee with this email already exists", e);
        }
    }
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        if (employeeDetails == null) {
            throw new IllegalArgumentException("Employee details cannot be null");
        }

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(EMPLOYEE_NOT_FOUND_MSG, id)));

        if (StringUtils.hasText(employeeDetails.getName())) {
            existingEmployee.setName(employeeDetails.getName());
        }

        if (StringUtils.hasText(employeeDetails.getEmail())) {
            existingEmployee.setEmail(employeeDetails.getEmail());
        }

        if (StringUtils.hasText(employeeDetails.getDepartment())) {
            existingEmployee.setDepartment(employeeDetails.getDepartment());
        }

        try {
            return employeeRepository.save(existingEmployee);
        } catch (DataIntegrityViolationException e) {
            throw new EmployeeAlreadyExistsException("Employee with this email already exists", e);
        }
    }

    public void deleteEmployee(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Employee ID must not be null");
        }

        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format(EMPLOYEE_NOT_FOUND_MSG, id));
        }

        employeeRepository.deleteById(id);
        logger.info("Deleted employee with ID {}", id);
    }

    // Optional: Search by Email
    public Optional<Employee> getEmployeeByEmail(String email) {
        if (!StringUtils.hasText(email)) {
            throw new IllegalArgumentException("Email must not be empty");
        }
        return employeeRepository.findAll().stream()
                .filter(emp -> email.equalsIgnoreCase(emp.getEmail()))
                .findFirst();
    }

    // Optional: Filter by Department
    public List<Employee> getEmployeesByDepartment(String department) {
        if (!StringUtils.hasText(department)) {
            throw new IllegalArgumentException("Department must not be empty");
        }
        return employeeRepository.findAll().stream()
                .filter(emp -> department.equalsIgnoreCase(emp.getDepartment()))
                .toList();
    }

    private void validateEmployee(Employee employee) {
        if (!StringUtils.hasText(employee.getName())) {
            throw new IllegalArgumentException("Employee name cannot be empty");
        }
        if (!StringUtils.hasText(employee.getEmail())) {
            throw new IllegalArgumentException("Employee email cannot be empty");
        }
        if (!StringUtils.hasText(employee.getDepartment())) {
            throw new IllegalArgumentException("Employee department cannot be empty");
        }
    }

    public static class EmployeeAlreadyExistsException extends RuntimeException {
        public EmployeeAlreadyExistsException(String message) {
            super(message);
        }

        public EmployeeAlreadyExistsException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}