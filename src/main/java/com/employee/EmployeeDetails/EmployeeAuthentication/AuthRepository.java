package com.employee.EmployeeDetails.EmployeeAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<AuthEntity, String> {
}