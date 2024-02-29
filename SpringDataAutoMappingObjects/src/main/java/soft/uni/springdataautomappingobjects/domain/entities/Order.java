package soft.uni.springdataautomappingobjects.domain.entities;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BasicEntity {

    @ManyToOne
    private User user;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"))
    private Set<Game> games;

    public Order() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Game> getGames() {
        return Collections.unmodifiableSet(games);
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }
}
