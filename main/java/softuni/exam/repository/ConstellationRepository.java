package softuni.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Constellation;

import java.util.Optional;

@Repository
public interface ConstellationRepository extends JpaRepository<Constellation, Integer> {
    Optional<Constellation> findConstellationByName(String name);
    Optional<Constellation> findConstellationById(Long id);
}
