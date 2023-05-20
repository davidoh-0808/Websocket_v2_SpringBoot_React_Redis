package david.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import david.model.Item;
import lombok.*;

/**
 * The Broadcast Event (WebSocket/Redis PubSub)
 */
@Getter
@Setter
@Builder
public class StockUpdateEvent {

    @JsonProperty(value = "topic")
    private String topic;

    @JsonProperty(value = "message")
    private Item message;

}
