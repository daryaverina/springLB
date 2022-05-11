package ru.ulstu.is.sbapp.student.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fullName;
    private String email;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "add_fk", nullable = true)
    private Department department;

    @ManyToMany
    @JoinTable(name = "workers_positions",
            joinColumns = @JoinColumn(name = "worker_fk"),
            inverseJoinColumns = @JoinColumn(name = "position_fk"))
    private List<Position> positions = new ArrayList<>();

    public Worker() {}

    public Worker(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
        this.department = new Department();
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) { this.department = department; }

    public void setPositions (List<Position> positions){this.positions = positions;}

    public void addPosition(Position position) {
        positions.add(position);
        if (!position.getWorkers().contains(this)) {
            position.addWorker(this);
        }
    }

    public List<Position> getPositions(){return this.positions;}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worker worker = (Worker) o;
        return Objects.equals(id, worker.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", department='" + department + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
