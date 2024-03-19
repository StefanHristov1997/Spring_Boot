package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "jobs")
public class Job extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private double salary;

    @Column(name = "hoursaweek", nullable = false)
    private double hoursAWeek;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Job title: ").append(getTitle()).append(System.lineSeparator());
        sb.append(String.format("-Salary: %.2f", getSalary())).append(System.lineSeparator());
        sb.append(String.format("--Hours a week: %.2fh.", getHoursAWeek())).append(System.lineSeparator());

        return sb.toString();
    }
}
