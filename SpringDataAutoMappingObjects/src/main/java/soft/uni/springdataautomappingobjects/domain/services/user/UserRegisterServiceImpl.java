package soft.uni.springdataautomappingobjects.domain.services.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soft.uni.springdataautomappingobjects.domain.dtos.user.UserDTO;
import soft.uni.springdataautomappingobjects.domain.entities.User;
import soft.uni.springdataautomappingobjects.domain.repositories.user.UserRepository;

import java.util.Optional;

import static soft.uni.springdataautomappingobjects.domain.constants.OutputMessage.*;
import static soft.uni.springdataautomappingobjects.domain.exseptions.ExceptionMessage.*;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {
    private final UserRepository userRepository;
    private final static ModelMapper mapper = new ModelMapper();
    private User loggedUser;

    @Autowired
    public UserRegisterServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(String[] args) {
        String email = args[1];
        String password = args[2];
        String confirmPassword = args[3];
        String fullName = args[4];

        try {
            Optional<User> isUserAlreadyExist = userRepository.findUserByEmailAndPassword(email, password);

            if (isUserAlreadyExist.isEmpty()) {
                UserDTO userRegister = new UserDTO(email, password, confirmPassword, fullName);
                this.loggedUser = mapper.map(userRegister, User.class);
                if (userRepository.count() == 0) {
                    loggedUser.setAdmin(true);
                }
                userRepository.save(loggedUser);


                System.out.printf(SUCCESSFULLY_REGISTER, loggedUser.getFullName());
            } else {
                throw new IllegalArgumentException(ACCOUNT_ALREADY_EXIST);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

    }

    @Override
    public void loginUser(String[] args) {
        String email = args[1];
        String password = args[2];

        Optional<User> doesUserExist = userRepository.findUserByEmailAndPassword(email, password);

        try {
            if (loggedUser == null && doesUserExist.isEmpty()) {
                throw new IllegalArgumentException(INCORRECT_PASSWORD_OR_USERNAME);
            }

            this.loggedUser = doesUserExist.get();
            System.out.printf(SUCCESSFULLY_LOGIN, doesUserExist.get().getFullName());

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

    }

    @Override
    public void logoutUser(String command) {
        try {
            if (this.loggedUser == null) {
                throw new IllegalArgumentException(NO_USER_LOGIN);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return;
        }

        System.out.printf(SUCCESSFULLY_LOGOUT, loggedUser.getFullName());
        this.loggedUser = null;

    }

    @Override
    public User loggedUser() {
        return this.loggedUser;
    }
}
