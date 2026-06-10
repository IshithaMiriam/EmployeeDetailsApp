package com.employee.EmployeeDetails.EmployeeHome;

import com.employee.EmployeeDetails.EmployeeDTO.APIResponse;
import com.employee.EmployeeDetails.EmployeeDTO.EmployeeRequestDTO;
import com.employee.EmployeeDetails.EmployeeDTO.EmployeeResponseDTO;
import com.employee.EmployeeDetails.EmployeeDTO.MetaDataDTO;
import com.employee.EmployeeDetails.EmployeeException.DuplicateEmployeeException;
import com.employee.EmployeeDetails.EmployeeException.EmployeeAlreadyExistsException;
import com.employee.EmployeeDetails.EmployeeException.EmployeeNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;   //dependency injection
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);  //logger to print messages

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }     //injects repository


    public ResponseEntity<APIResponse<List<EmployeeResponseDTO>>> createEmployee(List<EmployeeRequestDTO> employees) {
        logger.info("Creating {} employees", employees.size());
        Set<Long> ids = new HashSet<>();
        List<EmployeeResponseDTO> result = new ArrayList<>();
        for (EmployeeRequestDTO dto : employees) {
            logger.info("Processing employee ID: {}", dto.getId());
            if (!ids.add(dto.getId())) {
                throw new DuplicateEmployeeException("Duplicate employee ID " + dto.getId() + " in request");
            }
            if (repository.existsById(dto.getId())) {
                throw new EmployeeAlreadyExistsException("Employee with ID " + dto.getId() + " already exists");
            }

            EmployeeEntity entity = mapToEntity(dto);   //convert dto to entity
            entity.setSalary(calculateSalary(dto.getYearsOfExperience()));
            EmployeeEntity saved = repository.save(entity);
            result.add(mapToResponse(saved));
        }
        APIResponse<List<EmployeeResponseDTO>> response =
                new APIResponse<>(
                        "success",

                        "Employees created successfully",
                        result,
                        null
                );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        logger.info("Fetching all employees");
        return ResponseEntity.ok(repository.findAll()
                .stream()   //convert entity to dto using stream
                .map(this::mapToResponse) //transform each item
                .collect(Collectors.toList()));  //converts stream back into lists
    }

    public ResponseEntity<APIResponse<List<EmployeeResponseDTO>>> getAllEmployees(int page, int size, String sortBy, String direction) {
        logger.info("Fetching employees with pagination");
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();   //ternary operator
        Pageable pageable = PageRequest.of(page - 1, size, sort);   //pageable object
        Page<EmployeeEntity> employeePage = repository.findAll(pageable);  //returns only requested pages
        List<EmployeeResponseDTO> result = employeePage
                .stream()  //convert page into stream
                .map(this::mapToResponse)  //convert entity to dto for every employee
                .toList();  //collect all converted dto into a list
        MetaDataDTO metadata = new MetaDataDTO(
                page,
                size,
                employeePage.getTotalElements()
        );
        APIResponse<List<EmployeeResponseDTO>> response =
                new APIResponse<>(
                        "success",
                        "Employees retrieved successfully",
                        result,
                        metadata
                );
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<APIResponse<EmployeeResponseDTO>> getEmployeeById(Long id) {
        logger.info("Fetching employee with ID: {}", id);
        EmployeeEntity employeeEntity = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + id)); //throws exception if id not found
        EmployeeResponseDTO employee = mapToResponse(employeeEntity);
        APIResponse<EmployeeResponseDTO> response =
                new APIResponse<>(
                        "success",
                        "Employee retrieved successfully",
                        employee,
                        null
                );
        return ResponseEntity.ok(response);   //convert entity to dto
    }

    public ResponseEntity<APIResponse<EmployeeResponseDTO>> updateEmployee(Long id, EmployeeRequestDTO dto) {
        logger.info("Updating employee with ID: {}", id);
        EmployeeEntity existing = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + id)); //throws exception if id not found
        if (dto.getName() != null) {
            existing.setName(dto.getName());
        }
        if (dto.getAge() != null) {
            existing.setAge(dto.getAge());
        }
        if (dto.getDob() != null) {
            existing.setDob(dto.getDob());
        }
        if (dto.getDepartment() != null) {
            existing.setDepartment(dto.getDepartment());
        }
        if (dto.getPassword() != null) {
            existing.setPassword(dto.getPassword());
        }
        if (dto.getYearsOfExperience() != 0) {
            existing.setYearsOfExperience(dto.getYearsOfExperience());
            existing.setSalary(calculateSalary(dto.getYearsOfExperience()));
        }
        EmployeeEntity saved = repository.save(existing);
        EmployeeResponseDTO responseDTO = mapToResponse(saved);
        APIResponse<EmployeeResponseDTO> response =
                new APIResponse<>(
                        "success",
                        "Employee updated successfully",
                        responseDTO,
                        null
                );
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<APIResponse<String>> deleteEmployee(Long id) {
        logger.info("Deleting employee with ID: {}", id);
        repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + id));
        repository.deleteById(id);
        APIResponse<String> response =
                new APIResponse<>(
                        "success",
                        "Employee deleted successfully",
                        "Employee deleted with ID: " + id,
                        null
                );
        return ResponseEntity.ok(response);
    }

    private EmployeeEntity mapToEntity(EmployeeRequestDTO dto) {     //converts request dto to entity
        EmployeeEntity e = new EmployeeEntity();  //create new entity object
        e.setId(dto.getId());
        e.setName(dto.getName());
        e.setAge(dto.getAge());
        e.setDob(dto.getDob());
        e.setDepartment(dto.getDepartment());
        e.setPassword(dto.getPassword());
        e.setYearsOfExperience(dto.getYearsOfExperience());
        return e;     //copy fields from dto to entity and return entity
    }

    private EmployeeResponseDTO mapToResponse(EmployeeEntity e) {   //converts entity to response dto
        EmployeeResponseDTO r = new EmployeeResponseDTO();  //create new response object
        r.setId(e.getId());
        r.setName(e.getName());
        r.setAge(e.getAge());
        r.setDob(e.getDob());
        r.setDepartment(e.getDepartment());
        r.setYearsOfExperience(e.getYearsOfExperience());
        r.setSalary(e.getSalary());
        return r;     //copy fields from entity to dto and return dto
    }

    private double calculateSalary(int years) {
        return 30000 + (years * 5000);
    }
}