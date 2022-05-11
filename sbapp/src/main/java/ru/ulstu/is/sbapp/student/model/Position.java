package ru.ulstu.is.sbapp.student.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column()
    private String name;
    private Double salaryPerHour;

    @ManyToMany(mappedBy = "positions")
    private List<Worker> workers = new ArrayList<>();

    public Position() {}

    public Position(String name, Double salaryPerHour) {
        this.name = name;
        this.salaryPerHour = salaryPerHour;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalaryPerHour() {
        return salaryPerHour;
    }

    public void setSalaryPerHour(Double salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }

    public void setWorkers(List<Worker> workers){this.workers = workers;}

    public void addWorker(Worker worker){
        workers.add(worker);
        if (!worker.getPositions().contains(this)) {
            worker.addPosition(this);
        }
    }

    public List<Worker> getWorkers(){return this.workers;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(id, position.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salaryPerHour='" + salaryPerHour + '\'' +
                '}';
    }
}
