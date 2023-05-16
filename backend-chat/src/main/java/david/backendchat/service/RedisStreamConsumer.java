package david.backendchat.service;

import david.model.StreamDataEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisStreamConsumer implements StreamListener<String, ObjectRecord<String, StreamDataEvent>> {

    private final SimpMessagingTemplate simpMessagingTemplate;


    @Override
    public void onMessage(ObjectRecord<String, StreamDataEvent> record) {
        /* topic                -> "/topic/to-frontend/new-item-status"
           newItemStatus        -> includes itemId, itemName, price, stockCount, itemStatus*/
        String topic = record.getValue().getTopic();
        StreamDataEvent event = record.getValue();

        

    }
}
