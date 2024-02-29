package soft.uni.springdataautomappingobjects.domain.services.user;


import soft.uni.springdataautomappingobjects.domain.entities.User;

public interface UserRegisterService {
    void registerUser(String[] args);

    void loginUser(String[] args);

    void logoutUser(String command);

    User loggedUser();
}
