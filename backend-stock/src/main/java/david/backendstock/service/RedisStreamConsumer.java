package david.backendstock.service;

import david.model.ItemInquiryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisStreamConsumer implements StreamListener<String, ObjectRecord<String, ItemInquiryEvent>> {

    private final SimpMessagingTemplate simpMessagingTemplate;


    /**
     * finally, after the inquiry message is published into Redis Stream,
     * the redis stream is ready to relay the message to the websocket client
     * which will handle the message based on messageType and userType
     *
     * websocket topic   : itemInquiryEvent.getUsername(),"/private"  ->  /chatroom/inquiry/{username}/private
     * websocket message : itemInquiryEvent.getMessage()
     */
    @Override
    public void onMessage(ObjectRecord<String, ItemInquiryEvent> record) {
        /* topic                -> "/topic/to-frontend/new-item-status"
           newItemStatus        -> includes itemId, itemName, price, stockCount, itemStatus*/
        ItemInquiryEvent itemInquiryEvent = record.getValue();
        String messageType = itemInquiryEvent.getMessageType().name();
        String userType = itemInquiryEvent.getUserType().name();
        String username = itemInquiryEvent.getUsername();
        String topic = itemInquiryEvent.getTopic();
        String message = itemInquiryEvent.getMessage();

        switch (messageType) {
            case "INIT" -> handleInitMessage(userType, username, topic, message);
            case "MESSAGE" -> handlePrivateMessage(username, topic, message);
            default -> log.error(String.format("The inserted messageType [%s] does not belong to MessageType ENUM", messageType));
        }

        log.info( String.format("sent streamDataEvent to websocket client - topic[%s] , message[%s]", topic, message) );
    }

    private void handleInitMessage(String userType, String username, String topic, String message) {
        /*
            the init message occurs when the user (user or staff) enters the chatroom the first time
            so greet them with different messages
         */
        if (userType.equals("USER")) {
            // message must be null since it's the init message, aka "open the chatroom!"
            message = String.format("Welcome! You are UserType [%s] , Username [%s]", userType, username);
            simpMessagingTemplate.convertAndSendToUser(username, "private", message);

        } else if (userType.equals("STAFF")) {
            message = String.format("Welcome! You are UserType [%s] , Username [%s]", userType, username);
            simpMessagingTemplate.convertAndSendToUser(username, "private", message);

        } else {
            log.error(String.format( "The inserted userType [%s] does not belong to UserType Enum", userType) );
        }

    }

    private void handlePrivateMessage(String username, String topic, String message) {
        // finally, relay a regular message to the frontend client
        simpMessagingTemplate.convertAndSendToUser(username,"/private", message);
    }


}
