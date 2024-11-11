package com.ardhiharry.EmployeeData.service;

import com.ardhiharry.EmployeeData.model.Employee;
import com.ardhiharry.EmployeeData.model.JenisKelamin;
import com.ardhiharry.EmployeeData.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class EmployeeService {
  private final EmployeeRepository employeeRepository;

  @Autowired
  public EmployeeService(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  public Employee createEmployee(Employee employee) {
    try {
      Optional<Employee> existingEmployee = employeeRepository.findByNik(employee.getNik());

      if (existingEmployee.isPresent()) {
        throw new IllegalStateException("Employee with NIK " + employee.getNik() + " already exists");
      }

      return employeeRepository.save(employee);
    } catch (IllegalStateException err) {
      throw new IllegalStateException("Failed to create employee: " + err.getMessage());
    } catch (Exception err) {
      throw new IllegalStateException("An unexpected error occurred while creating employee: " + err.getMessage());
    }
  }

  public List<Employee> getAllEmployees(Long nik, String namaLengkap) {
    try {
      List<Employee> result;

      if (nik != null && namaLengkap != null && !namaLengkap.isEmpty()) {
        // Jika nik dan namaLengkap tersedia
        result = employeeRepository.findByNikAndNamaLengkapContainingIgnoreCase(nik, namaLengkap);
      } else if (nik != null) {
        result = employeeRepository.findByNikIn(Collections.singletonList(nik));
      } else if (namaLengkap != null && !namaLengkap.isEmpty()) {
        result = employeeRepository.findByNamaLengkapContainingIgnoreCase(namaLengkap);
      } else {
        result = employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "createdAt"));
      }

      if (result.isEmpty()) {
        throw new IllegalStateException("No employees found");
      }

      return result;
    } catch (IllegalStateException err) {
      throw new IllegalStateException("Failed to retrieve employees: " + err.getMessage());
    } catch (Exception err) {
      throw new IllegalStateException("An unexpected error occurred while retrieving employees: " + err.getMessage());
    }
  }

  public Employee getEmployee(long nik) {
    try {
      return employeeRepository.findByNik(nik)
          .orElseThrow(() -> new IllegalStateException("Employee with NIK " + nik + " not found"));
    } catch (IllegalStateException err) {
      throw new IllegalStateException("Failed to find employee by NIK: " + err.getMessage());
    } catch (Exception err) {
      throw new IllegalStateException("An unexpected error occurred while retrieving employee: " + err.getMessage());
    }
  }

  public Employee updateEmployee(long nik, Map<String, Object> updates) {
    try {
      Employee employee = employeeRepository.findByNik(nik)
          .orElseThrow(() -> new IllegalStateException("Employee with NIK " + nik + " not found"));

      if (updates.containsKey("namaLengkap")) {
        employee.setNamaLengkap((String) updates.get("namaLengkap"));
      }

      if (updates.containsKey("jenisKelamin")) {
        employee.setJenisKelamin(updates.get("jenisKelamin").equals("Laki-Laki") ? JenisKelamin.LAKI_LAKI : JenisKelamin.PEREMPUAN);
      }

      if (updates.containsKey("tanggalLahir")) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date tanggalLahir = dateFormat.parse((String) updates.get("tanggalLahir"));
        employee.setTanggalLahir(tanggalLahir);
      }

      if (updates.containsKey("alamat")) {
        employee.setAlamat((String) updates.get("alamat"));
      }

      if (updates.containsKey("negara")) {
        employee.setNegara((String) updates.get("negara"));
      }


      return employeeRepository.save(employee);
    } catch (IllegalStateException err) {
      throw new IllegalStateException("Failed to update employee: " + err.getMessage());
    } catch (Exception err) {
      throw new IllegalStateException("An unexpected error occurred while updating employee: " + err.getMessage());
    }
  }

  @Transactional
  public void deleteEmployee(long nik) {
    try {
      if (!employeeRepository.existsByNik(nik)) {
        throw new IllegalStateException("Employee with NIK " + nik + " not found");
      }
      employeeRepository.deleteByNik(nik);
    } catch (IllegalStateException err) {
      throw new IllegalStateException("Failed to delete employee: " + err.getMessage());
    } catch (Exception err) {
      throw new IllegalStateException("An unexpected error occurred while deleting employee: " + err.getMessage());
    }
  }
}
