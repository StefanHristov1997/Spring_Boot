package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "companies")
public class Company extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String website;

    @Column(name = "date_established", nullable = false, columnDefinition = "DATE")
    private Date dateEstablished;

    @OneToMany
    @JoinTable(name = "companies_jobs", joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "jobs_id", referencedColumnName = "id"))
    private List<Job> jobs;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    public Company() {
        this.jobs = new ArrayList<>();
    }

}
