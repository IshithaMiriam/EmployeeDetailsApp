package com.employee.EmployeeDetails.EmployeeException;

public class DuplicateEmployeeException extends RuntimeException {
    public DuplicateEmployeeException(String message) {     //constructor
        super(message);       //goes to runtime exception
    }
}