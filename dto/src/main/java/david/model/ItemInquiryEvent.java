package david.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemInquiryEvent {

    @JsonProperty("message")
    private String message;

    @JsonProperty("topic")
    private String topic;

}
