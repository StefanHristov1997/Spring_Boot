package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table (name = "teachers")
public class Teacher extends User {
    @Column (length = 50, nullable = false, unique = true)
    private String email;
    @Column (name = "salary_per_hour")
    private double salaryPerHour;
    @OneToMany
    @JoinTable (joinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
    private List<Course> courses;

    public Teacher() {
        this.courses = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public double getSalaryPerHour() {
        return salaryPerHour;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSalaryPerHour(double salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }

    public List<Course> getCourses() {
        return Collections.unmodifiableList(courses);
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
