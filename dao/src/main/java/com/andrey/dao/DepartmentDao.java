package com.andrey.dao;

import com.andrey.model.Department;
import com.andrey.model.Employee;

import java.util.List;

/**
 * Created by kobri on 30.06.2016.
 */
public interface DepartmentDao {

    public List<Department> getAllDepartments();

    public Long addDepartment(Department department);

    public void deleteDepartmentById(long id);

    public void editDepartment (Department department);

    public Department getDepartmentById(long id);

    public Department getDepartmentByName(String name);

    public List<Employee> getEmployeesBySelectedDepartment(long id);
}
