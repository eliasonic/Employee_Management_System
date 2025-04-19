package com.example.employee_management_system.service;

import com.example.employee_management_system.domain.Employee;
import com.example.employee_management_system.repository.EmployeeRepository;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class that provides salary-related operations for {@code Employee} entities.
 * Supports applying raises, finding top-paid employees, and calculating salary average.
 *
 * @param <T> the type of the employee ID
 */
public class EmployeeSalaryService<T> {
    private final EmployeeRepository<T> repository;

    public EmployeeSalaryService(EmployeeRepository<T> repository) {
        this.repository = repository;
    }

    /**
     * Applies a percentage-based raise to employees meeting the minimum performance rating.
     *
     * @param percentage the raise percentage to apply
     * @param minRating the minimum performance rating required to receive the raise
     */
    public void applyRaise(double percentage, double minRating) {
        repository.findAll().stream()
                .filter(e -> e.getPerformanceRating() >= minRating)
                .forEach(e -> e.setSalary(e.getSalary() * (1 + percentage/100)));
    }

    /**
     * Retrieves the top-paid employees.
     *
     * @param count the number of top-paid employees to retrieve
     * @return a {@link List} of the top-paid employees
     */
    public List<Employee<T>> getTopPaid(int count) {
        return repository.findAll().stream()
                .sorted((e1, e2) -> Double.compare(e2.getSalary(), e1.getSalary()))
                .limit(count)
                .collect(Collectors.toList());
    }

    /**
     * Calculates the average salary for a given department.
     *
     * @param department the department to calculate the average salary for
     * @return the average salary for the specified department
     */
    public double getAverageSalary(String department) {
        return repository.findAll().stream()
                .filter(e -> e.getDepartment().equalsIgnoreCase(department))
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0.0);
    }
}
