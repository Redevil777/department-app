package com.andrey.web;

import com.andrey.model.Department;
import com.andrey.model.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by kobri on 30.06.2016.
 */
@Controller
@RequestMapping("/employee")
public class EmployeeWebController {

    private static final Logger LOGGER = LogManager.getLogger(EmployeeWebController.class);

    private final String EMPLOYEE_REST = "http://localhost:8080/rest/employee/";
    private final String DEPARTMENT_REST = "http://localhost:8080/rest/department/";

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView getAllEmployees() {
        LOGGER.debug("get all employees");

        ModelAndView view = new ModelAndView("allEmployees");

        RestTemplate restTemplate = new RestTemplate();


        try {
            Employee[] employees = restTemplate.getForObject(EMPLOYEE_REST + "/all", Employee[].class);
            Department[] departments = restTemplate.getForObject(DEPARTMENT_REST + "/all", Department[].class);
            view.addObject("departments", departments);
            view.addObject("employees", employees);
        }catch (Exception e) {
            view.addObject("error", "Not found any employees.");
        }
        return view;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView getAddEmployee() {

        LOGGER.debug("adding employee");
        ModelAndView view = new ModelAndView("employeeadd");

        RestTemplate restTemplate = new RestTemplate();

        try {
            Department[] departments = restTemplate.getForObject(DEPARTMENT_REST + "/all", Department[].class);
            view.addObject("employees", new Employee());
            view.addObject("departments", departments);
        } catch (Exception e) {
            view.addObject("error", "Can't add new employee.");
        }

        return view;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addEmployee(RedirectAttributes redirectAttributes,
                                    @RequestParam("fname") String fname,
                                    @RequestParam("lname") String lname,
                                    @RequestParam("mname") String mname,
                                    @RequestParam("birthday") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthday,
                                    @RequestParam("salary") Long salary,
                                    @RequestParam("dep_id") Long dep_id) {
        LOGGER.debug("employee added");

        RestTemplate restTemplate = new RestTemplate();

        try {
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("fname", fname);
            map.add("lname", lname);
            map.add("mname", mname);
            map.add("birthday", birthday.toString());
            map.add("salary", salary.toString());
            map.add("dep_id", dep_id.toString());

            restTemplate.postForObject(EMPLOYEE_REST + "/add", map, String.class);

            redirectAttributes.addFlashAttribute("message", "New employee added.");
            return new ModelAndView("redirect:/employee/all");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute( "error", "Can't add new employee, may be department list is empty");
            return new ModelAndView("redirect:/employee/all");
        }


    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteEmployee(RedirectAttributes redirectAttributes,
                                       @PathVariable long id){
        LOGGER.debug("delete employee with id = " + id);

        ModelAndView view = new ModelAndView("redirect:/employee/all");

        RestTemplate restTemplate = new RestTemplate();

        try {
            restTemplate.delete(EMPLOYEE_REST + "/delete/" + id);

            redirectAttributes.addFlashAttribute("message", "employee deleted");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Can't delete employee whith id = " + id);
        }

        return view;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView getEdit(@PathVariable long id) {
        LOGGER.debug("edit employee with id = " + id);

        ModelAndView view = new ModelAndView("employeeedit");

        RestTemplate restTemplate = new RestTemplate();

        try {
            Employee employee = restTemplate.getForObject(EMPLOYEE_REST + "/id/" + id, Employee.class);
            Department[] departments = restTemplate.getForObject(DEPARTMENT_REST + "/all", Department[].class);
            view.addObject("employee", employee);
            view.addObject("departments", departments);
        } catch (Exception e) {
            view.addObject("error", "can't edit employee with id = " + id);
        }

        return view;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView saveEdit(RedirectAttributes redirectAttributes,
                                 @RequestParam("id") String      id,
                                 @RequestParam("fname") String fname,
                                 @RequestParam("lname") String lname,
                                 @RequestParam("mname") String mname,
                                 @RequestParam("birthday") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthday,
                                 @RequestParam("salary") Long salary,
                                 @RequestParam("dep_id") Long dep_id) {
        LOGGER.debug("edited employee with id = " + id);

        ModelAndView view = new ModelAndView("redirect:/employee/all");

        RestTemplate restTemplate = new RestTemplate();

        try {
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("id", id);
            map.add("fname", fname);
            map.add("lname", lname);
            map.add("mname", mname);
            map.add("birthday", birthday.toString());
            map.add("salary", salary.toString());
            map.add("dep_id", dep_id.toString());

            restTemplate.postForObject(EMPLOYEE_REST + "/edit", map, String.class);

            redirectAttributes.addFlashAttribute("message", "employee edited");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "can't edit employee with id = " + id);
        }

        return view;
    }

    @RequestMapping(value = "/date", method = RequestMethod.GET)
    public ModelAndView getEmployeeByDate(@RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        LOGGER.debug("get employee by date = " + date.toString());

        ModelAndView view = new ModelAndView("employeedate");

        RestTemplate restTemplate = new RestTemplate();

        try {
            Employee[] employees = restTemplate.getForObject(EMPLOYEE_REST + "/date/" + date, Employee[].class);
            Department[] departments = restTemplate.getForObject(DEPARTMENT_REST + "/all", Department[].class);
            view.addObject("employees", employees);
            view.addObject("departments", departments);
        } catch (Exception e) {
            view.addObject("error", "Can't find employees for this date.");
        }

        return view;
    }

    @RequestMapping(value = "/between", method = RequestMethod.GET)
    public ModelAndView getEmployeeBetweenDates(@RequestParam(value = "from")@DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate from,
                                                @RequestParam(value = "to")@DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate to) {
        LOGGER.debug("get employee between " + from.toString() + " and " + to.toString());

        ModelAndView view = new ModelAndView("employeesearch");

        RestTemplate restTemplate = new RestTemplate();

        try {
            Employee[] employees = restTemplate.getForObject(EMPLOYEE_REST + "/dates/" + from + "/" + to, Employee[].class);
            Department[] departments = restTemplate.getForObject(DEPARTMENT_REST + "/all", Department[].class);

            view.addObject("employees", employees);
            view.addObject("departments", departments);
        } catch (Exception e) {
            view.addObject("error", "Can't find employees between this date!");
        }
        return view;
    }
}