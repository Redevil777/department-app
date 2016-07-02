package com.andrey.dao;

import com.andrey.model.Employee;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kobri on 30.06.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-dao-test.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional
public class EmployeeDaoImplTest extends Assert {

    private Employee createEmployee() {
        Employee employee = new Employee(null, "Andrey", "Vaschuk","Igorevich", new LocalDate("1994-04-18"), 322l, 1l);
        return employee;
    }


    @Autowired
    private EmployeeDao employeeDao;

    @Test
    public void testGetAllEmployees() throws Exception {
        List<Employee> employees = employeeDao.getAllEmployees();
        assertNotNull(employees);
        assertFalse(employees.isEmpty());
    }

    @Test
    public void testAddEmployee() throws Exception {
        List<Employee> employees = employeeDao.getAllEmployees();
        int sizeBefore = employees.size();

        Employee employee = createEmployee();
        employeeDao.addEmployee(employee);
        employees = employeeDao.getAllEmployees();

        int sizeAfter = employees.size();
        assertEquals(sizeBefore, sizeAfter-1);
    }

    @Test
    public void testGetEmployeeById() throws Exception {
        Employee employee = createEmployee();
        Long id = employeeDao.addEmployee(employee);
        employee.setId(id);
        Employee employee1 = employeeDao.getEmployeeById(id);
        assertEquals(employee.getId(), employee1.getId());
        assertEquals(employee.getFname(), employee1.getFname());
        assertEquals(employee.getLname(), employee1.getLname());
        assertEquals(employee.getMname(), employee1.getMname());
        assertEquals(employee.getBirthday(), employee1.getBirthday());
        assertEquals(employee.getSalary(), employee1.getSalary());
        assertEquals(employee.getDep_id(), employee1.getDep_id());

    }

    @Test
    public void testEditEmployee() throws Exception {
        String fname = "fname";
        String lname = "lname";
        String mname = "mname";
        LocalDate birthday = new LocalDate("1988-05-03");
        Long salary = 123L;
        Long dep_id = 2L;

        Employee employee = createEmployee();
        Long id = employeeDao.addEmployee(employee);

        employee.setId(id);
        employee.setFname(fname);
        employee.setLname(lname);
        employee.setMname(mname);
        employee.setBirthday(birthday);
        employee.setSalary(salary);
        employee.setDep_id(dep_id);

        employeeDao.editEmployee(employee);

        Employee employee1 = employeeDao.getEmployeeById(id);

        assertEquals(employee, employee1);
    }

    @Test
    public void testDeleteEmployeeById() throws Exception {
        List<Employee> employees = employeeDao.getAllEmployees();

        int sizeBefore = employees.size();

        employeeDao.deleteEmployeeById(1l);

        employees = employeeDao.getAllEmployees();

        int sizeAfter = employees.size();

        assertEquals(sizeBefore, sizeAfter+1);
    }


    @Test
    public void testGetEmployeeByDateOfBirthday() throws Exception{

        LocalDate date = new LocalDate("1985-02-05");

        Employee employee = createEmployee();

        employee.setBirthday(date);

        employeeDao.addEmployee(employee);

        List<Employee> employee1 = employeeDao.getEmployeeByDateOfBirthday(date);

        assertEquals(2, employee1.size());
    }

    @Test
    public void testGetEmployeesBetweenDatesOfBirthday() throws Exception{
        LocalDate from = new LocalDate("1987-12-18");
        LocalDate to = new LocalDate("1994-03-02");

        List<Employee> employees = employeeDao.getEmployeesBetweenDatesOfBirthday(from, to);

        assertEquals(3, employees.size());
    }
}