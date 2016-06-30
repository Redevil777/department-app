package com.andrey.dao;

import com.andrey.model.Employee;
import org.joda.time.LocalDate;

import java.util.List;

/**
 * Created by kobri on 30.06.2016.
 */
public interface EmployeeDao {

    public List<Employee> getAllEmployees();

    public long addEmployee(Employee employee);

    public void deleteEmployee(long id);

    public void editEmployee(Employee employee);

    public Employee getEmployeeById(long id);

    public List<Employee> getEmployeeByDateOfBirthday(LocalDate date);

    public List<Employee> getEmployeeBetweenDatesOfBirthday(LocalDate from, LocalDate to);
}
