package com.example.employee_management_system.exceptions;

/**
 * This class represents an exception that is thrown when an invalid salary is provided.
 */
public class InvalidSalaryException extends InvalidEmployeeArgumentException {
    public InvalidSalaryException(String message) {
        super(message);
    }
}
