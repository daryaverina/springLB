package ru.ulstu.is.sbapp;

import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ulstu.is.sbapp.student.model.Worker;
import ru.ulstu.is.sbapp.student.model.Position;
import ru.ulstu.is.sbapp.student.service.WorkerService;
import ru.ulstu.is.sbapp.student.service.PositionService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class JpaPositionTests {
    private static final Logger log = LoggerFactory.getLogger(JpaPositionTests.class);

    @Autowired
    private PositionService positionService;
    @Autowired
    private WorkerService workerService;

    @Test
    void testTariffCreate() {
        positionService.deleteAllPositions();
        final Position position = positionService.addPosition("Test",32.3);
        log.info(position.toString());
        Assertions.assertNotNull(position.getId());
    }

    @Test
    void testPositionRead() {
        positionService.deleteAllPositions();
        final Position position = positionService.addPosition("Test", 32.3);
        log.info(position.toString());
        final Position findPosition = positionService.findPosition(position.getId());
        log.info(findPosition.toString());
        Assertions.assertEquals(position, findPosition);
    }

    @Test
    void testPositionReadNotFound() {
        positionService.deleteAllPositions();
        Assertions.assertThrows(EntityNotFoundException.class, () -> positionService.findPosition(-1L));
    }

    @Test
    void testPositionReadAll() {
        positionService.deleteAllPositions();
        positionService.addPosition("Test1", 12.3);
        positionService.addPosition("Test2", 12.0);
        final List<Position> positions = positionService.findAllPositions();
        log.info(positions.toString());
        Assertions.assertEquals(positions.size(), 2);
    }

    @Test
    void testPositionReadAllEmpty() {
        positionService.deleteAllPositions();
        final List<Position> positions = positionService.findAllPositions();
        log.info(positions.toString());
        Assertions.assertEquals(positions.size(), 0);
    }

    @Test
    void testTariffAddClient(){
        workerService.deleteAllWorkers();
        positionService.deleteAllPositions();
        final Worker worker = workerService.addWorker("Petrov Damon Igorevich", "Damon@gmail.com");
        final Position position = positionService.addPosition("Test", 32.3);
        positionService.addWorkerToPosition(position, worker);
        log.info(worker.getPositions().toString());
        log.info(position.getWorkers().toString());
        Assertions.assertEquals(worker.getPositions().get(0), position);
    }
}
