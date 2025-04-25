package com.example.employee_management_system.repository;

import com.example.employee_management_system.domain.Employee;
import java.util.*;

/**
 * Generic repository class for storing and managing {@code Employee} entities.
 * This class provides basic CRUD operations using an in-memory {@code HashMap} storage.
 *
 * @param <T> the type of the employee ID
 */
public class EmployeeRepository<T> {
    private final Map<T, Employee<T>> database = new HashMap<>();

    /**
     * Saves an employee to the repository.
     *
     * @param employee the employee to be saved
     * @return {@code true} if the employee was successfully saved, {@code false} otherwise
     */
    public boolean save(Employee<T> employee) {
        if (employee == null) {
            return false;
        }
        database.put(employee.getEmployeeId(), employee);
        return true;
    }

    public boolean delete(T employeeId) {
        return database.remove(employeeId) != null;
    }

    public Optional<Employee<T>> findById(T employeeId) {
        return Optional.ofNullable(database.get(employeeId));
    }

    public List<Employee<T>> findAll() {
        return new ArrayList<>(database.values());
    }

    public Iterator<Employee<T>> getIterator() {
        return database.values().iterator();
    }
}