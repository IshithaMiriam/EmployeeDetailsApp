package com.employee.EmployeeDetails.EmployeeException;

public class EmployeeAlreadyExistsException extends RuntimeException {
    public EmployeeAlreadyExistsException(String message) {   //constructor
        super(message);         //goes to runtime exception
    }
}