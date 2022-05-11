package ru.ulstu.is.sbapp.student.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.ulstu.is.sbapp.student.model.Worker;
import ru.ulstu.is.sbapp.student.model.Position;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class PositionService {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Position addPosition(String name,  Double salaryPerHour) {
        if (!StringUtils.hasText(name) || !StringUtils.hasText(salaryPerHour.toString()) ) {
            throw new IllegalArgumentException("Position name is null or empty");
        }
        final Position position = new Position(name, salaryPerHour);
        em.persist(position);
        return position;
    }

    @Transactional(readOnly = true)
    public Position findPosition(Long id) {
        final Position position = em.find(Position.class, id);
        if (position == null) {
            throw new EntityNotFoundException(String.format("Position with id [%s] is not found", id));
        }
        return position;
    }

    @Transactional(readOnly = true)
    public List<Position> findAllPositions() {
        return em.createQuery("select t from Position t", Position.class)
                .getResultList();
    }

    @Transactional
    public Position updatePosition(Long id, String name, Double salaryPerHour) {
        if (!StringUtils.hasText(name) || !StringUtils.hasText(salaryPerHour.toString()) ) {
            throw new IllegalArgumentException("Position name is null or empty");
        }
        final Position currentPosition = findPosition(id);
        currentPosition.setName(name);
        currentPosition.setSalaryPerHour(salaryPerHour);
        return em.merge(currentPosition);
    }

    @Transactional
    public Position deletePosition(Long id) {
        final Position currentPosition = findPosition(id);
        em.remove(currentPosition);
        return currentPosition;
    }

    @Transactional
    public void deleteAllPositions() {
        em.createQuery("delete from Position").executeUpdate();
    }

    @Transactional
    public void addWorkerToPosition(Position position, Worker worker) {
        if (position.toString().isEmpty()) {
            throw new IllegalArgumentException("Position is null or empty");
        }
        position.addWorker(worker);
        em.merge(position);
    }
}
