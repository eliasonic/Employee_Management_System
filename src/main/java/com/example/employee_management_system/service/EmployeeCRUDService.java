package com.example.employee_management_system.service;

import com.example.employee_management_system.domain.Employee;
import com.example.employee_management_system.domain.EmployeeValidator;
import com.example.employee_management_system.exceptions.EmployeeNotFoundException;
import com.example.employee_management_system.exceptions.InvalidEmployeeArgumentException;
import com.example.employee_management_system.repository.EmployeeRepository;
import java.util.List;
import java.util.Optional;

/**
 * Service class that provides CRUD operations for {@code Employee} entities.
 * Delegates persistence operations to the underlying {@link EmployeeRepository}.
 *
 * @param <T> the type of the employee ID
 */
public class EmployeeCRUDService<T> {
    private final EmployeeRepository<T> repository;
    private final EmployeeValidator<T> validator;

    public EmployeeCRUDService(EmployeeRepository<T> repository) {
        this.repository = repository;
        this.validator = new EmployeeValidator<>();
    }

    /**
     * Adds a new employee to the repository.
     *
     * @param employee the employee to be added
     * @return {@code true} if the employee was successfully added, {@code false} otherwise
     * @throws InvalidEmployeeArgumentException if the employee data is invalid
     */
    public boolean addEmployee(Employee<T> employee) {
        validator.validate(employee);
        return repository.save(employee);
    }

    /**
     * Removes an employee from the repository.
     *
     * @param employeeId the ID of the employee to be removed
     * @return {@code true} if the employee was successfully removed, {@code false} otherwise
     * @throws EmployeeNotFoundException if the employee with the given ID is not found
     */
    public boolean removeEmployee(T employeeId) {
        Employee<T> employee = repository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        return repository.delete(employeeId);
    }

    /**
     * Updates an existing employee with the provided new data.
     *
     * @param employeeId the ID of the employee to be updated
     * @param updatedEmployee the employee object containing the new data
     * @return {@code true} if the employee was successfully updated, {@code false} otherwise
     * @throws EmployeeNotFoundException if the employee with the given ID is not found
     * @throws InvalidEmployeeArgumentException if the updated employee data is invalid
     */
    public boolean updateEmployee(T employeeId, Employee<T> updatedEmployee) {
        Employee<T> employee = repository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        updatedEmployee.setEmployeeId(employeeId);
        validator.validate(updatedEmployee);
        return repository.save(updatedEmployee);
    }

    public List<Employee<T>> getAllEmployees() {
        return repository.findAll();
    }
}
