package com.employee.EmployeeDetails.EmployeeDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public class EmployeeResponseDTO {
    @Schema(description = "Employee ID", example = "1")
    private Long id;
    @Schema(description = "Employee name", example = "Ishitha")
    private String name;
    @Schema(description = "Employee age", example = "20")
    private int age;
    @Schema(description = "Date of birth", example = "2006-03-12")
    private LocalDate dob;
    @Schema(description = "Employee department", example = "IT")
    private String department;
    @Schema(description = "Years of experience", example = "2")
    private int yearsOfExperience;
    @Schema(
            description = "Calculated salary based on years of experience",
            example = "40000.0"
    )
    private double salary;
                      //what postman/frontend receives
    public EmployeeResponseDTO() {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public LocalDate getDob() {
        return dob;
    }
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public int getYearsOfExperience() {
        return yearsOfExperience;
    }
    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
    public double getSalary() {
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }
}