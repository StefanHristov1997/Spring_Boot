package soft.uni.springdataautomappingobjects.domain.exseptions;

public enum ExceptionMessage {
    ;
    public final static String INCORRECT_EMAIL = "Incorrect email.";
    public final static String INCORRECT_PASSWORD_OR_USERNAME = "Incorrect username / password.";
    public final static String INCORRECT_CONFIRMED_PASSWORD = "Incorrect confirm password.";
    public final static String INCORRECT_GAME_TITLE = "Incorrect game title.";

    public final static String ACCOUNT_ALREADY_EXIST= "Account with this email already exist.";
    public final static String GAME_ALREADY_EXIST = "Game with this title already exist.";

    public final static String INVALID_COMMAND = "Invalid command.";
    public final static String INVALID_GAME_PRICE = "Price can not be negative number.";
    public final static String INVALID_GAME_SIZE = "Size can not be negative number.";
    public final static String INVALID_GAME_TRAILER = "Only videos from YouTube are allowed.";
    public final static String INVALID_GAME_IMAGE_URL = "Invalid image url.";
    public final static String INVALID_GAME_DESCRIPTION = "Game description must be at least 20 symbols.";
    public final static String INVALID_GAME_ID = "This game doesn't exist.";

    public final static String NO_USER_LOGIN = "Cannot log out. No user was logged in.";
    public final static String NO_PERMISSION = "You don't have permission.";
    public final static String NO_LOGGED_USER = "You have to login first.";


}
