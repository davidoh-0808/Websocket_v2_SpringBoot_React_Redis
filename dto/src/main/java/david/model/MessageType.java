package david.model;

public enum MessageType {

    INIT("INIT"),
    MESSAGE("MESSAGE");

    public final String messageType;

    MessageType(String messageType) {
        this.messageType = messageType;
    }

}
