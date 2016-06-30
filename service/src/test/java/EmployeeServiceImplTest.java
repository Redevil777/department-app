import com.andrey.model.Employee;
import com.andrey.service.EmployeeService;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kobri on 30.06.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-service-test.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional
public class EmployeeServiceImplTest extends Assert {

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void getAllEmployees() throws Exception{
        List<Employee> employees = employeeService.getAllEmployees();
        assertEquals(10, employees.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEmployeeWithNullFName() throws Exception{
        Employee employee = new Employee(null, null, "lname", "mname", new LocalDate("1994-03-03"), 322, 1);
        Long id = employeeService.addEmployee(employee);
        assertNull(id);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEmployeeWithNullLName() throws Exception{
        Employee employee = new Employee(null, "fname", null, "mname", new LocalDate("1994-03-03"), 322, 1);
        Long id = employeeService.addEmployee(employee);
        assertNull(id);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEmployeeWithNullMName() throws Exception{
        Employee employee = new Employee(null, "fname", "lname", null, new LocalDate("1994-03-03"), 322, 1);
        Long id = employeeService.addEmployee(employee);
        assertNull(id);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEmployeeWithEmptyFName() throws Exception{
        Employee employee = new Employee(null, "", "lname", "mname",new LocalDate("1994-03-03"), 322, 1);
        Long id = employeeService.addEmployee(employee);
        assertNull(id);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEmployeeWithEmptyLName() throws Exception{
        Employee employee = new Employee(null, "fname", "", "mname",new LocalDate("1994-03-03"), 322, 1);
        Long id = employeeService.addEmployee(employee);
        assertNull(id);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEmployeeWithEmptyMName() throws Exception{
        Employee employee = new Employee(null, "fname", "lname", "",new LocalDate("1994-03-03"), 322, 1);
        Long id = employeeService.addEmployee(employee);
        assertNull(id);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEmployeeWithNullDate() throws Exception{
        Employee employee = new Employee(null, "fname", "lname", "mname", null, 322, 1);
        Long id = employeeService.addEmployee(employee);
        assertNull(id);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEmployeeWithNegativeSalary() throws Exception{
        Employee employee = new Employee(null, "fname", "lname", "mname", new LocalDate("1994-03-03"), -322, 1);
        Long id = employeeService.addEmployee(employee);
        assertNull(id);
    }

    @Test
    public void testAddEmployee() throws Exception{
        Employee employee = new Employee(null, "fname", "lname", "mname", new LocalDate("1994-03-03"), 322, 1);
        Long id = employeeService.addEmployee(employee);
        assertNotNull(id);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateEmployeeWithNullFName() throws Exception{
        Employee employee = employeeService.getEmployeeById(1);
        employee.setFname(null);
        employeeService.editEmployee(employee);
        Employee employee1 = employeeService.getEmployeeById(1);
        assertEquals(employee, employee1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateEmployeeWithEmptyFName() throws Exception{
        Employee employee = employeeService.getEmployeeById(1);
        employee.setFname("");
        employeeService.editEmployee(employee);
        Employee employee1 = employeeService.getEmployeeById(1);
        assertEquals(employee, employee1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateEmployeeWithNullLName() throws Exception{
        Employee employee = employeeService.getEmployeeById(1);
        employee.setLname(null);
        employeeService.editEmployee(employee);
        Employee employee1 = employeeService.getEmployeeById(1);
        assertEquals(employee, employee1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateEmployeeWithEmptyLName() throws Exception{
        Employee employee = employeeService.getEmployeeById(1);
        employee.setLname("");
        employeeService.editEmployee(employee);
        Employee employee1 = employeeService.getEmployeeById(1);
        assertEquals(employee, employee1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateEmployeeWithNullMName() throws Exception{
        Employee employee = employeeService.getEmployeeById(1);
        employee.setMname(null);
        employeeService.editEmployee(employee);
        Employee employee1 = employeeService.getEmployeeById(1);
        assertEquals(employee, employee1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateEmployeeWithEmptyMName() throws Exception{
        Employee employee = employeeService.getEmployeeById(1);
        employee.setMname("");
        employeeService.editEmployee(employee);
        Employee employee1 = employeeService.getEmployeeById(1);
        assertEquals(employee, employee1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateEmployeeWithNullDate() throws Exception{
        Employee employee = employeeService.getEmployeeById(1);
        employee.setBirthday(null);
        employeeService.editEmployee(employee);
        Employee employee1 = employeeService.getEmployeeById(1);
        assertEquals(employee, employee1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateEmployeeWithNegativeSalary() throws Exception{
        Employee employee = employeeService.getEmployeeById(1);
        employee.setSalary(-2);
        employeeService.editEmployee(employee);
        Employee employee1 = employeeService.getEmployeeById(1);
        assertEquals(employee, employee1);
    }

    @Test
    public void testUpdateEmployee() throws Exception{
        Employee employee = employeeService.getEmployeeById(5);
        employee.setFname("fname");
        employee.setLname("lname");
        employee.setMname("mname");
        employee.setBirthday(new LocalDate("1994-02-01"));
        employee.setSalary(111);
        employee.setDep_id(33);
        employeeService.editEmployee(employee);
        Employee employee1 = employeeService.getEmployeeById(5);
        assertEquals(employee, employee1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteEmpoyeeWithWrongId() throws Exception{
        int sizeBefore = employeeService.getAllEmployees().size();
        employeeService.deleteEmployee(-1);
        int sizeAfter = employeeService.getAllEmployees().size();
        assertEquals(sizeBefore, sizeAfter);
    }

    @Test
    public void testDeleteEmployee() throws Exception{
        int sizeBefore = employeeService.getAllEmployees().size();
        employeeService.deleteEmployee(1);
        int sizeAfter = employeeService.getAllEmployees().size();
        assertEquals(sizeBefore, sizeAfter+1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEmployeeByWrongId() throws Exception{
        Employee employee = employeeService.getEmployeeById(-2);
        assertNull(employee);
    }

    @Test
    public void testGetEmployeeById() throws Exception{
        Employee employee = new Employee(null, "fname", "lname", "mname",new LocalDate("1994-03-03"), 322, 1);
        Long id = employeeService.addEmployee(employee);
        assertNotNull(id);

        employee.setId(id);

        Employee employee1 = employeeService.getEmployeeById(id);
        assertEquals(employee, employee1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEmployeeByWrongDate() throws Exception{
        List<Employee> employees = employeeService.getEmployeeByDateOfBirthday(new LocalDate("2011-02-01"));
        assertNull(employees);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEmployeeByNullDate() throws Exception{
        List<Employee> employees = employeeService.getEmployeeByDateOfBirthday(null);
        assertNull(employees);
    }

    @Test
    public void getEmployeeByDate() throws Exception{
        List<Employee> employees = employeeService.getEmployeeByDateOfBirthday(new LocalDate("1985-02-05"));
        assertNotNull(employees);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEmployeeBetweenDatesWithNullTo() throws Exception {
        LocalDate date = new LocalDate("1985-02-01");
        List<Employee> employees = employeeService.getEmployeeBetweenDatesOfBirthday(date, null);
        assertNull(employees);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEmployeeBetweenDatesWithNullFrom() throws Exception {
        LocalDate date = new LocalDate("1985-02-01");
        List<Employee> employees = employeeService.getEmployeeBetweenDatesOfBirthday(null, date);
        assertNull(employees);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEmployeeBetweenNullDates() throws Exception {
        List<Employee> employees = employeeService.getEmployeeBetweenDatesOfBirthday(null, null);
        assertNull(employees);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEmployeeDatesBetweenToAndFrom() throws Exception {
        LocalDate from = new LocalDate("1985-02-01");
        LocalDate to = new LocalDate("1995-02-01");
        List<Employee> employees = employeeService.getEmployeeBetweenDatesOfBirthday(to, from);
        assertNull(employees);
    }

    @Test
    public void testGetEmployeeBetweenDates() throws Exception{
        LocalDate from = new LocalDate("1983-02-01");
        LocalDate to = new LocalDate("1995-07-07");
        List<Employee> employees = employeeService.getEmployeeBetweenDatesOfBirthday(from, to);

        assertNotNull(employees);
        for(Employee qwe:employees){
            assertNotNull(qwe.getBirthday());
            assertTrue(from.isBefore(qwe.getBirthday()));
            assertTrue(to.isAfter(qwe.getBirthday()));
        }
    }
}