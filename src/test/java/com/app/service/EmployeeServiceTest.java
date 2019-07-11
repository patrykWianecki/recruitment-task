package com.app.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.app.exception.MyException;
import com.app.model.Employee;
import com.app.model.dto.EmployeeDto;
import com.app.repository.EmployeeRepository;

import static com.app.builder.EmployeeDataBuilder.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {

    @TestConfiguration
    public static class TestConfig {

        @MockBean
        private EmployeeRepository employeeRepository;

        @Bean
        public EmployeeService employeeService() {
            return new EmployeeService(employeeRepository);
        }
    }

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    private Employee employee;
    private EmployeeDto employeeDto;

    @BeforeEach
    void setUp() {
        employee = createEmployees().get(0);
        employeeDto = createEmployeeDto();
    }

    @Test
    void should_successfully_add_new_employee() {
        // given
        when(employeeRepository.save(any())).thenReturn(employee);

        // when
        EmployeeDto actualEmployee = employeeService.addEmployee(employeeDto);

        // then
        assertNotNull(actualEmployee);
        assertEquals(Integer.valueOf(1), actualEmployee.getId());
        assertEquals("Jack", actualEmployee.getName());
        assertEquals("Black", actualEmployee.getSurname());
        assertEquals(Integer.valueOf(1), actualEmployee.getGrade());
        assertEquals(Integer.valueOf(6000), actualEmployee.getSalary());
    }

    @Test
    void should_throw_exception_during_adding_new_employee() {
        // given

        // when
        Throwable actualException = assertThrows(MyException.class, () -> employeeService.addEmployee(null));

        // then
        assertEquals("Failed to add employee", actualException.getMessage());
    }

    @Test
    void should_successfully_update_employee() {
        // given
        when(employeeRepository.save(any())).thenReturn(employee);

        // when
        EmployeeDto actualEmployee = employeeService.updateEmployee(employeeDto);

        // then
        assertNotNull(actualEmployee);
        assertEquals(Integer.valueOf(1), actualEmployee.getId());
        assertEquals("Jack", actualEmployee.getName());
        assertEquals("Black", actualEmployee.getSurname());
        assertEquals(Integer.valueOf(1), actualEmployee.getGrade());
        assertEquals(Integer.valueOf(6000), actualEmployee.getSalary());
    }

    @Test
    void should_throw_exception_during_updating_employee() {
        // given

        // when
        Throwable actualException = assertThrows(MyException.class, () -> employeeService.updateEmployee(null));

        // then
        assertEquals("Failed to update employee", actualException.getMessage());
    }

    @Test
    void should_successfully_find_employee_by_id() {
        // given
        when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));

        // when
        EmployeeDto actualEmployee = employeeService.findEmployeeById(employee.getId());

        // then
        assertNotNull(actualEmployee);
        assertEquals(Integer.valueOf(1), actualEmployee.getId());
        assertEquals("Jack", actualEmployee.getName());
        assertEquals("Black", actualEmployee.getSurname());
        assertEquals(Integer.valueOf(1), actualEmployee.getGrade());
        assertEquals(Integer.valueOf(6000), actualEmployee.getSalary());
    }

    @Test
    void should_throw_exception_during_finding_employee_by_id() {
        // given

        // when
        Throwable actualException = assertThrows(MyException.class, () -> employeeService.findEmployeeById(null));

        // then
        assertEquals("Failed to find employee by id " + null, actualException.getMessage());
    }

    @Test
    void should_successfully_delete_employee_by_id() {
        // given
        when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));

        // when
        EmployeeDto actualEmployee = employeeService.deleteEmployeeById(employee.getId());

        // then
        assertNotNull(actualEmployee);
        assertEquals(Integer.valueOf(1), actualEmployee.getId());
        assertEquals("Jack", actualEmployee.getName());
        assertEquals("Black", actualEmployee.getSurname());
        assertEquals(Integer.valueOf(1), actualEmployee.getGrade());
        assertEquals(Integer.valueOf(6000), actualEmployee.getSalary());
    }

    @Test
    void should_throw_exception_during_deleting_employee_by_id() {
        // given

        // when
        Throwable actualException = assertThrows(MyException.class, () -> employeeService.deleteEmployeeById(null));

        // then
        assertEquals("Failed to delete employee by id " + null, actualException.getMessage());
    }

    @Test
    void should_successfully_find_employees_by_given_criteria() {
        // given
        List<Employee> employees = createEmployees();
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employees.get(0)));
        when(employeeRepository.findByName(anyString())).thenReturn(List.of(employees.get(1)));
        when(employeeRepository.findBySurname(anyString())).thenReturn(List.of(employees.get(2)));
        when(employeeRepository.findByGrade(anyInt())).thenReturn(List.of(employees.get(3)));
        when(employeeRepository.findBySalary(anyInt())).thenReturn(List.of(employees.get(4)));

        // when
        Set<EmployeeDto> actualEmployees = employeeService.findByCriteria("5,Jack,White,5000,9");

        // then
        assertNotNull(actualEmployees);
        assertEquals(5, actualEmployees.size());
        assertEquals(1L, actualEmployees.stream().filter(employeeDto -> employeeDto.getId().equals(1)).count());
        assertEquals(1L, actualEmployees.stream().filter(employeeDto -> employeeDto.getName().equals("Jack")).count());
        assertEquals(1L, actualEmployees.stream().filter(employeeDto -> employeeDto.getSurname().equals("White")).count());
        assertEquals(1L, actualEmployees.stream().filter(employeeDto -> employeeDto.getGrade().equals(9)).count());
        assertEquals(1L, actualEmployees.stream().filter(employeeDto -> employeeDto.getSalary().equals(5000)).count());
    }

    @Test
    void should_throw_exception_when_criteria_is_missing() {
        // given

        // when
        assertThrows(MyException.class, () -> employeeService.findByCriteria(null));

        // then
    }

    @Test
    void should_throw_exception_when_criteria_has_wrong_syntax() {
        // given

        // when
        assertThrows(MyException.class, () -> employeeService.findByCriteria("Jack;Black"));

        // then
    }
}