package com.example.employee_management_system.exceptions;

/**
 * This class represents an exception that is thrown when an invalid name is provided.
 */
public class InvalidNameException extends InvalidEmployeeArgumentException {
    public InvalidNameException(String message) {
        super(message);
    }
}
