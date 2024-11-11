package com.ardhiharry.EmployeeData.controller;

import com.ardhiharry.EmployeeData.model.Employee;
import com.ardhiharry.EmployeeData.service.EmployeeService;
import com.ardhiharry.EmployeeData.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
  private final EmployeeService employeeService;

  @Autowired
  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @PostMapping
  public ResponseEntity<ApiResponse<Employee>> createEmployee(@RequestBody Employee employee) {
    Employee result = employeeService.createEmployee(employee);

    ApiResponse<Employee> response = ApiResponse.success(
        HttpStatus.CREATED.value(),
        "Employee created successfully",
        result
    );

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<Employee>>> getAllEmployees(
      @RequestParam(value = "nik", required = false) Long nik,
      @RequestParam(value = "namaLengkap", required = false) String namaLengkap) {
    List<Employee> result = employeeService.getAllEmployees(nik, namaLengkap);

    ApiResponse<List<Employee>> response = ApiResponse.success(
        HttpStatus.OK.value(),
        "Employees retrieved successfully",
        result
    );

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/{nik}")
  public ResponseEntity<ApiResponse<Employee>> getEmployeeByNik(@PathVariable long nik) {
    Employee result = employeeService.getEmployee(nik);

    ApiResponse<Employee> response = ApiResponse.success(
        HttpStatus.OK.value(),
        "Employee retrieved successfully",
        result
    );

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PatchMapping("/{nik}")
  public ResponseEntity<ApiResponse<Employee>> updateEmployee(
      @PathVariable long nik,
      @RequestBody Map<String, Object> updatedEmployee
      ) {
    Employee result = employeeService.updateEmployee(nik, updatedEmployee);

    ApiResponse<Employee> response = ApiResponse.success(
        HttpStatus.OK.value(),
        "Employee updated successfully",
        result
    );

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/{nik}")
  public ResponseEntity<ApiResponse<String>> deleteEmployee(@PathVariable long nik) {
    employeeService.deleteEmployee(nik);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
