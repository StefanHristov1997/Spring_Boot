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
@Table(name = "cities")
public class City extends BaseEntity {

    @Column(name = "city_name", nullable = false, unique = true)
    private String cityName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int population;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;


}
