package com.app.model.dto.mapper;

import com.app.model.Employee;
import com.app.model.dto.EmployeeDto;

public interface MyMapper {

    static Employee fromEmployeeDtoToEmployee(EmployeeDto employeeDto) {
        return employeeDto == null ? null : Employee
            .builder()
            .id(employeeDto.getId())
            .name(employeeDto.getName())
            .surname(employeeDto.getSurname())
            .grade(employeeDto.getGrade())
            .salary(employeeDto.getSalary())
            .build();
    }

    static EmployeeDto fromEmployeeToEmployeeDto(Employee employee) {
        return employee == null ? null : EmployeeDto
            .builder()
            .id(employee.getId())
            .name(employee.getName())
            .surname(employee.getSurname())
            .grade(employee.getGrade())
            .salary(employee.getSalary())
            .build();
    }
}
