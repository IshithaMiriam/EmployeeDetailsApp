package com.employee.EmployeeDetails.EmployeeHome;

import com.employee.EmployeeDetails.EmployeeDTO.APIResponse;
import com.employee.EmployeeDetails.EmployeeDTO.EmployeeRequestDTO;
import com.employee.EmployeeDetails.EmployeeDTO.EmployeeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Employee Management",
        description = "APIs for managing employees"
)
@RestController     //tells spring that this class handles http requests
@RequestMapping("/employees")     //all endpoints start with /employees
public class EmployeeController {

    private final EmployeeService service;   //dependency injection

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }                           //injects service

    @Operation(
            summary = "Create employees",
            description = "Creates one or more employees and calculates salary based on years of experience"
    )
    @PostMapping
    public ResponseEntity<APIResponse<List<EmployeeResponseDTO>>> createEmployee(@RequestBody List<@Valid EmployeeRequestDTO> employees) {
        return service.createEmployee(employees);
    }                          //@RequestBody converts into EmployeeRequestDTO from JSON & @Valid validates each object

    @Operation(
            summary = "Get all employees",
            description = "Returns employees with pagination and sorting support"
    )
    @GetMapping
    public ResponseEntity<APIResponse<List<EmployeeResponseDTO>>> getAllEmployees(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return service.getAllEmployees(page, size, sortBy, direction);
    }

    @Operation(
            summary = "Get employee by ID",
            description = "Fetches a single employee using the employee ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<EmployeeResponseDTO>> getEmployeeById(@PathVariable Long id) {
        return service.getEmployeeById(id);
    }                                //@PathVariable takes id from url

    @Operation(
            summary = "Update employee",
            description = "Updates employee fields using the employee ID"
    )
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<EmployeeResponseDTO>> updateEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeRequestDTO employee) {
        return service.updateEmployee(id, employee);
    }

    @Operation(
            summary = "Delete employee",
            description = "Deletes an employee using the employee ID"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteEmployee(@PathVariable Long id) {
        return service.deleteEmployee(id);
    }
}