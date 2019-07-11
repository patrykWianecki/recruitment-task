package com.app.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.app.exception.MyException;
import com.app.model.dto.EmployeeDto;

@Component
public class EmployeeValidator implements Validator {

    @Override
    public boolean supports(final Class<?> clazz) {
        return clazz.equals(EmployeeDto.class);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        try {
            EmployeeDto employeeDto = (EmployeeDto) target;

            String name = employeeDto.getName();
            String surname = employeeDto.getSurname();
            Integer grade = employeeDto.getGrade();
            Integer salary = employeeDto.getSalary();

            if (name == null || name.isBlank() || !isTextMadeOfLettersOnly(name)) {
                errors.rejectValue("name", "Name must contain at least one letter, but was " + isValueBlankOrNull(name));
            }
            if (surname == null || surname.isBlank() || !isTextMadeOfLettersOnly(surname)) {
                errors.rejectValue("surname", "Surname must contain at least one letter, but was " + isValueBlankOrNull(surname));
            }
            if (grade == null || grade < 0) {
                errors.rejectValue("name", "Grade must be a number greater or equal to 0, but was " + grade);
            }
            if (salary == null || salary <= 0) {
                errors.rejectValue("name", "Salary must be a number greater than 0, but was " + salary);
            }
        } catch (Exception e) {
            throw new MyException("Employee validation exception");
        }
    }

    private static String isValueBlankOrNull(String value) {
        return value == null ? null : (value.isBlank() ? "blank" : value);
    }

    private static boolean isTextMadeOfLettersOnly(String text) {
        return text.matches("([A-Za-z]+ )*[A-Za-z]+");
    }
}
