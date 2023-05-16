package david.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StreamDataEvent {

    @JsonProperty("userType")
    private String userType;                // the frontend client sends either CUSTOMER or STAFF

    @JsonProperty("username")
    private String username;                // the frontend client sends the currently logged-in user's info

    @JsonProperty("messageType")
    private String messageType;             // the frontend client sends either INIT-CHAT or MESSAGE

    @JsonProperty("message")
    private String message;

    @JsonProperty("topic")
    private String topic;                   // not useful anymore..??

}
