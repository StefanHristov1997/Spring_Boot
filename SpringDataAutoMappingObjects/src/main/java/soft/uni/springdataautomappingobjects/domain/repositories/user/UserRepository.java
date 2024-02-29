package soft.uni.springdataautomappingobjects.domain.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import soft.uni.springdataautomappingobjects.domain.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmailAndPassword(String email, String password);

    User findUserById(Long id);
}
