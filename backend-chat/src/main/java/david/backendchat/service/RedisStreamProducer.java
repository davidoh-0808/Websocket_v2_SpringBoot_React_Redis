package david.backendchat.service;

import david.model.ItemInquiryEvent;
import david.model.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisStreamProducer {
    @Value("${server.servlet.application-display-name}")
    private String applicationName;

    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;


    /*
        before handing the message to the receiving websocket client,
        send the chat message to Redis PubSub ("globalize")
    */
    public void publishEvent(String streamTopic, ItemInquiryEvent itemInquiryEvent) {

        /* convert the event (userType, username, messageType, message)
            into Redis Stream format, ObjectRecord<stream topic, value object> */
        ObjectRecord<String, ItemInquiryEvent> record =
                StreamRecords.newRecord().ofObject( itemInquiryEvent ).withStreamKey( streamTopic );

        reactiveRedisTemplate.opsForStream().add( record ).subscribe();
    }


    @Scheduled(initialDelay = 10000, fixedRate = 5000)
    private void testPublishTestMessageToBackend() {

        ItemInquiryEvent itemInquiryEvent = ItemInquiryEvent.builder()
                                                .topic("TEST TOPIC")
                                                .messageType(MessageType.MESSAGE)
                                                .message( String.format("New Message from [%s]", applicationName) )
                                                .build();

        publishEvent( "TEST_EVENT_TO_BACKEND",  itemInquiryEvent);

        log.info(String.format("Published Message: [%s] to Stream: TEST_REDIS_STREAM_KEY", itemInquiryEvent));

    }


}
