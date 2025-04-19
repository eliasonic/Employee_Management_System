package com.example.employee_management_system.service;

import com.example.employee_management_system.domain.Employee;
import com.example.employee_management_system.repository.EmployeeRepository;
import java.util.*;
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

    public List<Employee<T>> filterByDepartment(String department) {
        return repository.findAll().stream()
                .filter(e -> e.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    public List<Employee<T>> filterByName(String name) {
        return repository.findAll().stream()
                .filter(e -> e.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Employee<T>> filterByRating(double minRating) {
        return repository.findAll().stream()
                .filter(e -> e.getPerformanceRating() >= minRating)
                .collect(Collectors.toList());
    }

    public List<Employee<T>> filterBySalaryRange(double min, double max) {
        return repository.findAll().stream()
                .filter(e -> e.getSalary() >= min && e.getSalary() <= max)
                .collect(Collectors.toList());
    }

    public Iterator<Employee<T>> getEmployeeIterator() {
        return repository.getIterator();
    }
}
