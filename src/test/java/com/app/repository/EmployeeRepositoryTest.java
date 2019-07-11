package com.app.repository;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.app.exception.MyException;
import com.app.model.Employee;

import static com.app.builder.EmployeeDataBuilder.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = createEmployees().get(0);
    }

    @Test
    void should_successfully_save_employee_in_repository() {
        // given
        testEntityManager.merge(employee);
        testEntityManager.flush();

        // when
        Employee actualEmployee = employeeRepository.findById(1).orElseThrow(() -> new MyException("Missing employee with id 1"));

        // then
        assertNotNull(actualEmployee);
        assertEquals(Integer.valueOf(1), actualEmployee.getId());
        assertEquals("Jack", actualEmployee.getName());
        assertEquals("Black", actualEmployee.getSurname());
        assertEquals(Integer.valueOf(1), actualEmployee.getGrade());
        assertEquals(Integer.valueOf(6000), actualEmployee.getSalary());
    }

    @Test
    void should_successfully_find_employees_by_name() {
        // given
        testEntityManager.merge(employee);
        testEntityManager.flush();

        // when
        List<Employee> actualEmployees = employeeRepository.findByName("Jack");

        // then
        assertNotNull(actualEmployees);
        assertEquals(1, actualEmployees.size());
        Employee actualEmployee = actualEmployees.get(0);
        assertEquals(Integer.valueOf(3), actualEmployee.getId());
        assertEquals("Jack", actualEmployee.getName());
        assertEquals("Black", actualEmployee.getSurname());
        assertEquals(Integer.valueOf(1), actualEmployee.getGrade());
        assertEquals(Integer.valueOf(6000), actualEmployee.getSalary());
    }

    @Test
    void should_successfully_find_employees_by_surname() {
        // given
        testEntityManager.merge(employee);
        testEntityManager.flush();

        // when
        List<Employee> actualEmployees = employeeRepository.findBySurname("Black");

        // then
        assertNotNull(actualEmployees);
        assertEquals(1, actualEmployees.size());
        Employee actualEmployee = actualEmployees.get(0);
        assertEquals(Integer.valueOf(2), actualEmployee.getId());
        assertEquals("Jack", actualEmployee.getName());
        assertEquals("Black", actualEmployee.getSurname());
        assertEquals(Integer.valueOf(1), actualEmployee.getGrade());
        assertEquals(Integer.valueOf(6000), actualEmployee.getSalary());
    }

    @Test
    void should_successfully_find_employees_by_grade() {
        // given
        testEntityManager.merge(employee);
        testEntityManager.flush();

        // when
        List<Employee> actualEmployees = employeeRepository.findByGrade(1);

        // then
        assertNotNull(actualEmployees);
        assertEquals(1, actualEmployees.size());
        Employee actualEmployee = actualEmployees.get(0);
        assertEquals(Integer.valueOf(4), actualEmployee.getId());
        assertEquals("Jack", actualEmployee.getName());
        assertEquals("Black", actualEmployee.getSurname());
        assertEquals(Integer.valueOf(1), actualEmployee.getGrade());
        assertEquals(Integer.valueOf(6000), actualEmployee.getSalary());
    }

    @Test
    void should_successfully_find_employees_by_salary() {
        // given
        testEntityManager.merge(employee);
        testEntityManager.flush();

        // when
        List<Employee> actualEmployees = employeeRepository.findBySalary(6000);

        // then
        assertNotNull(actualEmployees);
        assertEquals(1, actualEmployees.size());
        Employee actualEmployee = actualEmployees.get(0);
        assertEquals(Integer.valueOf(5), actualEmployee.getId());
        assertEquals("Jack", actualEmployee.getName());
        assertEquals("Black", actualEmployee.getSurname());
        assertEquals(Integer.valueOf(1), actualEmployee.getGrade());
        assertEquals(Integer.valueOf(6000), actualEmployee.getSalary());
    }
}
