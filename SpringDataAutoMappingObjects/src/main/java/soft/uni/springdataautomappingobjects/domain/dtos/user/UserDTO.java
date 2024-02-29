package soft.uni.springdataautomappingobjects.domain.dtos.user;

import java.util.regex.Pattern;

import static soft.uni.springdataautomappingobjects.domain.constants.Validation.EMAIL_VERIFICATION;
import static soft.uni.springdataautomappingobjects.domain.constants.Validation.PASSWORD_VERIFICATION;
import static soft.uni.springdataautomappingobjects.domain.exseptions.ExceptionMessage.*;

public class UserDTO {
    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;

    public UserDTO(String email, String password, String confirmPassword, String fullName) {
        setEmail(email);
        setPassword(password);
        setConfirmPassword(confirmPassword);
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        boolean isEmailValid = Pattern.matches(EMAIL_VERIFICATION, email);

        if (!isEmailValid) {
            throw new IllegalArgumentException(INCORRECT_EMAIL);
        }

        this.email = email;
    }

    public void setPassword(String password) {
        boolean isPasswordValid = Pattern.matches(PASSWORD_VERIFICATION, password);

        if (!isPasswordValid) {
            throw new IllegalArgumentException(INCORRECT_PASSWORD_OR_USERNAME);
        }

        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        boolean isConfirmedPasswordValid = Pattern.matches(password, confirmPassword);

        if (!isConfirmedPasswordValid) {
            throw new IllegalArgumentException(INCORRECT_CONFIRMED_PASSWORD);
        }
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }
}
