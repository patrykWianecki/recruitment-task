package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByName(String name);

    List<Employee> findBySurname(String surname);

    List<Employee> findByGrade(Integer grade);

    List<Employee> findBySalary(Integer salary);
}
