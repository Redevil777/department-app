package com.andrey.rest;

import com.andrey.model.Department;
import com.andrey.model.Employee;
import com.andrey.service.DepartmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by kobri on 30.06.2016.
 */
@Component
@RestController
@RequestMapping(value = "/department")
public class DepartmentRestController {

    private static final Logger LOGGER = LogManager.getLogger(DepartmentRestController.class);

    @Autowired
    @Qualifier("departmentServiceImpl")
    DepartmentService departmentService;

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Department> getDepartmentById(@PathVariable("id") long id){
        LOGGER.debug("get department by id " + id);
        try {
            Department department = departmentService.getDepartmentById(id);
            return new ResponseEntity(department, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Department not found with id=" + id + ", error: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<Department> getDepartmentByName(@PathVariable("name") String name){
        LOGGER.debug("get department by name " + name);
        try {
            Department department = departmentService.getDepartmentByName(name);
            return new ResponseEntity(department, HttpStatus.OK);
        } catch (Exception e ){
            return new ResponseEntity("Department not found with name=" + name + ", error:" + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value = "/all ",method = RequestMethod.GET)
    public ResponseEntity<List<Department>> getDepartments() {
        LOGGER.debug("get all departments");
        List<Department> departments = departmentService.getAllDepartments();
        return new ResponseEntity(departments, HttpStatus.OK);
    }


    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResponseEntity addDepartment(@RequestParam(value = "id", required = false) Long id ,
                                        @RequestParam("dep_name") String dep_name){
        LOGGER.debug("add department with name " + dep_name);

        Department department = new Department(null, dep_name);

        try {
            Long addId = departmentService.addDepartment(department);
            return new ResponseEntity(addId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),  HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResponseEntity editDepartment(@RequestParam("id") Long id,
                                         @RequestParam("name") String name){
        LOGGER.debug("edit department with id = " + id);

        Department department = new Department(id, name);

        try {
            departmentService.editDepartment(department);
            return new ResponseEntity("", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Check input data!", HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteDepartment(@PathVariable("id") Long id){
        LOGGER.debug("delete department with id = " + id);

        try {
            departmentService.deleteDepartmentById(id);
            return new ResponseEntity("", HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/employees/{dep_id}", method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> showEmployees(@PathVariable("dep_id") long dep_id){
        List<Employee> employees = departmentService.showEmployee(dep_id);
        return new ResponseEntity(employees, HttpStatus.OK);
    }
}