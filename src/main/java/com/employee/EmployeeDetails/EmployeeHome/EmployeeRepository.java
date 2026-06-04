package com.employee.EmployeeDetails.EmployeeHome;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository
        extends JpaRepository<EmployeeEntity, Long> {
}