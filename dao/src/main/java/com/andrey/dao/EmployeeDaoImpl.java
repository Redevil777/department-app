package com.andrey.dao;

import com.andrey.model.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
public class EmployeeDaoImpl implements EmployeeDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    KeyHolder keyHolder = new GeneratedKeyHolder();

    private static final Logger LOGGER = LogManager.getLogger(DepartmentDaoImpl.class);

    public class EmployeeMapper implements RowMapper<Employee> {
        @Override
        public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
            Employee employee = new Employee();
            employee.setId(resultSet.getLong("id"));
            employee.setFname(resultSet.getString("fname"));
            employee.setLname(resultSet.getString("lname"));
            employee.setMname(resultSet.getString("mname"));
            employee.setBirthday(new LocalDate(resultSet.getTimestamp("birthday")));//resultSet.getDate("birthday")
            employee.setSalary(resultSet.getLong("salary"));
            employee.setDep_id(resultSet.getLong("dep_id"));
            return employee;
        }
    }


    @Override
    public List<Employee> getAllEmployees() {
        LOGGER.debug("get all employee");
        String sql = "select *from employee;";

        return namedParameterJdbcTemplate.query(sql, new EmployeeMapper());
    }

    @Override
    public long addEmployee(Employee employee) {
        LOGGER.debug("add employee " + employee);
        MapSqlParameterSource sqlParameterSource= new MapSqlParameterSource();
        sqlParameterSource.addValue("id", employee.getId());
        sqlParameterSource.addValue("fname", employee.getFname());
        sqlParameterSource.addValue("lname", employee.getLname());
        sqlParameterSource.addValue("mname", employee.getMname());
        sqlParameterSource.addValue("birthday", employee.getBirthday().toString());
        sqlParameterSource.addValue("salary", employee.getSalary());
        sqlParameterSource.addValue("dep_id", employee.getDep_id());

        String sql = "insert into employee values(null, :fname, :lname, :mname, :birthday, :salary, :dep_id);";

        namedParameterJdbcTemplate.update(sql, sqlParameterSource, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public void deleteEmployeeById(long id) {
        LOGGER.debug("delete employee with id = " + id);

        Map<String, Object> map = new HashMap<>(1);
        map.put("id", id);
        String sql = "delete from employee where id = :id;";
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void editEmployee(Employee employee) {
        LOGGER.debug("edit employee where id = " + employee.getId());
        Map<String, Object> map = new HashMap<>();

        map.put("id", employee.getId());
        map.put("fname", employee.getFname());
        map.put("lname", employee.getLname());
        map.put("mname", employee.getMname());
        map.put("birthday", employee.getBirthday().toString());
        map.put("salary", employee.getSalary());
        map.put("dep_id", employee.getDep_id());

        String sql = "update employee set fname = :fname, lname = :lname, mname = :mname, birthday = :birthday, salary = :salary, dep_id = :dep_id where id = :id;";
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public Employee getEmployeeById(long id) {
        LOGGER.debug("get employee with id = " + id);

        Map<String, Object> map = new HashMap<>(1);
        map.put("id", id);

        String sql = "select *from employee where id = :id;";

        return namedParameterJdbcTemplate.queryForObject(sql, map, new EmployeeMapper());
    }

    @Override
    public List<Employee> getEmployeeByDateOfBirthday(LocalDate date) {
        LOGGER.debug("get employee by date = " + date.toString());

        Map<String, Object> map = new HashMap<>(1);
        map.put("birthday", date.toString());

        String sql = "select *from employee where birthday = :birthday;";
        return namedParameterJdbcTemplate.query(sql, map, new EmployeeMapper());
    }

    @Override
    public List<Employee> getEmployeesBetweenDatesOfBirthday(LocalDate from, LocalDate to) {
        LOGGER.debug("get employee between " + from + " to " + to);

        Map<String, Object> map = new HashMap<>(2);
        map.put("from", from.toString());
        map.put("to", to.toString());

        String sql = "select *from employee where birthday between :from and :to;";

        return namedParameterJdbcTemplate.query(sql, map, new EmployeeMapper());
    }
}