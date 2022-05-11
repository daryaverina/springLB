package ru.ulstu.is.sbapp;

import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ulstu.is.sbapp.student.model.Department;
import ru.ulstu.is.sbapp.student.model.Worker;
import ru.ulstu.is.sbapp.student.model.Position;
import ru.ulstu.is.sbapp.student.service.DepartmentService;
import ru.ulstu.is.sbapp.student.service.WorkerService;
import ru.ulstu.is.sbapp.student.service.PositionService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@SpringBootTest
public class JpaWorkerTests {
    private static final Logger log = LoggerFactory.getLogger(JpaPositionTests.class);

    @Autowired
    private WorkerService workerService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private PositionService positionService;

    @Test
    void testWorkerCreate() {
        workerService.deleteAllWorkers();
        final Worker worker = workerService.addWorker("Petrov Damon Igorevich", "Damon@gmail.com");
        log.info(worker.toString());
        Assertions.assertNotNull(worker.getId());
    }

    @Test
    void testWorkerRead() {
        workerService.deleteAllWorkers();
        final Worker worker = workerService.addWorker("Petrov Damon Igorevich", "Damon@gmail.com");
        log.info(worker.toString());
        final Worker findWorker = workerService.findWorker(worker.getId());
        log.info(findWorker.toString());
        Assertions.assertEquals(worker, findWorker);
    }

    @Test
    void testClientReadNotFound() {
        workerService.deleteAllWorkers();
        Assertions.assertThrows(EntityNotFoundException.class, () -> workerService.findWorker(-1L));
    }

    @Test
    void testPositionReadAll() {
        workerService.deleteAllWorkers();
        workerService.addWorker("Petrov Damon Igorevich", "Damon@gmail.com");
        workerService.addWorker("Pik Oleg Petrovich", "pop@gmail.com");
        final List<Worker> workers = workerService.findAllWorkers();
        log.info(workers.toString());
        Assertions.assertEquals(workers.size(), 2);
    }

    @Test
    void testWorkerReadAllEmpty() {
        workerService.deleteAllWorkers();
        final List<Worker> workers = workerService.findAllWorkers();
        log.info(workers.toString());
        Assertions.assertEquals(workers.size(), 0);
    }

    @Test
    void testWorkerAddPosition(){
        workerService.deleteAllWorkers();
        positionService.deleteAllPositions();
        final Worker worker = workerService.addWorker("Petrov Damon Igorevich", "Damon@gmail.com");
        final Position position = positionService.addPosition("Test", 32.3);
        workerService.addPositionToWorker(worker, position);
        Assertions.assertEquals(position.getWorkers().get(0), worker);
    }

    @Test
    void testWorkerAdddepartment(){
        workerService.deleteAllWorkers();
        departmentService.deleteAllDepartments();
        final Worker worker = workerService.addWorker("Petrov Damon Igorevich", "Damon@gmail.com");
        final Department department = departmentService.addDepartment("Moscow", "Krasnaya","1A",3);
        workerService.addDepartment(worker, department);
        log.info(department.getWorkers().toString());
        log.info(worker.getDepartment().toString());
        Assertions.assertEquals(department.getWorkers().get(0), worker);
        Assertions.assertEquals(worker.getDepartment(), department);
    }
}
