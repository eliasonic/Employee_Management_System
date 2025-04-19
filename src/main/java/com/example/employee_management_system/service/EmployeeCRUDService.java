package com.example.employee_management_system.service;

import com.example.employee_management_system.domain.Employee;
import com.example.employee_management_system.repository.EmployeeRepository;
import java.util.List;
import java.util.function.Consumer;

/**
 * Service class that provides CRUD operations for {@code Employee} entities.
 * Delegates persistence operations to the underlying {@link EmployeeRepository}.
 *
 * @param <T> the type of the employee ID
 */
public class EmployeeCRUDService<T> {
    private final EmployeeRepository<T> repository;

    public EmployeeCRUDService(EmployeeRepository<T> repository) {
        this.repository = repository;
    }

    public boolean addEmployee(Employee<T> employee) {
        return repository.save(employee);
    }

    public boolean removeEmployee(T employeeId) {
        return repository.delete(employeeId);
    }

    /**
     * Updates an existing employee using the provided updater function.
     *
     * @param employeeId the ID of the employee to be updated
     * @param updater a function that takes an {@code Employee} and updates its properties
     * @return {@code true} if the employee was successfully updated, {@code false} otherwise
     */
    public boolean updateEmployee(T employeeId, Consumer<Employee<T>> updater) {
        Employee<T> employee = repository.findById(employeeId).orElse(null);
        if (employee == null) return false;
        updater.accept(employee);
        return repository.save(employee);
    }

    public List<Employee<T>> getAllEmployees() {
        return repository.findAll();
    }
}
