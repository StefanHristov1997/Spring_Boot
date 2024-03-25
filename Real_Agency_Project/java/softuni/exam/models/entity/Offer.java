package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "offers")
public class Offer extends BasicEntity {

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "published_on", nullable = false, columnDefinition = "DATE")
    private LocalDate publishedOn;

    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

    @ManyToOne
    @JoinColumn(name = "apartment_id", nullable = false)
    private Apartment apartment;

    @Override
    public String toString() {
        return String.format("Agent %s %s with offer №%d:",
                agent.getFirstName(), agent.getLastName(), getId()) +
                System.lineSeparator() +
                String.format("   -Apartment area: %.2f", apartment.getArea()) + System.lineSeparator() +
                String.format("   --Town: %s", apartment.getTown().getTownName()) + System.lineSeparator() +
                String.format("   ---Price: %.2f$", getPrice()) + System.lineSeparator();
    }
}
