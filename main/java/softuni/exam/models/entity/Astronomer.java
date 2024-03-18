package softuni.exam.models.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "astronomers")
public class Astronomer extends BaseEntity {

    private String firstName;
    private String lastName;
    private Double salary;
    private Double averageObservationHours;
    private LocalDate birthday;
    private Star observingStar;

    @Column(nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public Astronomer setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Column(nullable = false)
    public String getLastName() {
        return lastName;
    }

    public Astronomer setLastName(String lasttName) {
        this.lastName = lasttName;
        return this;
    }

    @Column(nullable = false)
    public Double getSalary() {
        return salary;
    }

    public Astronomer setSalary(Double minTemperature) {
        this.salary = minTemperature;
        return this;
    }

    @Column(nullable = false)
    public Double getAverageObservationHours() {
        return averageObservationHours;
    }

    public Astronomer setAverageObservationHours(Double maxTemperature) {
        this.averageObservationHours = maxTemperature;
        return this;
    }

    @ManyToOne
    public Star getObservingStar() {
        return observingStar;
    }

    public Astronomer setObservingStar(Star observingStars) {
        this.observingStar = observingStars;
        return this;
    }

    // TODO: 6/5/2023 problem_description: i have changed it to true
    @Column(nullable = true)
    public LocalDate getBirthday() {
        return birthday;
    }

    public Astronomer setBirthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }
}
