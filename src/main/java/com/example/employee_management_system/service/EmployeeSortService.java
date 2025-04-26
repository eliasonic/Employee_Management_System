package com.example.employee_management_system.service;

import com.example.employee_management_system.domain.Employee;
import com.example.employee_management_system.domain.comparators.EmployeePerformanceComparator;
import com.example.employee_management_system.domain.comparators.EmployeeSalaryComparator;
import com.example.employee_management_system.repository.EmployeeRepository;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class that provides sorting operations for {@code Employee} entities.
 * Supports sorting by experience, salary, and performance rating.
 *
 * @param <T> the type of the employee ID
 */
public class EmployeeSortService<T> {
    private final EmployeeRepository<T> repository;

    public EmployeeSortService(EmployeeRepository<T> repository) {
        this.repository = repository;
    }

    /**
     * Returns all employees sorted by years of experience in descending order.
     * Uses the natural ordering defined in {@link Employee#compareTo}.
     *
     * @return a new {@link List} of employees sorted by experience
     */
    public List<Employee<T>> sortByExperience() {
        return repository.findAll().stream()
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Returns all employees sorted by salary in descending order.
     * Uses {@link EmployeeSalaryComparator} for sorting.
     *
     * @return a new {@link List} of employees sorted by salary
     */
    public List<Employee<T>> sortBySalary() {
        return repository.findAll().stream()
                .sorted(new EmployeeSalaryComparator<>())
                .collect(Collectors.toList());
    }

    /**
     * Returns all employees sorted by performance rating in descending order.
     * Uses {@link EmployeePerformanceComparator} for sorting.
     *
     * @return a new {@link List} of employees sorted by performance rating
     * @throws IllegalStateException if no employees are found to sort
     */
    public List<Employee<T>> sortByPerformance() {
        List<Employee<T>> employees = repository.findAll();
        if (employees.isEmpty()) {
            throw new IllegalStateException("No employees found to sort.");
        }
        return employees.stream()
                .sorted(new EmployeePerformanceComparator<>())
                .collect(Collectors.toList());
    }
}
