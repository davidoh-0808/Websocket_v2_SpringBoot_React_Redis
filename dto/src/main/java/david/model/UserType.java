package david.model;

public enum UserType {

    USER("USER"),
    STAFF("STAFF");

    public final String userType;

    UserType(String userType) {
        this.userType = userType;
    }

}
