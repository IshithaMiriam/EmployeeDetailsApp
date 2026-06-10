package com.employee.EmployeeDetails.EmployeeException;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(String message) {   //constructor
        super(message);           //goes to runtime exception
    }
}