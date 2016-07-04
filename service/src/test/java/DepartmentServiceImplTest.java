import com.andrey.model.Department;
import com.andrey.model.Employee;
import com.andrey.service.DepartmentService;
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
public class DepartmentServiceImplTest extends Assert {


    @Autowired
    private DepartmentService departmentService;

    @Test
    public void GetAllDepartments() throws Exception {
        List<Department> departments = departmentService.getAllDepartments();
        assertNotNull(departments);
        assertEquals(4, departments.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetDepartmentByIdWithNegativeId() throws Exception{
        Department department = departmentService.getDepartmentById(-2l);
        assertNull(department);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetDepartmentByIdWithZeroId() throws Exception{
        Department department = departmentService.getDepartmentById(0l);
        assertNull(department);
    }

    @Test
    public void testGetDepartmentById() throws Exception {
        Department department = departmentService.getDepartmentById(1);
        assertNotNull(department);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddDepartmentNameWithNullName() throws Exception{
        Department department = new Department(null, null);
        Long id = departmentService.addDepartment(department);
        assertNull(id);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddDepartmentWithEmptyName() throws Exception{
        Department department = new Department(1l, "");
        Long id = departmentService.addDepartment(department);

        assertNull(id);
    }

    @Test
    public void testAddDepartment() throws Exception{
        int sizeBefore = departmentService.getAllDepartments().size();
        Department department = new Department(null, "new department121312");

        departmentService.addDepartment(department);

        int sizeAfter = departmentService.getAllDepartments().size();

        assertEquals(sizeBefore, sizeAfter-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteDepartmentWithWrongId() throws Exception{
        int sizeBefore = departmentService.getAllDepartments().size();
        departmentService.deleteDepartmentById(-1);

        int sizeAfter = departmentService.getAllDepartments().size();

        assertEquals(sizeAfter, sizeBefore);
    }

    @Test
    public void testDeleteDepartmentById() throws Exception{
        int sizeBefore = departmentService.getAllDepartments().size();

        departmentService.deleteDepartmentById(4);

        int sizeAfter = departmentService.getAllDepartments().size();

        assertEquals(sizeBefore, sizeAfter+1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEditDepartmentWithDataNull() throws Exception{
        departmentService.editDepartment(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEditDepartmentWithIncorrectName() throws Exception{
        Department department = departmentService.getDepartmentById(1);

        department.setDep_name(null);

        departmentService.editDepartment(department);

        department = departmentService.getDepartmentById(1);

        assertNotNull(department.getDep_name());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEditDepartmentWithNotExistedId() throws Exception{
        Department department = new Department(-1l, "asd");
        departmentService.editDepartment(department);
    }

    @Test
    public void testEditDepartment() throws Exception{
        String new_name = "new name";

        Department department = departmentService.getDepartmentById(1);

        department.setDep_name(new_name);

        departmentService.editDepartment(department);

        department = departmentService.getDepartmentById(1);

        assertEquals(new_name, department.getDep_name());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetDepartmentByNullName() throws Exception{
        Department department = departmentService.getDepartmentByName(null);
        assertNull(department);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetDepartmentByEmptyName() throws Exception{
        Department department = departmentService.getDepartmentByName("");
        assertNull(department);
    }

    @Test
    public void testGetEmployeesBySelectedDepartment() throws Exception {
        List<Employee> employees = departmentService.getEmployeesBySelectedDepartment(1);
        assertNotNull(employees);
        assertEquals(3, employees.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEmployeeBySelectedDepartmentWithWrongId() throws Exception{
        List<Employee> employees = departmentService.getEmployeesBySelectedDepartment(5);
        assertNull(employees);
    }
}