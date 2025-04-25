package com.example.employee_management_system.domain.comparators;

import com.example.employee_management_system.domain.Employee;
import java.util.Comparator;

/**
 * Comparator for sorting employees by salary in descending order.
 *
 * @param <T> the type of the employee ID
 */
public class EmployeeSalaryComparator<T> implements Comparator<Employee<T>> {
    @Override
    public int compare(Employee<T> emp1, Employee<T> emp2) {
        if (emp1 == null || emp2 == null) {
            throw new IllegalArgumentException("Employees cannot be null");
        }

        return Double.compare(
                emp2.getSalary() != null ? emp2.getSalary() : 0.0,
                emp1.getSalary() != null ? emp1.getSalary() : 0.0
        );
    }
}
