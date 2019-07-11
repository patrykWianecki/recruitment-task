package com.app.controller;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.dto.EmployeeDto;
import com.app.service.EmployeeService;
import com.app.validator.EmployeeValidator;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeValidator employeeValidator;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(employeeValidator);
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> addEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
        return ResponseEntity.ok(employeeService.addEmployee(employeeDto));
    }

    @PutMapping
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
        return ResponseEntity.ok(employeeService.updateEmployee(employeeDto));
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDto> findOneEmployeeById(@PathVariable Integer employeeId) {
        return ResponseEntity.ok(employeeService.findEmployeeById(employeeId));
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<EmployeeDto> deleteEmployeeById(@PathVariable Integer employeeId) {
        return ResponseEntity.ok(employeeService.deleteEmployeeById(employeeId));
    }

    @GetMapping("/find")
    public ResponseEntity<Set<EmployeeDto>> findEmployeesByGivenParameters(@RequestParam String criteria) {
        return ResponseEntity.ok(employeeService.findByCriteria(criteria));
    }
}
