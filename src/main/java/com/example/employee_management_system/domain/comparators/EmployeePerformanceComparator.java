package com.example.employee_management_system.domain.comparators;

import com.example.employee_management_system.domain.Employee;
import java.util.Comparator;

/**
 * Comparator for sorting employees by performance rating in descending order.
 *
 * @param <T> the type of the employee ID
 */
public class EmployeePerformanceComparator<T> implements Comparator<Employee<T>> {
    @Override
    public int compare(Employee<T> emp1, Employee<T> emp2) {
        if (emp1 == null || emp2 == null) {
            throw new IllegalArgumentException("Employees cannot be null");
        }

        return Double.compare(
                emp2.getPerformanceRating() != null ? emp2.getPerformanceRating() : 0.0,
                emp1.getPerformanceRating() != null ? emp1.getPerformanceRating() : 0.0
        );
    }
}
