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
        return Double.compare(emp2.getSalary(), emp1.getSalary());
    }
}
