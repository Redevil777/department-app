package com.andrey.service;

import com.andrey.dao.DepartmentDao;
import com.andrey.model.Department;
import com.andrey.model.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by kobri on 30.06.2016.
 */
@Component
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    private static final Logger LOGGER = LogManager.getLogger(DepartmentServiceImpl.class);


    @Override
    public List<Department> getAllDepartments() {
        LOGGER.debug("get departments");
        List<Department> departments = departmentDao.getAllDepartments();
        Assert.notEmpty(departments, "Empty list of departments");
        return departments;
    }

    @Override
    public Long addDepartment(Department department) {
        LOGGER.debug("add department " + department);
        Assert.notNull(department, "Object department can't be null");
        Assert.isNull(department.getId());
        Assert.notNull(department.getDep_name(), "Department name can't be empty");
        Department department1 = getDepartmentByName(department.getDep_name());
        if (department1 != null){
            throw new IllegalArgumentException("Department with this name already exist");
        }
        return departmentDao.addDepartment(department);
    }

    @Override
    public void deleteDepartmentById(long id) {
        LOGGER.debug("delete department with id = " + id);
        Assert.notNull(id, "Department id can't be null");

        Department department = null;
        try {
            department = departmentDao.getDepartmentById(id);
            Assert.notNull(department, "Department wasn't exist");
        } catch (EmptyResultDataAccessException e){
            throw new IllegalArgumentException("Department with this ID doesn't exist");
        }
        departmentDao.deleteDepartmentById(id);
    }

    @Override
    public void editDepartment(Department department) {
        LOGGER.debug("edit department " + department);
        Assert.notNull(department, "Department can't be null");
        Assert.notNull(department.getId(), "department id can't be null");
        Assert.notNull(department.getDep_name(), "department can't be null");

        Department department1 = null;
        try {
            department1 = departmentDao.getDepartmentById(department.getId());
            Assert.notNull(department1, "Can't found department with this id");
        } catch (EmptyResultDataAccessException e){
            throw new IllegalArgumentException("Can't edit department with wrong id");
        }
        departmentDao.editDepartment(department);
    }

    @Override
    public Department getDepartmentById(long id) {
        LOGGER.debug("get department with id = " + id);
        Department department = null;
        try{
            department = departmentDao.getDepartmentById(id);
            Assert.notNull(department, "Department can't be null");
        } catch (EmptyResultDataAccessException e){
            throw new IllegalArgumentException("Department with this id doesn't exist");
        }
        return department;
    }

    @Override
    public Department getDepartmentByName(String name) {
        LOGGER.debug("get department with name = " + name);
        Assert.hasText(name, "Department name can't be empty");
        Department department = null;
        try {
            department = departmentDao.getDepartmentByName(name);
            Assert.notNull(name, "Department name can't be null");
            Assert.hasText(department.getDep_name(), "Department can't be without NAME");
        } catch (EmptyResultDataAccessException e){

        }
        return department;
    }

    @Override
    public List<Employee> showEmployee(long id) {
        LOGGER.debug("show employees by department");

        List<Employee> employees = departmentDao.showEmployee(id);
        return employees;
    }
}