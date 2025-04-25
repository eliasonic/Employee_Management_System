package com.example.employee_management_system.exceptions;

/**
 * This class represents an exception that is thrown when an invalid employee argument is provided.
 * It extends the {@link IllegalArgumentException} class.
 */
public class InvalidEmployeeArgumentException extends IllegalArgumentException {
    public InvalidEmployeeArgumentException(String message) {
        super(message);
    }
}
