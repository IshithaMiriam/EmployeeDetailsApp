package com.employee.EmployeeDetails.EmployeeHome;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController   //tells spring that this class handles HTTP requests
@RequestMapping("/employees")  //all endpoints in the class has /employees
public class EmployeeController {

    private final EmployeeService service;  //dependency injection

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }                                //constructor injection, injects service

    @PostMapping
    public String createEmployee(@RequestBody List<EmployeeEntity> employees) {
        return service.createEmployee(employees);
    }     //@RequestBody converts the JSON from postman and converts into an object of entity

    @GetMapping
    public List<EmployeeEntity> getAllEmployees() {
        return service.getAllEmployees();
    }   //returns as a list

    @GetMapping("/{id}")
    public EmployeeEntity getEmployeeById(@PathVariable Long id) {
        return service.getEmployeeById(id);
    }   //@PathVariable pulls the {id} placeholder from the url and gives it to method

    @PutMapping("/{id}")
    public EmployeeEntity updateEmployee(@PathVariable Long id, @RequestBody EmployeeEntity employee) {
        return service.updateEmployee(id, employee);
    }


    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        return service.deleteEmployee(id);
    }
}