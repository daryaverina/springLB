package ru.ulstu.is.sbapp.student.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ulstu.is.sbapp.student.model.Position;
import ru.ulstu.is.sbapp.student.service.PositionService;

import java.util.List;

@RestController
@RequestMapping("/position")
public class PositionController {
    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping("p/{id}")
    public Position getPosition(@PathVariable Long id) {
        return positionService.findPosition(id);
    }

    @GetMapping("p/")
    public List<Position> getPositions() {
        return positionService.findAllPositions();
    }

    @PostMapping("p/")
    public Position createPosition(@RequestParam("name") String name,
                               @RequestParam("salaryPerHour") Double salaryPerHour) {
        return positionService.addPosition(name, salaryPerHour);
    }

    @PatchMapping("p/{id}")
    public Position updatePosition(@PathVariable Long id,
                               @RequestParam("name") String name,
                               @RequestParam("salaryPerHour") Double salaryPerHour) {
        return positionService.updatePosition(id, name, salaryPerHour);
    }

    @DeleteMapping("p/{id}")
    public Position deletePosition(@PathVariable Long id) {
        return positionService.deletePosition(id);
    }
}
