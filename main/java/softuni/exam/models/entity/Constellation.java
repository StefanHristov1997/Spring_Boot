package softuni.exam.models.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "constellations")
public class Constellation extends BaseEntity {

    private String name;
    private String description;
    private Set<Star> stars = new HashSet<>();

    @Column(unique = true, nullable = false)
    public String getName() {
        return name;
    }

    public Constellation setName(String countryName) {
        this.name = countryName;
        return this;
    }

    @Column(nullable = false)
    public String getDescription() {
        return description;
    }

    public Constellation setDescription(String currency) {
        this.description = currency;
        return this;
    }

    @OneToMany(mappedBy = "constellation", fetch = FetchType.EAGER)
    public Set<Star> getStars() {
        return stars;
    }

    public Constellation setStars(Set<Star> stars) {
        this.stars = stars;
        return this;
    }
}
