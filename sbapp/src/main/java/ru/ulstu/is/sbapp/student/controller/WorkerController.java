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
import ru.ulstu.is.sbapp.student.model.Worker;
import ru.ulstu.is.sbapp.student.service.WorkerService;

import java.util.List;

@RestController
@RequestMapping("/worker")
public class WorkerController {
    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @GetMapping("w/{id}")
    public Worker getWorker(@PathVariable Long id) {
        return workerService.findWorker(id);
    }

    @GetMapping("w/")
    public List<Worker> getWorkers() {
        return workerService.findAllWorkers();
    }

    @PostMapping("w/")
    public Worker createWorker(@RequestParam("fullName") String fullName,
                               @RequestParam("email") String email) {
        return workerService.addWorker(fullName, email);
    }

    @PatchMapping("w/{id}")
    public Worker updateWorker(@PathVariable Long id,
                               @RequestParam("fullName") String fullName,
                               @RequestParam("email") String email,
                               @RequestParam("add_fk") Department department) {
        return workerService.updateWorker(id, fullName, email, department);
    }

    @DeleteMapping("w/{id}")
    public Worker deleteWorker(@PathVariable Long id) {
        return workerService.deleteWorker(id);
    }
}
