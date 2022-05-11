package ru.ulstu.is.sbapp.student.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ulstu.is.sbapp.student.model.Department;
import ru.ulstu.is.sbapp.student.service.DepartmentService;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("d/{id}")
    public Department getDepartment(@PathVariable Long id) {
        return departmentService.findDepartment(id);
    }

    @GetMapping("d/")
    public List<Department> getDepartments() {
        return departmentService.findAllDepartments();
    }

    @PostMapping("d/")
    public Department createDepartment(@RequestParam("city") String city,
                                 @RequestParam("street") String street,
                                 @RequestParam("house") String house,
                                 @RequestParam("apartment") Integer apartment) {
        return departmentService.addDepartment(city, street, house, apartment);
    }

    @PatchMapping("d/{id}")
    public Department updateDepartment(@PathVariable Long id,
                                 @RequestParam("city") String city,
                                 @RequestParam("street") String street,
                                 @RequestParam("house") String house,
                                 @RequestParam("apartment") Integer apartment) {
        return departmentService.updateDepartment(id, city, street, house, apartment);
    }

    @DeleteMapping("d/{id}")
    public Department deleteDepartment(@PathVariable Long id) {
        return departmentService.deleteDepartment(id);
    }
}
