package com.andrey.model;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kobri on 30.06.2016.
 */
public class EmployeeTest extends Assert {
    private final List<Integer> TEST_DATA = new ArrayList<Integer>();

    private Employee employee;

    @Before
    public void setUpTestData() {
        TEST_DATA.add(10);
        employee = new Employee();
    }

    @Test
    public void testGetEmployeeId() throws Exception {
        employee.setId(1L);
        assertEquals(1, employee.getId().longValue());
    }

    @Test
    public void testGetEmployeeFname() throws Exception {
        employee.setFname("fname");
        assertEquals("fname", employee.getFname());
    }

    @Test
    public void testGetEmployeeLname() throws Exception {
        employee.setLname("lname");
        assertEquals("lname", employee.getLname());
    }

    @Test
    public void testGetEmployeeMname() throws Exception {
        employee.setMname("mname");
        assertEquals("mname", employee.getMname());
    }

    @Test
    public void testGetEmployeeBirthday() throws Exception {
        employee.setBirthday(new LocalDate(1999-11-11));
        assertEquals(new LocalDate(1999-11-11), employee.getBirthday());
    }

    @Test
    public void testGetEmployeeSalary() throws Exception {
        employee.setSalary(322L);
        assertEquals(322L, employee.getSalary());
    }

    @Test
    public void testGetEmployeeDepId() throws Exception {
        employee.setDep_id(322L);
        assertEquals(322L, employee.getDep_id());
    }

    @Test(expected = NullPointerException.class)
    public void testForNPEAnnotation() {
        throw new NullPointerException();
    }

    @After
    public void cleanUpTestData() {
        TEST_DATA.clear();
    }
}