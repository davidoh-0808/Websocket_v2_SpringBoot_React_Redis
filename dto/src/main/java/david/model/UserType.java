package david.model;

public enum UserType {

    USER("USER"),
    STAFF("STAFF");

    public final String userType;

    private UserType(String userType) {
        this.userType = userType;
    }

}
