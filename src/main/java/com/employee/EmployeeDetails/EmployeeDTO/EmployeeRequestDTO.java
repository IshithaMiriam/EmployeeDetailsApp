package com.employee.EmployeeDetails.EmployeeDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
       //represents what postman/frontend sends to api
public class EmployeeRequestDTO {
    @Schema(
            description = "Employee ID",
            example = "1"
    )
    @NotNull(message = "Employee ID is required")
    private Long id;

    @Schema(
            description = "Employee name",
            example = "Ishitha"
    )
    @NotBlank(message = "Name cannot be empty")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name should contain only letters and spaces")
    private String name;

    @Schema(
            description = "Employee age",
            example = "20"
    )
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 65, message = "Age cannot exceed 65")
    private Integer age;

    @Schema(
            description = "Date of birth",
            example = "2006-03-12"
    )
    @NotNull(message = "Date of Birth is required")
    private LocalDate dob;

    @Schema(
            description = "Department name",
            example = "IT"
    )
    @NotBlank(message = "Department cannot be empty")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Department should contain only letters and spaces")
    private String department;

    @NotBlank(message = "Password cannot be empty")
    private String password;


    @Schema(
            description = "Years of experience",
            example = "2"
    )
    @Min(value = 0, message = "Years of experience cannot be negative")
    @Max(value = 50, message = "Years of experience is invalid")
    private Integer yearsOfExperience;

    public EmployeeRequestDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Name cannot be empty") @Pattern(regexp = "^[A-Za-z ]+$", message = "Name should contain only letters and spaces") String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
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

    public @NotBlank(message = "Department cannot be empty") @Pattern(regexp = "^[A-Za-z ]+$", message = "Department should contain only letters and spaces") String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public @NotBlank(message = "Password cannot be empty") String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
}