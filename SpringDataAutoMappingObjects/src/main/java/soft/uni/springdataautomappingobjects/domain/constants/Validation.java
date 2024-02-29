package soft.uni.springdataautomappingobjects.domain.constants;

public enum Validation {
    ;
    public static final String EMAIL_VERIFICATION = "^(.+)@(.+)$";
    public static final String PASSWORD_VERIFICATION = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).+";
    public static final String GAME_TITLE_VERIFICATION = "^[A-Z][A-Za-z0-9]{3,100}";
    public static final String GAME_IMAGE_FIRST_URL_VERIFICATION = "http://";
    public static final String GAME_IMAGE_SECOND_URL_VERIFICATION = "https://";
}
