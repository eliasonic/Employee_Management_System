package com.example.employee_management_system.exceptions;

/**
 * This class represents an exception that is thrown when an employee is not found.
 * It extends the {@link IllegalStateException} class.
 */
public class EmployeeNotFoundException extends IllegalStateException {
    public EmployeeNotFoundException(Object employeeId) {
        super("Employee with ID " + employeeId + " not found");
    }
}
