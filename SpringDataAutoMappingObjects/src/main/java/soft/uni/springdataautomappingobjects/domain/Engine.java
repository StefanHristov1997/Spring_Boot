package soft.uni.springdataautomappingobjects.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import soft.uni.springdataautomappingobjects.domain.services.game.GameServiceImpl;
import soft.uni.springdataautomappingobjects.domain.services.user.UserRegisterServiceImpl;

import java.util.Scanner;

import static soft.uni.springdataautomappingobjects.domain.constants.Command.*;
import static soft.uni.springdataautomappingobjects.domain.exseptions.ExceptionMessage.INVALID_COMMAND;

@Component
public class Engine implements CommandLineRunner {
    private UserRegisterServiceImpl userRegisterService;
    private GameServiceImpl gameService;

    @Autowired
    public Engine(UserRegisterServiceImpl userRegisterService, GameServiceImpl gameService) {
        this.userRegisterService = userRegisterService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        String[] arg = scanner.nextLine().split("\\|");
        String command = arg[0];

        while (!command.isEmpty()) {
            switch (command) {
                case REGISTER_USER -> userRegisterService.registerUser(arg);
                case LOGIN_USER -> userRegisterService.loginUser(arg);
                case LOGOUT_USER -> userRegisterService.logoutUser(command);
                case ADD_GAME -> gameService.addGame(arg);
                case EDIT_GAME -> gameService.editGame(arg);
                case DELETE_GAME -> gameService.deleteGame(Long.parseLong(arg[1]));
                default -> System.out.println(INVALID_COMMAND);
            }

            arg = scanner.nextLine().split("\\|");
            command = arg[0];
        }
    }
}
