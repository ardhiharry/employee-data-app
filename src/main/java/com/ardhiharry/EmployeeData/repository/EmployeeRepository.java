package com.ardhiharry.EmployeeData.repository;

import com.ardhiharry.EmployeeData.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  Optional<Employee> findByNik(long nik);
  boolean existsByNik(long nik);
  void deleteByNik(long nik);

  List<Employee> findByNikIn(List<Long> nikList);
  List<Employee> findByNikAndNamaLengkapContainingIgnoreCase(long nik, String namaLengkap);
  List<Employee> findByNamaLengkapContainingIgnoreCase(String namaLengkap);
}
