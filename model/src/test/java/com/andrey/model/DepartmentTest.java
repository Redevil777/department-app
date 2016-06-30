package com.andrey.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kobri on 30.06.2016.
 */
public class DepartmentTest extends Assert {
    private final List<Integer> TEST_DATA = new ArrayList<Integer>();

    private Department department;

    @Before
    public void setUpTestData() {
        TEST_DATA.add(10);
        department = new Department();
    }

    @Test
    public void testGetDepartmentId() throws Exception {
        department.setId(1l);
        assertEquals(1l, department.getId().longValue());
    }

    @Test
    public void testGetDepartmentName() throws Exception {
        department.setDep_name("name");
        assertEquals("name", department.getDep_name());
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