package com.andrey.service;

import com.andrey.dao.EmployeeDao;
import com.andrey.model.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by kobri on 30.06.2016.
 */
@Component
public class EmployeeServiceImpl implements EmployeeService {


    @Autowired
    private EmployeeDao employeeDao;

    private static final Logger LOGGER = LogManager.getLogger(EmployeeServiceImpl.class);

    @Override
    public List<Employee> getAllEmployees() {
        LOGGER.debug("get all employees");
        List<Employee> employees = employeeDao.getAllEmployees();
        Assert.notNull(employees, "Employees not found");
        return employees;
    }

    @Override
    public long addEmployee(Employee employee) {
        LOGGER.debug("add employee " + employee);
        Assert.notNull(employee, "Object employee can't be null");
        Assert.isNull(employee.getId());
        Assert.hasText(employee.getFname(), "first name can't be empty");
        Assert.hasText(employee.getLname(), "last name can't be empty");
        Assert.hasText(employee.getMname(), "middle name can't be empty");
        Assert.notNull(employee.getBirthday(), "birthday  can't be empty");
        Assert.notNull(employee.getSalary(), "salary can't be empty");
        Assert.notNull(employee.getDep_id(), "department id can't be empty");
        Assert.isTrue(employee.getSalary()>0, "Employee Salary can't be negative");

        return employeeDao.addEmployee(employee);
    }

    @Override
    public void deleteEmployee(long id) {
        LOGGER.debug("delete employee by id = " + id);
        Assert.notNull(id, "Employee id can't be null");
        Employee employee = null;

        try {
            employee = employeeDao.getEmployeeById(id);
        } catch (EmptyResultDataAccessException e){
            throw new IllegalArgumentException("Employee with this ID doesn't exist");
        }
        employeeDao.deleteEmployee(id);
    }

    @Override
    public void editEmployee(Employee employee) {
        LOGGER.debug("edit employee " + employee);
        Assert.notNull(employee, "employee can't be null");
        Assert.notNull(employee.getId(), "employee id can't be null");
        Assert.hasText(employee.getFname(), "first name can't be empty");
        Assert.hasText(employee.getLname(), "last name can't be empty");
        Assert.hasText(employee.getMname(), "middle name can't be empty");
        Assert.notNull(employee.getBirthday(), "birthday  can't be empty");
        Assert.notNull(employee.getSalary(), "salary can't be empty");
        Assert.notNull(employee.getDep_id(), "department id can't be empty");
        Assert.isTrue(employee.getSalary()>0, "Employee Salary can't be negative");

        Employee employee1 = null;

        try {
            employee1 = employeeDao.getEmployeeById(employee.getId());
            Assert.notNull(employee1, "Can't found employee with this id");
        } catch (EmptyResultDataAccessException e){
            throw new IllegalArgumentException("Can't edit employee with wrong id");
        }

        employeeDao.editEmployee(employee);
    }

    @Override
    public Employee getEmployeeById(long id) {
        LOGGER.debug("get employee by id = " + id);

        Employee employee = null;
        try {
            employee = employeeDao.getEmployeeById(id);
            Assert.notNull(employee, "Employee can't be null");
        } catch (EmptyResultDataAccessException e){
            throw new IllegalArgumentException("Employee with this id doesn't exist");
        }
        return employee;
    }

    @Override
    public List<Employee> getEmployeeByDateOfBirthday(LocalDate date) {
        LOGGER.debug("get employee by date = " + date);
        Assert.notNull(date, "Date of birthday can't be null");
        List<Employee> employees = null;

        try {
            employees = employeeDao.getEmployeeByDateOfBirthday(date);
            Assert.notNull(employees, "Employee can't be null");
            Assert.notEmpty(employees, "Employee can't be empty");
        }catch (EmptyResultDataAccessException ex) {
            throw new IllegalArgumentException("Employee with this date doesn't exist!");
        }
        return employees;
    }

    @Override
    public List<Employee> getEmployeeBetweenDatesOfBirthday(LocalDate from, LocalDate to) {
        LOGGER.debug("get employee between date from " + from + " to " + to);
        Assert.notNull(from, "Date from can't be null");
        Assert.notNull(to, "Date to can't be null");
        Assert.isTrue(from.isBefore(to), "Date from can't be earlier then date to");

        List<Employee> employees = null;

        try {
            employees = employeeDao.getEmployeeBetweenDatesOfBirthday(from, to);
            Assert.notNull(employees, "Employee can't be null");
            Assert.notEmpty(employees, "Employee can't be empty");
        } catch (EmptyResultDataAccessException ex) {
            throw new IllegalArgumentException("Employee with this date doesn't exist!");
        }
        return employees;
    }
}
