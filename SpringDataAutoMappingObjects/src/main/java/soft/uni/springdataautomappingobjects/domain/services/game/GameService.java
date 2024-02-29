package soft.uni.springdataautomappingobjects.domain.services.game;

public interface GameService {
    void addGame(String[] args);

    void editGame(String[] args);

    void deleteGame(Long id);
}
