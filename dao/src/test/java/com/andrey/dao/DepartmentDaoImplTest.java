package com.andrey.dao;

import com.andrey.model.Department;
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
public class DepartmentDaoImplTest extends Assert {

    @Autowired
    private DepartmentDao departmentDao;

    @Test
    public void testGetAllDepartments() throws Exception {
        List<Department> departments = departmentDao.getAllDepartments();
        assertNotNull(departments);
        assertFalse(departments.isEmpty());
    }

    @Test
    public void testAddDepartment() throws Exception {
        List<Department> departments = departmentDao.getAllDepartments();
        int sizeBefore = departments.size();
        Department department = new Department(null, "name");
        departmentDao.addDepartment(department);

        departments = departmentDao.getAllDepartments();
        int sizeAfter = departments.size();

        assertEquals(sizeBefore, sizeAfter-1);
    }

    @Test
    public void testGetDepartmentById() throws Exception {
        Department department = new Department(null, "new department");
        Long id = departmentDao.addDepartment(department);

        Department department1 = departmentDao.getDepartmentById(id);
        department.setId(id);
        assertEquals(department, department1);
    }

    @Test
    public void testEditDepartment() throws Exception {

        String dep_name = "new department";
        Department department = new Department(null, "department");
        Long id = departmentDao.addDepartment(department);
        department.setId(id);
        department.setDep_name(dep_name);

        departmentDao.editDepartment(department);
        department.setId(id);

        Department department1 = departmentDao.getDepartmentById(id);

        assertEquals(department1.getDep_name(), dep_name);
    }

    @Test
    public void testDeleteDepartment() throws Exception {
        List<Department> departments = departmentDao.getAllDepartments();
        int sizeBefore = departments.size();

        departmentDao.deleteDepartmentById(1l);

        departments = departmentDao.getAllDepartments();

        int sizeAfter = departments.size();

        assertEquals(sizeBefore, sizeAfter+1);
    }

    @Test
    public void testGetDepartmentByName() throws Exception {
        String dep_name = "department3";
        Department department = new Department(null, dep_name);
        Long id = departmentDao.addDepartment(department);
        department.setId(id);

        Department department1 = departmentDao.getDepartmentByName(dep_name);

        assertEquals(department, department1);
    }

    @Test
    public void testGetEmployeesByDepartment() throws Exception{

    }
}
