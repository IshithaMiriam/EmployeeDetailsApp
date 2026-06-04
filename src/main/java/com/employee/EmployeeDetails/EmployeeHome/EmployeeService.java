package com.employee.EmployeeDetails.EmployeeHome;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public String createEmployee(List<EmployeeEntity> employees) {
        Set<Long> ids = new HashSet<>();
        for (EmployeeEntity employee : employees) {
            if (!ids.add(employee.getId())) {
                return "Duplicate ID "+employee.getId()+" found in request";
            }

            if (repository.existsById(employee.getId())) {
                return "Employee with ID "+employee.getId()+" already exists";
            }
        }
        repository.saveAll(employees);
        return "Employees created successfully";
    }

    public List<EmployeeEntity> getAllEmployees() {
        return repository.findAll();
    }

    public EmployeeEntity getEmployeeById(Long id) {
        EmployeeEntity employee = repository.findById(id).orElse(null);
        if (employee == null) {
            throw new RuntimeException("Employee not found");
        }
        return employee;
    }

    public EmployeeEntity updateEmployee(Long id, EmployeeEntity employee) {
        EmployeeEntity existing = repository.findById(id).orElse(null);
        if (existing == null) {
            throw new RuntimeException("Employee not found");
        }
        existing.setName(employee.getName());
        existing.setDepartment(employee.getDepartment());
        existing.setSalary(employee.getSalary());
        return repository.save(existing);
    }

    public String deleteEmployee(Long id) {
        if (!repository.existsById(id)) {
            return "Employee not found";
        }
        repository.deleteById(id);
        return "Employee deleted";
    }
}