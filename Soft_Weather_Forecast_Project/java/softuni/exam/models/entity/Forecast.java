package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.enums.DayOfWeek;

import javax.persistence.*;
import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "forecasts")
public class Forecast extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(name = "max_temperature", nullable = false)
    private double maxTemperature;

    @Column(name = "min_temperature", nullable = false)
    private double minTemperature;

    @Column(nullable = false)
    private Time sunrise;

    @Column(nullable = false)
    private Time sunset;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("City: %s", getCity().getCityName())).append(System.lineSeparator());
        sb.append(String.format("-min temperature: %.2f", getMinTemperature())).append(System.lineSeparator());
        sb.append(String.format("--max temperature: %.2f", getMaxTemperature())).append(System.lineSeparator());
        sb.append(String.format("---sunrise: %s", getSunrise())).append(System.lineSeparator());
        sb.append(String.format("----sunset: %s", getSunset())).append(System.lineSeparator());

        return sb.toString();
    }
}
