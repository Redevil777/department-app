package com.andrey.web;

import com.andrey.model.Department;
import com.andrey.model.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
@RequestMapping("/department")
public class DepartmentWebController {

    private static final Logger LOGGER = LogManager.getLogger(DepartmentWebController.class);

    private final String DEPARTMENT_REST = "http://localhost:8080/rest/department/";


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView getAllDepartments() {
        LOGGER.debug("get all departments");

        ModelAndView view = new ModelAndView("departmentall");

        RestTemplate restTemplate = new RestTemplate();

        try {
            Department[] departments = restTemplate.getForObject(DEPARTMENT_REST + "/all", Department[].class);

            view.addObject("departments", departments);
        } catch (Exception e) {
            view.addObject("error", "Not found any departments.");
        }
        return view;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteDepartmentById(RedirectAttributes redirectAttributes,
                                             @PathVariable long id) {
        LOGGER.debug("delete department with id = " + id);

        ModelAndView view = new ModelAndView("redirect:/department/all");

        try {
            RestTemplate restTemplate = new RestTemplate();

            restTemplate.delete(DEPARTMENT_REST + "/delete/" + id);

            redirectAttributes.addFlashAttribute("message", "Department deleted");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute( "error", "Can't remove department with id = " + id);
        }

        return view;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView getEditDepartment(@PathVariable long id){
        LOGGER.debug("edit department with id = " + id);

        ModelAndView view = new ModelAndView("departmentedit");

        try {
            RestTemplate restTemplate = new RestTemplate();

            Department department = restTemplate.getForObject(DEPARTMENT_REST + "/id/" + id, Department.class);

            view.addObject("department", department);
        } catch (Exception e) {
            view.addObject("errot", "Can't edit department whit id = " + id);
        }

        return view;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView saveEditDepartment(RedirectAttributes redirectAttributes,
                                           @RequestParam("id")   String   id,
                                           @RequestParam("dep_name") String   name){
        LOGGER.debug("edited department with id = " + id);

        ModelAndView view = new ModelAndView("redirect:/department/all");

        RestTemplate restTemplate = new RestTemplate();

        try {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("id", id);
            map.add("name", name);

            restTemplate.postForObject(DEPARTMENT_REST + "/edit", map, String.class);

            redirectAttributes.addFlashAttribute("message", "Department edited");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Can't edit department whit id =" + id);
        }
        return view;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView getAddDepartment(){
        ModelAndView view = new ModelAndView("departmentadd");

        view.addObject("department", new Department());
        return view;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView AddDepartment(RedirectAttributes redirectAttributes,
                                      @RequestParam("dep_name") String dep_name){
        LOGGER.debug("add new department.");

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("dep_name", dep_name);
        try {
            restTemplate.postForObject(DEPARTMENT_REST + "/add",map, String.class);

            redirectAttributes.addFlashAttribute("message", "New department added.");

            return new ModelAndView("redirect:/department/all");
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("error", "Department with this name already exist.");

            return new ModelAndView("redirect:/department/all");
        }


    }

    @RequestMapping(value = "/employees/{id}", method = RequestMethod.GET)
    public ModelAndView getEmployeesBySelectedDepartment(@PathVariable long id){
        LOGGER.debug("show employees");

        ModelAndView view = new ModelAndView("departmentemployee");

        RestTemplate restTemplate = new RestTemplate();

        try {
            Employee[] employees = restTemplate.getForObject(DEPARTMENT_REST + "/employees/" + id, Employee[].class);

            view.addObject("employees", employees);
        } catch (Exception e) {
            view.addObject("message", "Not found any employees.");
        }

        return view;
    }

    @RequestMapping(value = "/salary/{id}", method = RequestMethod.GET)
    public ModelAndView getAverageSalaryBySelectedDepartment(@PathVariable long id) {
        LOGGER.debug("show average salary in select department.");

        ModelAndView view = new ModelAndView("avgsalary");

        RestTemplate restTemplate = new RestTemplate();

        try {
            long salary = restTemplate.getForObject(DEPARTMENT_REST + "/avg_salary/" + id, Long.class);

            view.addObject("avg", salary);
        } catch (Exception e) {
            view.addObject("error", "Not found any employees.");
        }

        return view;
    }
}