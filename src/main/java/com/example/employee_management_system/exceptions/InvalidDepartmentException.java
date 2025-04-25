package com.example.employee_management_system.exceptions;

/**
 * This class represents an exception that is thrown when an invalid department is encountered.
 */
public class InvalidDepartmentException extends InvalidEmployeeArgumentException {
    public InvalidDepartmentException(String message) {
        super(message);
    }
}
