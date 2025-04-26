package com.example.employee_management_system.service;

import com.example.employee_management_system.domain.Employee;
import com.example.employee_management_system.repository.EmployeeRepository;
import java.util.*;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Service class that provides query operations for {@code Employee} entities.
 * Supports filtering by various criteria.
 *
 * @param <T> the type of the employee ID
 */
public class EmployeeQueryService<T> {
    private final EmployeeRepository<T> repository;

    public EmployeeQueryService(EmployeeRepository<T> repository) {
        this.repository = repository;
    }

    /**
     * Filters employees by department (case-insensitive).
     *
     * @param department the department to filter by
     * @return a list of employees in the given department
     */
    public List<Employee<T>> filterByDepartment(String department) {
        return repository.findAll().stream()
                .filter(e -> e.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    /**
     * Filters employees by name (case-insensitive).
     *
     * @param name the name to filter by
     * @return a list of employees with the given name
     * @throws NoSuchElementException if no employees are found with the given name
     */
    public List<Employee<T>> filterByName(String name) {
        List <Employee<T>> employees = repository.findAll().stream()
                .filter(e -> e.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        if (employees.isEmpty())  {
            throw new NoSuchElementException("No employees found with the given name.");
        } else {
            return employees;
        }
    }

    /**
     * Filters employees by performance rating.
     *
     * @param minRating the minimum rating to filter by
     * @return a list of employees with a rating greater than or equal to the minimum rating
     */
    public List<Employee<T>> filterByRating(double minRating) {
        return repository.findAll().stream()
                .filter(e -> e.getPerformanceRating() >= minRating)
                .collect(Collectors.toList());
    }

    /**
     * Filters employees by salary range.
     *
     * @param min the minimum salary to filter by
     * @param max the maximum salary to filter by
     * @return a list of employees with a salary between the minimum and maximum values
     */
    public List<Employee<T>> filterBySalaryRange(double min, double max) {
        return repository.findAll().stream()
                .filter(e -> e.getSalary() >= min && e.getSalary() <= max)
                .collect(Collectors.toList());
    }

    public Iterator<Employee<T>> getEmployeeIterator() {
        return repository.getIterator();
    }
}
