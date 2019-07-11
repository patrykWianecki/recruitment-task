package com.app.builder;

import java.util.List;

import com.app.model.Employee;
import com.app.model.dto.EmployeeDto;

public class EmployeeDataBuilder {

    public static EmployeeDto createEmployeeDto() {
        return EmployeeDto.builder()
            .id(1)
            .name("Jack")
            .surname("Black")
            .grade(5)
            .salary(6000)
            .build();
    }

    public static List<Employee> createEmployees() {
        return List.of(
            Employee.builder()
                .id(1)
                .name("Jack")
                .surname("Black")
                .grade(1)
                .salary(6000)
                .build(),
            Employee.builder()
                .id(2)
                .name("Walter")
                .surname("White")
                .grade(2)
                .salary(2000)
                .build(),
            Employee.builder()
                .id(3)
                .name("Mark")
                .surname("Dark")
                .grade(3)
                .salary(5000)
                .build(),
            Employee.builder()
                .id(4)
                .name("Brad")
                .surname("Tip")
                .grade(9)
                .salary(6000)
                .build(),
            Employee.builder()
                .id(5)
                .name("John")
                .surname("Blue")
                .grade(5)
                .salary(9000)
                .build()
        );
    }
}
