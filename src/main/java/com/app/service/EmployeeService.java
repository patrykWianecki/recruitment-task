package com.app.service;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.exception.MyException;
import com.app.model.Employee;
import com.app.model.dto.EmployeeDto;
import com.app.model.dto.mapper.MyMapper;
import com.app.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

import static com.app.model.dto.mapper.MyMapper.*;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        try {
            if (employeeDto == null) {
                throw new MyException("Missing employee");
            }
            Employee employee = MyMapper.fromEmployeeDtoToEmployee(employeeDto);

            return fromEmployeeToEmployeeDto(employeeRepository.save(employee));
        } catch (Exception e) {
            throw new MyException("Failed to add employee");
        }
    }

    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
        try {
            if (employeeDto == null) {
                throw new MyException("Missing employee");
            }
            Employee employee = MyMapper.fromEmployeeDtoToEmployee(employeeDto);
            employee.setName(employeeDto.getName());
            employee.setSurname(employeeDto.getSurname());
            employee.setGrade(employeeDto.getGrade());
            employee.setSalary(employeeDto.getSalary());

            return fromEmployeeToEmployeeDto(employeeRepository.save(employee));
        } catch (Exception e) {
            throw new MyException("Failed to update employee");
        }
    }

    public EmployeeDto findEmployeeById(Integer employeeId) {
        try {
            if (employeeId == null) {
                throw new MyException("Missing employee id");
            }
            Employee employee = findEmployeeByIdWithExceptionHandling(employeeId);

            return fromEmployeeToEmployeeDto(employee);
        } catch (Exception e) {
            throw new MyException("Failed to find employee by id " + employeeId);
        }
    }

    public EmployeeDto deleteEmployeeById(Integer employeeId) {
        try {
            if (employeeId == null) {
                throw new MyException("Missing employee id");
            }
            Employee employee = findEmployeeByIdWithExceptionHandling(employeeId);

            employeeRepository.deleteById(employeeId);

            return fromEmployeeToEmployeeDto(employee);
        } catch (Exception e) {
            throw new MyException("Failed to delete employee by id " + employeeId);
        }
    }

    public Set<EmployeeDto> findByCriteria(String criteria) {
        if (criteria == null) {
            throw new MyException("Missing criteria");
        }
        if (!criteria.matches("(\\w+,)*\\w+")) {
            throw new MyException("Missing criteria in correct format");
        }

        String[] params = criteria.split(",");
        List<String> strParams = findStringParameters(params);
        List<Integer> intParams = findIntParameters(params);

        return Stream.concat(findEmployeesWithStringParams(strParams).stream(), findEmployeesWithIntegerParams(intParams).stream())
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Set<EmployeeDto> findEmployeesWithStringParams(List<String> params) {
        Set<EmployeeDto> employeesWithStringParam = new LinkedHashSet<>();

        params.forEach(param -> {
            employeeRepository.findByName(param)
                .stream()
                .map(MyMapper::fromEmployeeToEmployeeDto)
                .forEach(employeesWithStringParam::add);

            employeeRepository.findBySurname(param)
                .stream()
                .map(MyMapper::fromEmployeeToEmployeeDto)
                .forEach(employeesWithStringParam::add);
        });

        return employeesWithStringParam;
    }

    private Set<EmployeeDto> findEmployeesWithIntegerParams(List<Integer> params) {
        Set<EmployeeDto> employeesWithIntegerParam = new LinkedHashSet<>();

        params.forEach(param -> {
            employeeRepository.findById(param)
                .stream()
                .map(MyMapper::fromEmployeeToEmployeeDto)
                .forEach(employeesWithIntegerParam::add);

            employeeRepository.findByGrade(param)
                .stream()
                .map(MyMapper::fromEmployeeToEmployeeDto)
                .forEach(employeesWithIntegerParam::add);

            employeeRepository.findBySalary(param)
                .stream()
                .map(MyMapper::fromEmployeeToEmployeeDto)
                .forEach(employeesWithIntegerParam::add);
        });

        return employeesWithIntegerParam;
    }

    private static List<String> findStringParameters(String[] params) {
        return Arrays.stream(params)
            .filter(param -> param.matches("\\w+"))
            .collect(Collectors.toList());
    }

    private List<Integer> findIntParameters(String[] params) {
        return Arrays.stream(params)
            .filter(param -> param.matches("\\d+"))
            .map(Integer::parseInt)
            .collect(Collectors.toList());
    }

    private Employee findEmployeeByIdWithExceptionHandling(Integer employeeId) {
        return employeeRepository
            .findById(employeeId)
            .orElseThrow(() -> new MyException("Missing employee with id " + employeeId));
    }
}
