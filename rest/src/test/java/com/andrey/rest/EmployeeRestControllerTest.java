package com.andrey.rest;

import com.andrey.model.Employee;
import com.andrey.service.EmployeeService;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.SqlDateSerializer;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by kobri on 30.06.2016.
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring-rest-mock-test.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
public class EmployeeRestControllerTest {


    private MockMvc mockMvc;

    @Resource
    EmployeeRestController employeeRestController;

    @Autowired
    EmployeeService employeeService;

    @Before
    public void setUp() {
        this.mockMvc = standaloneSetup(employeeRestController).
                setMessageConverters(createJacksonMessageConverter()).build();
    }

    @After
    public void down() {
        reset(employeeService);
    }

    private static MappingJackson2HttpMessageConverter createJacksonMessageConverter() {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

        ObjectMapper mapper = createObjectMapperWithJacksonConverter();

        messageConverter.setObjectMapper(mapper);

        return messageConverter;
    }

    private static ObjectMapper createObjectMapperWithJacksonConverter() {
        ObjectMapper mapper = new ObjectMapper();

        SimpleModule simpleModule = new SimpleModule("SimpleModule", new Version(1, 0, 0, null));
        simpleModule.addSerializer(Date.class, new SqlDateSerializer());
        mapper.registerModule(simpleModule);
        return mapper;
    }

    private Employee createEmployee() {
        return new Employee(1L, "Cristiano", "Ronaldo", "Aviero", new LocalDate("1985-02-05"), 322,1);
    }

    @Test
    public void testGetEmployeeById() throws Exception {
        employeeService.getEmployeeById(1);
        expectLastCall().andReturn(createEmployee());
        replay(employeeService);

        ObjectMapper mapper = createObjectMapperWithJacksonConverter();

        Employee employee = createEmployee();

        this.mockMvc.perform(
                get("/employee/id/" + 1)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(employee)));
    }

    @Test
    public void testGetEmployeeWithWrongId() throws Exception {
        employeeService.getEmployeeById(-1);
        expectLastCall().andThrow(new IllegalArgumentException("Employee not found for id=-1, error:Employee with this ID doesn't exist",null));
        replay(employeeService);

        this.mockMvc.perform(
                get("/employee/id" + -1)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        employeeService.getAllEmployees();
        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee(1L, "Cristiano", "Ronaldo", "Aviero", new LocalDate("1985-02-05"), 322, 1));
        employees.add(new Employee(2l, "Wayne", "Rooney", "Mark",new LocalDate("1985-10-24"), 300, 1));
        employees.add(new Employee(3l, "Carlos", "Tevez", "Alberto", new LocalDate("1984-02-05"), 250, 1));
        employees.add(new Employee(4l, "Anthony", "Martial", "Joran",new LocalDate("1995-12-05"), 100, 2));
        employees.add(new Employee(5l, "David", "de Gea", "Quintana",new LocalDate("1990-11-07"), 200, 2));
        employees.add(new Employee(7l, "Lius", "Suarez", "Alberto", new LocalDate("1987-01-24"), 250, 3));
        employees.add(new Employee(8l, "Gareth", "Bale", "Frank", new LocalDate("1989-07-16"), 250, 3));
        employees.add(new Employee(9l, "Karim", "Benzema", "Mostafa", new LocalDate("1987-12-19"), 200, 4));
        employees.add(new Employee(10l, "Poul", "Scholes", "Aron", new LocalDate("1974-11-16"), 180, 4));
        employees.add(new Employee(11l, "Cristiano", "Ronaldo", "Aviero", new LocalDate("1985-02-05"), 322, 1));
        expectLastCall().andReturn(employees);

        replay(employeeService);

        ObjectMapper mapper = createObjectMapperWithJacksonConverter();

        this.mockMvc.perform(
                get("/employee/all")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(employees)));
    }

    @Test
    public void testAddEmployee() throws Exception{
        employeeService.addEmployee(createEmployee());
        expectLastCall().andReturn(11);
        replay(employeeService);

        Employee employee = createEmployee();

        this.mockMvc.perform(
                post("/employee/add")
                        .param("id", String.valueOf(employee.getId()))
                        .param("fname", employee.getFname())
                        .param("lname", employee.getLname())
                        .param("mname", employee.getMname())
                        .param("birthday", employee.getBirthday().toString())
                        .param("salary", String.valueOf(employee.getSalary()))
                        .param("dep_id", String.valueOf(employee.getDep_id()))
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(String.valueOf("11")));
    }

    @Test
    public void testAddWrongEmployee() throws Exception {
        Employee employee = createEmployee();
        employee.setFname(null);
        employeeService.addEmployee(employee);
        expectLastCall().andThrow(new IllegalArgumentException());

        this.mockMvc.perform(
                post("/employee/add")
                        .param("id", String.valueOf(employee.getId()))
                        .param("fname", employee.getFname())
                        .param("lname", employee.getLname())
                        .param("mname", employee.getMname())
                        .param("birthday", employee.getBirthday().toString())
                        .param("salary", String.valueOf(employee.getSalary()))
                        .param("dep_id", String.valueOf(employee.getDep_id()))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteEmployee() throws Exception{
        employeeService.deleteEmployeeById(6);
        expectLastCall();
        replay(employeeService);

        this.mockMvc.perform(
                delete("/employee/delete/" + 6)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("\"Deleted\""));
    }

    @Test
    public void testEditEmployee() throws Exception{

        Employee employee = createEmployee();

        employeeService.editEmployee(employee);
        expectLastCall();
        replay(employeeService);

        this.mockMvc.perform(
                post("/employee/edit")
                        .param("fname", employee.getFname())
                        .param("lname", employee.getLname())
                        .param("mname", employee.getMname())
                        .param("dep_id", String.valueOf(employee.getDep_id()))
                        .param("salary", String.valueOf(employee.getSalary()))
                        .param("id", String.valueOf(employee.getId()))
                        .param("birthday", employee.getBirthday().toString())
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testEditWrongEmployee() throws Exception{
        ObjectMapper mapper = createObjectMapperWithJacksonConverter();
        Employee employee = createEmployee();
        employee.setFname(null);

        employeeService.editEmployee(employee);
        expectLastCall();
        replay(employeeService);

        this.mockMvc.perform(
                post("employee/edit")
                        .param("id", String.valueOf(employee.getId()))
                        .param("fname", employee.getFname())
                        .param("lname", employee.getLname())
                        .param("mname", employee.getMname())
                        .param("birthday", employee.getBirthday().toString())
                        .param("salary", String.valueOf(employee.getSalary()))
                        .param("dep_id", String.valueOf(employee.getDep_id()))
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetEmployeesByDate() throws Exception{
        Employee employee =  new Employee(2L, "Wayne", "Rooney", "Mark", new LocalDate("1985-10-24"), 300, 1);
        ObjectMapper mapper = createObjectMapperWithJacksonConverter();

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        employeeService.getEmployeeByDateOfBirthday(employee.getBirthday());
        expectLastCall().andReturn(employees);
        replay(employeeService);

        this.mockMvc.perform(
                get("/employee/date/" + employee.getBirthday().toString())
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(employees)));
    }

    @Test
    public void getEmployeeByWrongDate() throws  Exception {

        employeeService.getEmployeeByDateOfBirthday(new LocalDate("1200-04-18"));
        expectLastCall().andThrow(new IllegalArgumentException("Employee can't be empty", null));

        replay(employeeService);

        this.mockMvc.perform(
                get("/employee/date/"  + "1200-04-18" )
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("\"Employee can't be empty\""));
    }

    @Test
    public void getEmployeesBetweenDates() throws Exception {
        Employee employee =  new Employee(1L, "Cristiano", "Ronaldo", "Aviero", new LocalDate("1985-02-05"), 322, 1);
        ObjectMapper mapper = createObjectMapperWithJacksonConverter();

        employeeService.getEmployeesBetweenDatesOfBirthday(new LocalDate("1985-02-04"), new LocalDate("1985-02-06"));

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        expectLastCall().andReturn(employees);
        replay(employeeService);

        this.mockMvc.perform(
                get("/employee/dates/" + "1985-02-04" + "/" + "1985-02-06")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(employees)));
    }
}