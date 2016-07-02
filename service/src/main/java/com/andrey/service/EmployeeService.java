package com.andrey.service;

import com.andrey.model.Employee;
import org.joda.time.LocalDate;

import java.util.List;

/**
 * Created by kobri on 30.06.2016.
 */
public interface EmployeeService {
    public List<Employee> getAllEmployees();

    public long addEmployee(Employee employee);

    public void deleteEmployeeById(long id);

    public void editEmployee(Employee employee);

    public Employee getEmployeeById(long id);

    public List<Employee> getEmployeeByDateOfBirthday(LocalDate date);

    public List<Employee> getEmployeesBetweenDatesOfBirthday(LocalDate from, LocalDate to);
}