package com.example.newapp.service;

import com.example.newapp.entity.Employee;
import com.example.newapp.exception.ResourceNotFoundException;
import com.example.newapp.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");
        employee.setDepartment("IT");
        employee.setEmail("john@example.com");
    }

    @Test
    void testGetAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(Collections.singletonList(employee));

        List<Employee> result = employeeService.getAllEmployees();

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testGetAllEmployeesEmpty() {
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());

        List<Employee> result = employeeService.getAllEmployees();

        assertTrue(result.isEmpty());
        verify(employeeRepository).findAll();
    }

    @Test
    void testSaveEmployee() {
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee saved = employeeService.saveEmployee(employee);

        assertNotNull(saved);
        assertEquals("John Doe", saved.getName());
        verify(employeeRepository).save(employee);
    }

    @Test
    void testSaveNullEmployeeThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.saveEmployee(null);
        });
        assertEquals("Employee cannot be null", ex.getMessage());
    }

    @Test
    void testSaveEmployeeWithEmptyNameThrowsException() {
        Employee invalid = new Employee();
        invalid.setName("");
        invalid.setEmail("valid@email.com");
        invalid.setDepartment("HR");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.saveEmployee(invalid);
        });

        assertEquals("Employee name cannot be empty", ex.getMessage());
    }

    @Test
    void testSaveEmployeeWithEmptyEmailThrowsException() {
        Employee invalid = new Employee();
        invalid.setName("Name");
        invalid.setEmail("");
        invalid.setDepartment("Finance");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.saveEmployee(invalid);
        });

        assertEquals("Employee email cannot be empty", ex.getMessage());
    }

    @Test
    void testSaveEmployeeWithDuplicateEmailThrowsCustomException() {
        when(employeeRepository.save(any(Employee.class)))
                .thenThrow(new DataIntegrityViolationException("Duplicate"));

        EmployeeService.EmployeeAlreadyExistsException ex = assertThrows(
                EmployeeService.EmployeeAlreadyExistsException.class,
                () -> employeeService.saveEmployee(employee)
        );

        assertEquals("Employee with this email already exists", ex.getMessage());
    }

    @Test
    void testDeleteEmployee() {
        when(employeeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(employeeRepository).deleteById(1L);

        employeeService.deleteEmployee(1L);

        verify(employeeRepository).existsById(1L);
        verify(employeeRepository).deleteById(1L);
    }





    @Test
    void testDeleteEmployeeRepoThrowsRuntime() {
        when(employeeRepository.existsById(1L)).thenReturn(true);
        doThrow(new RuntimeException("DB error")).when(employeeRepository).deleteById(1L);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            employeeService.deleteEmployee(1L);
        });

        assertEquals("DB error", ex.getMessage());
    }

    @Test
    void testGetEmployeeById() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Employee found = employeeService.getEmployeeById(1L);

        assertNotNull(found);
        assertEquals("John Doe", found.getName());
        verify(employeeRepository).findById(1L);
    }

    @Test
    void testGetEmployeeByIdNotFound() {
        when(employeeRepository.findById(100L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.getEmployeeById(100L);
        });

        assertEquals("Employee with ID 100 not found", ex.getMessage());
    }

    @Test
    void testUpdateEmployee() {
        Employee updated = new Employee();
        updated.setName("Jane Doe");
        updated.setEmail("jane@example.com");
        updated.setDepartment("HR");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updated);

        Employee result = employeeService.updateEmployee(1L, updated);

        assertEquals("Jane Doe", result.getName());
        assertEquals("jane@example.com", result.getEmail());
        assertEquals("HR", result.getDepartment());
    }





    @Test
    void testUpdateEmployeeNotFound() {
        Employee update = new Employee();
        update.setName("New Name");

        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.updateEmployee(999L, update);
        });

        assertEquals("Employee with ID 999 not found", ex.getMessage());
    }

    @Test
    void testUpdateEmployeeWithNullDetails() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.updateEmployee(1L, null);
        });

        assertEquals("Employee details cannot be null", ex.getMessage());
    }



    @Test
    void testSaveEmployeeWithExistingId() {
        employee.setId(100L);
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee saved = employeeService.saveEmployee(employee);

        assertEquals(100L, saved.getId());
        verify(employeeRepository).save(employee);
    }

    @Test
    void testUpdateEmployeeDuplicateEmailThrowsException() {
        Employee update = new Employee();
        update.setName("New");
        update.setEmail("duplicate@email.com");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class)))
                .thenThrow(new DataIntegrityViolationException("Duplicate"));

        EmployeeService.EmployeeAlreadyExistsException ex = assertThrows(
                EmployeeService.EmployeeAlreadyExistsException.class,
                () -> employeeService.updateEmployee(1L, update)
        );

        assertEquals("Employee with this email already exists", ex.getMessage());
    }

    @Test
    void testUpdateEmployeeWithAllNullFields() {
        Employee update = new Employee(); // all fields null

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(i -> i.getArgument(0));

        Employee result = employeeService.updateEmployee(1L, update);

        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals("IT", result.getDepartment());
    }

    @Test
    void testUpdateEmployeeWithPartialFields() {
        Employee update = new Employee();
        update.setEmail("updated@example.com");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(i -> i.getArgument(0));

        Employee result = employeeService.updateEmployee(1L, update);

        assertEquals("updated@example.com", result.getEmail());
        assertEquals("John Doe", result.getName());
    }

    @Test
    void testDeleteEmployeeNotFound() {
        when(employeeRepository.existsById(99L)).thenReturn(false);

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.deleteEmployee(99L);
        });

        assertEquals("Employee with ID 99 not found", ex.getMessage());
        verify(employeeRepository).existsById(99L);
    }





}
