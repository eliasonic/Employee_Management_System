package com.example.employee_management_system.domain;

import com.example.employee_management_system.exceptions.*;
import java.util.List;

/**
 * Validates employee data before creating or updating an employee.
 */
public class EmployeeValidator<T> {
    private static final List<String> departments = List.of(
            "Backend", "Frontend", "DevOps", "QA", "DataEng"
    );

    public void validate(Employee<T> employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }

        this.validateName(employee.getName());
        this.validateSalary(employee.getSalary());
        this.validateDepartment(employee.getDepartment());
    }

    /**
     * Validates the name of an employee
     *
     * @param name
     * @throws InvalidNameException if the name is invalid
     */
    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidNameException("Employee name cannot be empty");
        }

        if (!name.matches("^[\\p{L} .'-]+$")) {
            throw new InvalidNameException("Employee name contains invalid characters");
        }
    }

    /**
     * Validates the department of an employee
     *
     * @param department
     * @throws InvalidDepartmentException if the department is invalid
     */
    private void validateDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new InvalidDepartmentException("Department cannot be empty");
        }

        if (!departments.contains(department)) {
            throw new InvalidDepartmentException(department + " is not a valid department");
        }
    }

    /**
     * Validates the salary of an employee
     *
     * @param salary
     * @throws InvalidSalaryException if the salary is invalid
     */
    private void validateSalary(Double salary) {
        if (salary == null) {
            throw new InvalidSalaryException("Salary cannot be empty");
        }

        if (salary < 0) {
            throw new InvalidSalaryException("Salary cannot be negative");
        }
    }
}

