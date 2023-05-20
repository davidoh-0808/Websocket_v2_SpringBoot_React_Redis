package david.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemInquiryEvent {

    @JsonProperty("userType")
    private UserType userType;                // the frontend client sends either CUSTOMER or STAFF

    @JsonProperty("username")
    private String username;                // the frontend client sends the currently logged-in user's info

    @JsonProperty("messageType")
    private MessageType messageType;             // the frontend client sends either INIT or MESSAGE

    @JsonProperty("message")
    private String message;

    @JsonProperty("topic")
    private String topic;

}
