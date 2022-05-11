package ru.ulstu.is.sbapp.student.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.ulstu.is.sbapp.student.model.Department;
import ru.ulstu.is.sbapp.student.model.Worker;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class DepartmentService {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Department addDepartment(String city, String street, String house, Integer apartment) {
        if (!StringUtils.hasText(city) || !StringUtils.hasText(street) || !StringUtils.hasText(house) || !StringUtils.hasText(apartment.toString())) {
            throw new IllegalArgumentException("Department name is null or empty");
        }
        final Department department = new Department(city, street, house, apartment);
        em.persist(department);
        return department;
    }

    @Transactional(readOnly = true)
    public Department findDepartment(Long id) {
        final Department department = em.find(Department.class, id);
        if (department == null) {
            throw new EntityNotFoundException(String.format("Department with id [%s] is not found", id));
        }
        return department;
    }

    @Transactional(readOnly = true)
    public List<Department> findAllDepartments() {
        return em.createQuery("select a from Department a", Department.class)
                .getResultList();
    }

    @Transactional
    public Department updateDepartment(Long id, String city, String street, String house, Integer apartment) {
        if (!StringUtils.hasText(city) || !StringUtils.hasText(street) || !StringUtils.hasText(house) || !StringUtils.hasText(apartment.toString())) {
            throw new IllegalArgumentException("Department name is null or empty");
        }
        final Department currentDepartment = findDepartment(id);
        currentDepartment.setCity(city);
        currentDepartment.setStreet(street);
        currentDepartment.setHouse(house);
        currentDepartment.setApartment(apartment);
        return em.merge(currentDepartment);
    }

    @Transactional
    public Department deleteDepartment(Long id) {
        final Department currentDepartment = findDepartment(id);
        em.remove(currentDepartment);
        return currentDepartment;
    }

    @Transactional
    public void deleteAllDepartments() {
        em.createQuery("delete from Department").executeUpdate();
    }
}
