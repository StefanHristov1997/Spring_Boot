package soft.uni.springdataautomappingobjects.domain.repositories.game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import soft.uni.springdataautomappingobjects.domain.entities.Game;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    boolean existsByTitle(String title);

    Optional<Game> findGameById(Long id);

    void deleteById(Long id);
}
