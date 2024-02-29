package soft.uni.springdataautomappingobjects.domain.services.game;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soft.uni.springdataautomappingobjects.domain.dtos.game.GameDTO;
import soft.uni.springdataautomappingobjects.domain.entities.Game;
import soft.uni.springdataautomappingobjects.domain.entities.User;
import soft.uni.springdataautomappingobjects.domain.repositories.game.GameRepository;
import soft.uni.springdataautomappingobjects.domain.repositories.user.UserRepository;
import soft.uni.springdataautomappingobjects.domain.services.user.UserRegisterServiceImpl;

import java.time.LocalDate;
import java.util.Optional;

import static soft.uni.springdataautomappingobjects.domain.constants.OutputMessage.*;
import static soft.uni.springdataautomappingobjects.domain.exseptions.ExceptionMessage.*;

@Service
public class GameServiceImpl implements GameService {
    private final UserRegisterServiceImpl userRegisterService;
    private final GameRepository gameRepository;
    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    public GameServiceImpl(UserRepository userRepository, UserRegisterServiceImpl userRegisterService, GameRepository gameRepository) {
        this.userRegisterService = userRegisterService;
        this.gameRepository = gameRepository;
    }

    @Override
    public void addGame(String[] args) {

        try {
            boolean isValidateUser = validateUser();

            if (isValidateUser) {
                String title = args[1];

                if (gameRepository.existsByTitle(title)) {
                    throw new IllegalArgumentException(GAME_ALREADY_EXIST);
                }

                double price = Double.parseDouble(args[2]);
                double size = Double.parseDouble(args[3]);
                String trailer = args[4];
                String imageURL = args[5];
                String description = args[6];
                LocalDate releaseDate = LocalDate.now();

                GameDTO gameDTO = new GameDTO(title, price, size, trailer, imageURL, description, releaseDate);
                Game gameToSave = mapper.map(gameDTO, Game.class);
                gameRepository.save(gameToSave);

                System.out.printf(SUCCESSFULLY_ADD_GAME, title);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void editGame(String[] args) {
        try {
            boolean isValidateUser = validateUser();

            if (isValidateUser) {
                Long gameId = Long.parseLong(args[1]);

                Optional<Game> doesGameExist = gameRepository.findGameById(gameId);

                if (doesGameExist.isEmpty()) {
                    throw new IllegalArgumentException(INVALID_GAME_ID);
                }

                Game gameToEdit = doesGameExist.get();

                String validatePrice = args[2].replace("price=", "");
                double newGamePrice = Double.parseDouble(validatePrice);

                String validateSize = args[3].replace("size=", "");
                double newGameSize = Double.parseDouble(validateSize);

                gameToEdit.setPrice(newGamePrice);
                gameToEdit.setSize(newGameSize);
                gameRepository.save(gameToEdit);

                System.out.printf(SUCCESSFULLY_EDIT_GAME, gameToEdit.getTitle());
            }
            ;

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void deleteGame(Long id) {
        try {
            boolean isUserValidate = validateUser();

            if (isUserValidate) {
                Optional<Game> doesGameExist = gameRepository.findGameById(id);

                if (doesGameExist.isEmpty()) {
                    throw new IllegalArgumentException(INVALID_GAME_ID);
                }

                Game gameToDelete = doesGameExist.get();
                gameRepository.deleteById(id);
                System.out.printf(SUCCESSFULLY_DELETE_GAME, gameToDelete.getTitle());
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private boolean validateUser() {
        User loggedUser = userRegisterService.loggedUser();

        if (loggedUser == null) {
            throw new IllegalArgumentException(NO_LOGGED_USER);
        }

        if (!loggedUser.isAdmin()) {
            throw new IllegalArgumentException(NO_PERMISSION);
        }

        return true;
    }


}
