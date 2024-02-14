package entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table (name = "store_locations")
public class StoreLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "location_name", nullable = false)
    private String locationName;

    @OneToMany
    private Set<Sale> sales;
}
