package com.andrey.rest;

import com.andrey.model.Employee;
import com.andrey.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
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
@RequestMapping(value = "/employee")
public class EmployeeRestController {

    private static final Logger LOGGER = LogManager.getLogger(DepartmentRestController.class);

    @Autowired
    @Qualifier("employeeServiceImpl")
    EmployeeService employeeService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> getEmployees() {
        LOGGER.debug("get all employees");
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity(employees, HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id) {
        LOGGER.debug("get employee with id = " + id);

        try {
            Employee employee = employeeService.getEmployeeById(id);
            return new ResponseEntity(employee, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Employee not found with id=" + id + ", error: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEmployee(@PathVariable("id") long id ) {
        LOGGER.debug("delete employee with id = " + id);

        try {
            employeeService.deleteEmployeeById(id);
            return new ResponseEntity("Deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResponseEntity editEmployee(@RequestParam("id") Long id ,
                                       @RequestParam("fname") String fname,
                                       @RequestParam("lname") String lname,
                                       @RequestParam("mname") String mname,
                                       @RequestParam("birthday") @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate birthday,
                                       @RequestParam("salary") int salary,
                                       @RequestParam("dep_id") int dep_id) {
        LOGGER.debug("edit employee with id = " + id);



        Employee employee = new Employee(id, fname, lname, mname, birthday, salary, dep_id);

        try {
            employeeService.editEmployee(employee);
            return new ResponseEntity("", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addEmployee(@RequestParam(value = "id", required = false) Long id ,
                                      @RequestParam("fname") String fname,
                                      @RequestParam("lname") String lname,
                                      @RequestParam("mname") String mname,
                                      @RequestParam("birthday") @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate birthday,
                                      @RequestParam("salary") int salary,
                                      @RequestParam("dep_id") int dep_id) {
        LOGGER.debug("add employee");

        Employee employee = new Employee(null, fname, lname, mname, birthday, salary, dep_id);

        try {
            Long addId = employeeService.addEmployee(employee);
            return new ResponseEntity(addId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(value = "/date/{date}", method = RequestMethod.GET)
    public ResponseEntity getEmployeeByDate(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        LOGGER.debug("get employee by date = " + date.toString());

        List<Employee> employees = null;

        try {
            employees = employeeService.getEmployeeByDateOfBirthday(date);
            return new ResponseEntity(employees, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/dates/{from}/{to}", method = RequestMethod.GET)
    public ResponseEntity getEmployeeBetweenDates(@PathVariable("from")@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
                                                  @PathVariable("to") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {
        LOGGER.debug("get employee between " + from.toString() + " and " + to.toString());

        List<Employee> employees = null;

        try {
            employees = employeeService.getEmployeesBetweenDatesOfBirthday(from, to);
            return new ResponseEntity(employees, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}