package com.app.controller;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.app.model.dto.EmployeeDto;
import com.app.service.EmployeeService;
import com.app.validator.EmployeeValidator;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.app.builder.EmployeeDataBuilder.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private EmployeeValidator employeeValidator;

    @Autowired
    private MockMvc mockMvc;

    private EmployeeDto employeeDto;

    @BeforeEach
    void setUp() {
        employeeDto = createEmployeeDto();
    }

    @Test
    void should_successfully_add_employee() throws Exception {
        // given
        when(employeeValidator.supports(any())).thenReturn(true);
        when(employeeService.addEmployee(any())).thenReturn(employeeDto);

        // when + then
        mockMvc.perform(post("/employees").content(toJson(employeeDto)).contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", equalTo(1)))
            .andExpect(jsonPath("$.name", equalTo("Jack")))
            .andExpect(jsonPath("$.surname", equalTo("Black")))
            .andExpect(jsonPath("$.grade", equalTo(5)))
            .andExpect(jsonPath("$.salary", equalTo(6000)))
            .andExpect(content().json("{id:1,name:Jack,surname:Black,grade:5,salary:6000}"));
    }

    @Test
    void should_successfully_update_employee() throws Exception {
        // given
        when(employeeValidator.supports(any())).thenReturn(true);
        when(employeeService.updateEmployee(any())).thenReturn(employeeDto);

        // when + then
        mockMvc.perform(put("/employees").content(toJson(employeeDto)).contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", equalTo(1)))
            .andExpect(jsonPath("$.name", equalTo("Jack")))
            .andExpect(jsonPath("$.surname", equalTo("Black")))
            .andExpect(jsonPath("$.grade", equalTo(5)))
            .andExpect(jsonPath("$.salary", equalTo(6000)))
            .andExpect(content().json("{id:1,name:Jack,surname:Black,grade:5,salary:6000}"));
    }

    @Test
    void should_successfully_get_employee_by_id() throws Exception {
        // given
        when(employeeService.findEmployeeById(anyInt())).thenReturn(employeeDto);

        // when + then
        mockMvc.perform(get("/employees/{employeeId}", 1).contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", equalTo(1)))
            .andExpect(jsonPath("$.name", equalTo("Jack")))
            .andExpect(jsonPath("$.surname", equalTo("Black")))
            .andExpect(jsonPath("$.grade", equalTo(5)))
            .andExpect(jsonPath("$.salary", equalTo(6000)))
            .andExpect(content().json("{id:1,name:Jack,surname:Black,grade:5,salary:6000}"));
    }

    @Test
    void should_successfully_delete_employee_by_id() throws Exception {
        // given
        when(employeeService.deleteEmployeeById(anyInt())).thenReturn(employeeDto);

        // when
        mockMvc.perform(delete("/employees/{employeeId}", 1).contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", equalTo(1)))
            .andExpect(jsonPath("$.name", equalTo("Jack")))
            .andExpect(jsonPath("$.surname", equalTo("Black")))
            .andExpect(jsonPath("$.grade", equalTo(5)))
            .andExpect(jsonPath("$.salary", equalTo(6000)))
            .andExpect(content().json("{id:1,name:Jack,surname:Black,grade:5,salary:6000}"));

        // then
    }

    @Test
    void should_successfully_find_employees_by_criteria() throws Exception {
        // given
        when(employeeService.findByCriteria(anyString())).thenReturn(Collections.singleton(employeeDto));

        // when + then
        mockMvc.perform(get("/employees/find?criteria={}", "Jack,5").contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id", equalTo(1)))
            .andExpect(jsonPath("$[0].name", equalTo("Jack")))
            .andExpect(jsonPath("$[0].surname", equalTo("Black")))
            .andExpect(jsonPath("$[0].grade", equalTo(5)))
            .andExpect(jsonPath("$[0].salary", equalTo(6000)))
            .andExpect(content().json("[{id:1,name:Jack,surname:Black,grade:5,salary:6000}]"));
    }

    private static String toJson(EmployeeDto employeeDto) {
        try {
            return new ObjectMapper().writeValueAsString(employeeDto);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
