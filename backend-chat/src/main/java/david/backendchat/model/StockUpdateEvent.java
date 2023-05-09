package david.backendchat.model;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Broadcast Event (WebSocket/Redis PubSub)
 */
public class StockUpdateEvent {

    @JsonProperty(value = "topic")
    private String topic;

    @JsonProperty(value = "message")
    private String message;

}
