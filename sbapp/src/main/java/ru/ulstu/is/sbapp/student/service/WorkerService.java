package ru.ulstu.is.sbapp.student.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.ulstu.is.sbapp.student.model.Department;
import ru.ulstu.is.sbapp.student.model.Position;
import ru.ulstu.is.sbapp.student.model.Worker;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class WorkerService {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Worker addWorker(String fullName, String email) {
        if (!StringUtils.hasText(fullName) || !StringUtils.hasText(email)) {
            throw new IllegalArgumentException("Worker name is null or empty");
        }
        final Worker worker = new Worker(fullName, email);
        em.persist(worker);
        return worker;
    }

    @Transactional(readOnly = true)
    public Worker findWorker(Long id) {
        final Worker worker = em.find(Worker.class, id);
        if (worker == null) {
            throw new EntityNotFoundException(String.format("Worker with id [%s] is not found", id));
        }
        return worker;
    }

    @Transactional(readOnly = true)
    public List<Worker> findAllWorkers() {
        return em.createQuery("select c from Worker c", Worker.class)
                .getResultList();
    }

    @Transactional
    public Worker updateWorker(Long id, String fullName, String email, Department department) {
        if (!StringUtils.hasText(fullName) || !StringUtils.hasText(email.toString())) {
            throw new IllegalArgumentException("Worker name is null or empty");
        }
        final Worker currentWorker = findWorker(id);
        currentWorker.setFullName(fullName);
        currentWorker.setEmail(email);
        currentWorker.setDepartment(department);
        return em.merge(currentWorker);
    }

    @Transactional
    public Worker deleteWorker(Long id) {
        final Worker currentWorker = findWorker(id);
        em.remove(currentWorker);
        return currentWorker;
    }

    @Transactional
    public void deleteAllWorkers() {
        em.createQuery("delete from Worker").executeUpdate();
    }

    @Transactional
    public Worker addDepartment(Worker worker, Department department) {
        if (department.getId().toString().isEmpty()) {
            throw new IllegalArgumentException("Department's id is null or empty");
        }
        worker.setDepartment(department);
        department.addWorker(worker);
        return em.merge(worker);
    }

    @Transactional
    public void addPositionToWorker(Worker worker, Position position) {
        if (position.getId().toString().isEmpty()) {
            throw new IllegalArgumentException("Position is null or empty");
        }
        worker.addPosition(position);
        em.merge(worker);
    }
}
