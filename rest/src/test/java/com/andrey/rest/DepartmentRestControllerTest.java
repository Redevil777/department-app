package com.andrey.rest;

import com.andrey.model.Department;
import com.andrey.model.Employee;
import com.andrey.service.DepartmentService;
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
import java.util.Arrays;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.replay;
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
public class DepartmentRestControllerTest {

    private MockMvc mockMvc;

    @Resource
    DepartmentRestController departmentRestController;

    @Autowired
    DepartmentService departmentService;

    @After
    public void down(){
        reset(departmentService);
    }

    @Before
    public void setUp() {
        this.mockMvc = standaloneSetup(departmentRestController).
                setMessageConverters(mappingJackson2HttpMessageConverter()).build();
    }

    private static MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

        ObjectMapper mapper = createObjectMapperWithJacksonConverter();

        messageConverter.setObjectMapper(mapper);

        return messageConverter;
    }

    private static Department createDepartment()  {
        Department department = new Department(1l, "java developer");

        return department;
    }


    private static ObjectMapper createObjectMapperWithJacksonConverter() {
        ObjectMapper mapper = new ObjectMapper();

        SimpleModule simpleModule = new SimpleModule("simpleModule", new Version(1, 0, 0, null));
        simpleModule.addSerializer(Date.class, new SqlDateSerializer());
        mapper.registerModule(simpleModule);
        return mapper;
    }

    private List<Employee> createEmployees() {
        return new ArrayList<Employee>(
                Arrays.asList(
                        new Employee(1l, "Cristiano", "Ronaldo", "Aviero", new LocalDate("1985-02-05"), 322, 1),
                        new Employee(2l, "Wayne", "Rooney", "Mark", new LocalDate("1985-10-24"), 300, 1),
                        new Employee(3l, "Carlos", "Tevez", "Alberto", new LocalDate("1984-02-05"), 250, 1)
                )
        );
    }

    private List<Department> createDepartments() {
        return new ArrayList<Department>(
                Arrays.asList(
                        new Department(1l, "java developer"),
                        new Department(2l, "c++ developer"),
                        new Department(3l, "change"),
                        new Department(4l, "objective-c developer")
                )
        );
    }

    @Test
    public void testGetAllDepartments() throws Exception {
        departmentService.getAllDepartments();
        expectLastCall().andReturn(createDepartments());
        replay(departmentService);

        List<Department> departments = createDepartments();

        ObjectMapper mapper = createObjectMapperWithJacksonConverter();

        this.mockMvc.perform(
                get("/department/all")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(departments)));
    }

    @Test
    public void testGetDepartmentById() throws Exception{
        departmentService.getDepartmentById(1L);
        expectLastCall().andReturn(createDepartment());
        replay(departmentService);

        Department department = createDepartment();

        ObjectMapper mapper = createObjectMapperWithJacksonConverter();

        this.mockMvc.perform(
                get("/department/id/" + 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(department)));
    }

    @Test
    public void testGetDepartmentByWrongId() throws Exception {
        departmentService.getDepartmentById(-1);
        expectLastCall().andThrow(new IllegalArgumentException("Department not found with id=-1"));

        replay(departmentService);

        this.mockMvc.perform(
                get("/department/id/" + -1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("\"Department not found with id=-1, error: Department with this id doesn't exist\""));

    }

    @Test
    public void testAddDepartmentWithNullData() throws Exception{
        departmentService.addDepartment(new Department(1l, null));
        expectLastCall().andThrow(new IllegalArgumentException());
        replay(departmentService);

        this.mockMvc.perform(
                post("/department/add")
                        .param("name", "")
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetDepartmentByName() throws Exception{

        departmentService.getDepartmentByName("java developer");
        expectLastCall().andReturn(createDepartment());

        replay(departmentService);

        Department department = createDepartment();

        ObjectMapper mapper = createObjectMapperWithJacksonConverter();

        this.mockMvc.perform(
                get("/department/name/" + "java developer")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(department)));
    }

    @Test
    public void testGetDepartmentWithIncorrectName() throws Exception{
        departmentService.getDepartmentByName("this name is incorrect");
        expectLastCall().andThrow(new IllegalArgumentException("", null));

        replay(departmentService);

        this.mockMvc.perform(
                get("/department/name/")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void testDeleteDepartmentById() throws Exception{
        departmentService.deleteDepartmentById(2);
        expectLastCall();
        replay(departmentService);

        this.mockMvc.perform(
                delete("/department/delete/" + 2)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteDepartmentWithIncorrectId() throws Exception {
        departmentService.deleteDepartmentById(-1);
        expectLastCall();
        replay(departmentService);

        this.mockMvc.perform(
                delete("/department/delete/" + -1)
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    public void testAddDepartmentWithNullName() throws Exception{
        departmentService.addDepartment(new Department(null, null));
        expectLastCall().andThrow(new IllegalArgumentException());
        replay(departmentService);

        this.mockMvc.perform(
                post("/department/add")
                        .param("name", "")
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testUpdateDepartment() throws Exception{
        Department department = new Department(3l, "change");

        departmentService.editDepartment(department);

        expectLastCall();
        replay(departmentService);

        this.mockMvc.perform(
                post("/department/edit/")
                        .param("id", String.valueOf(department.getId()))
                        .param("name", department.getDep_name())
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testDepartmentUpdateWithNullName() throws Exception {
        departmentService.editDepartment(anyObject(Department.class));

        replay(departmentService);

        ObjectMapper mapper = new ObjectMapper();
        Department department = createDepartment();
        department.setDep_name(null);
        String departmentJson = mapper.writeValueAsString(department);

        this.mockMvc.perform(
                post("/department/edit")
                        .content(departmentJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetEmployeesBySelectedDepartment() throws Exception {
        departmentService.getEmployeesBySelectedDepartment(1);
        expectLastCall().andReturn(createEmployees());
        replay(departmentService);

        List<Employee> employees = createEmployees();

        ObjectMapper mapper = createObjectMapperWithJacksonConverter();

        this.mockMvc.perform(
                get("/department/employees/" + 1)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(employees)));
    }

}