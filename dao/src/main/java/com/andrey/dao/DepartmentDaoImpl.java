package com.andrey.dao;

import com.andrey.model.Department;
import com.andrey.model.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kobri on 30.06.2016.
 */
@Component
public class DepartmentDaoImpl implements DepartmentDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    KeyHolder keyHolder = new GeneratedKeyHolder();

    private static final Logger LOGGER = LogManager.getLogger(DepartmentDaoImpl.class);

    public class DepartmentMapperWithSalary implements RowMapper<Department> {
        public Department mapRow(ResultSet resultSet, int i) throws SQLException {
            Department department = new Department();
            department.setId(resultSet.getLong("id"));
            department.setDep_name(resultSet.getString("dep_name"));
            department.setAvgSalary(resultSet.getLong("salary"));
            return department;
        }
    }

    public class DepartmentMapper implements RowMapper<Department> {
        public Department mapRow(ResultSet resultSet, int i) throws SQLException {
            Department department = new Department();
            department.setId(resultSet.getLong("id"));
            department.setDep_name(resultSet.getString("dep_name"));
            return department;
        }
    }

    public class EmployeeMapper implements RowMapper<Employee> {
        @Override
        public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
            Employee employee = new Employee();
            employee.setId(resultSet.getLong("id"));
            employee.setFname(resultSet.getString("fname"));
            employee.setLname(resultSet.getString("lname"));
            employee.setMname(resultSet.getString("mname"));
            employee.setBirthday(new LocalDate(resultSet.getTimestamp("birthday")));
            employee.setSalary(resultSet.getLong("salary"));
            employee.setDep_id(resultSet.getLong("dep_id"));
            return employee;
        }
    }

    @Override
    public List<Department> getAllDepartments() {
        LOGGER.debug("get Departments");
        String sql = "select d.id, d.dep_name, avg(e.salary) as salary from department d left join employee e on d.id = e.dep_id group by d.id;";
        return namedParameterJdbcTemplate.query(sql, new DepartmentMapperWithSalary());
    }

    @Override
    public Long addDepartment(Department department) {
        LOGGER.debug("add department", department);

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("dep_name", department.getDep_name());
        String sql = "insert into department values(null, :dep_name);";
        namedParameterJdbcTemplate.update(sql, sqlParameterSource, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public void deleteDepartmentById(long id) {
        LOGGER.debug("delete department with id = " + id);
        Map<String, Object> map = new HashMap<>(1);

        map.put("id", id);
        String sql = "delete from department where id = :id;";
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void editDepartment(Department department) {
        LOGGER.debug("edit department = " + department);

        Map<String, Object> map = new HashMap<>(2);

        map.put("id", department.getId());
        map.put("dep_name", department.getDep_name());

        String sql = "update department set dep_name = :dep_name where id = :id;";

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public Department getDepartmentById(long id) {
        LOGGER.debug("get department with id = " + id);
        Map<String, Object> map = new HashMap<>(1);
        map.put("id", id);
        String sql = "select *from department where id = :id;";
        DepartmentMapper mapper = new DepartmentMapper();

        return namedParameterJdbcTemplate.queryForObject(sql, map, mapper);
    }

    @Override
    public Department getDepartmentByName(String name) {
        LOGGER.debug("get department with name = " + name);
        Map<String, Object> map = new HashMap<>(1);
        map.put("dep_name", name);
        String sql = "select *from department where dep_name = :dep_name";
        return namedParameterJdbcTemplate.queryForObject(sql, map, new DepartmentMapper());
    }

    @Override
    public List<Employee> getEmployeesBySelectedDepartment(long id) {
        LOGGER.debug("get employees by selected department");
        Map<String, Object> map = new HashMap<>(1);
        map.put("id", id);
        String sql = "select *from employee where dep_id = :id;";
        return namedParameterJdbcTemplate.query(sql, map, new EmployeeMapper());
    }
}