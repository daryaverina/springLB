package ru.ulstu.is.sbapp;

import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ulstu.is.sbapp.student.model.Department;
import ru.ulstu.is.sbapp.student.service.DepartmentService;
import ru.ulstu.is.sbapp.student.service.WorkerService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@SpringBootTest
public class JpaDepartmentTests {
    private static final Logger log = LoggerFactory.getLogger(JpaPositionTests.class);

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private WorkerService workerService;

    @Test
    void testDepartmentCreate() {
        workerService.deleteAllWorkers();
        departmentService.deleteAllDepartments();
        final Department department = departmentService.addDepartment("Moscow", "Krasnaya","1A",3);
        log.info(department.toString());
        Assertions.assertNotNull(department.getId());
    }

    @Test
    void testDepartmentRead() {
        workerService.deleteAllWorkers();
        departmentService.deleteAllDepartments();
        final Department department = departmentService.addDepartment("Moscow", "Krasnaya","1A",3);
        log.info(department.toString());
        final Department findDepartment = departmentService.findDepartment(department.getId());
        log.info(findDepartment.toString());
        Assertions.assertEquals(department, findDepartment);
    }

    @Test
    void testDepartmentReadNotFound() {
        workerService.deleteAllWorkers();
        departmentService.deleteAllDepartments();
        Assertions.assertThrows(EntityNotFoundException.class, () -> departmentService.findDepartment(-1L));
    }

    @Test
    void testDepartmentReadAll() {
        workerService.deleteAllWorkers();
        departmentService.deleteAllDepartments();
        departmentService.addDepartment("Moscow", "Krasnaya","1A",1);
        departmentService.addDepartment("Moscow", "Krasnaya","2A",2);
        final List<Department> departments = departmentService.findAllDepartments();
        log.info(departments.toString());
        Assertions.assertEquals(departments.size(), 2);
    }

    @Test
    void testDepartmentReadAllEmpty() {
        workerService.deleteAllWorkers();
        departmentService.deleteAllDepartments();
        final List<Department> departments = departmentService.findAllDepartments();
        log.info(departments.toString());
        Assertions.assertEquals(departments.size(), 0);
    }
}
