package david.backendstock.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * The Broadcast Event (WebSocket/Redis PubSub)
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockUpdateEvent {

    @JsonProperty(value = "topic")
    private String topic;

    @JsonProperty(value = "message")
    private String message;

}
