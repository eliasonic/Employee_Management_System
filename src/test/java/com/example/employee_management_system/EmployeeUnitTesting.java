package com.example.employee_management_system;

import com.example.employee_management_system.domain.Employee;
import com.example.employee_management_system.exceptions.InvalidSalaryException;
import com.example.employee_management_system.repository.EmployeeRepository;
import com.example.employee_management_system.service.EmployeeCRUDService;
import com.example.employee_management_system.service.EmployeeQueryService;
import com.example.employee_management_system.service.EmployeeSalaryService;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeUnitTesting {
    private EmployeeRepository<Integer> repository;
    private EmployeeCRUDService<Integer> crudService;
    private EmployeeQueryService<Integer> queryService;
    private EmployeeSalaryService<Integer> salaryService;

    @BeforeEach
    void setUp() {
        repository = new EmployeeRepository<>();
        crudService = new EmployeeCRUDService<>(repository);
        queryService = new EmployeeQueryService<>(repository);
        salaryService = new EmployeeSalaryService<>(repository);
    }

    @Test
    void testAddEmployee() {
        // Valid data returns true and adds to repository
        Employee<Integer> employee = new Employee<>(1, "Elias", "Backend", 5000.0, 8.5, 4, true);

        boolean result = crudService.addEmployee(employee);
        assertTrue(result);

        Employee<Integer> retrievedEmployee = repository.findById(1).orElse(null);
        assertNotNull(retrievedEmployee);
        assertEquals(employee, retrievedEmployee);

        // Invalid data throws Exception
        Employee<Integer> invalidEmployee = new Employee<>(2, "Patrick", "Backend", -5000.0, 8.5, 4, true);
        assertThrows(InvalidSalaryException.class, () -> crudService.addEmployee(invalidEmployee));
    }

    @Test
    void testFilterByDepartment() {
        Employee<Integer> employee1 = new Employee<>(1, "Elias", "Backend", 5000.0, 8.5, 4, true);
        Employee<Integer> employee2 = new Employee<>(2, "Patrick", "Frontend", 6000.0, 9.0, 3, true);

        crudService.addEmployee(employee1);
        crudService.addEmployee(employee2);

        List<Employee<Integer>> filteredEmployees = queryService.filterByDepartment("Backend");

        assertEquals(1, filteredEmployees.size());
        assertEquals(employee1, filteredEmployees.get(0));
    }

    @Test
    void testRemoveEmployee() {
        Employee<Integer> employee = new Employee<>(1, "Elias", "Backend", 5000.0, 8.5, 4, true);
        crudService.addEmployee(employee);

        Employee<Integer> retrievedEmployee = repository.findById(1).orElse(null);
        assertNotNull(retrievedEmployee);

        boolean result = crudService.removeEmployee(1);
        assertTrue(result);

        retrievedEmployee = repository.findById(1).orElse(null);
        assertNull(retrievedEmployee);
    }

    // DEBUG
    @Test
    void testApplySalaryRaise() {
        Employee<Integer> employee = new Employee<>(1, "Elias", "Backend", 5000.0, 8.5, 4, true);
        crudService.addEmployee(employee);

        salaryService.applyRaise(10.0, 7.5);

        Employee<Integer> employeeWithRaise = repository.findById(1).get();
        assertEquals(5500.0, employeeWithRaise.getSalary(), 0.001);
    }
}
