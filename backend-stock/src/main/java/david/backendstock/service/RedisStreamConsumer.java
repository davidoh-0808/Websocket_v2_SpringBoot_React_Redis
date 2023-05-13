package david.backendstock.service;

import david.backendstock.model.StockUpdateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisStreamConsumer implements StreamListener<String, ObjectRecord<String, StockUpdateEvent>> {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void onMessage(ObjectRecord<String, StockUpdateEvent> record) {
        /* topic                -> "/topic/to-frontend/new-item-status"
           newItemStatus        -> includes itemId, itemName, price, stockCount, itemStatus*/
        String topic = record.getValue().getTopic();
        StockUpdateEvent message = record.getValue();

        // finally, send the new item status message to the frontend
        simpMessagingTemplate.convertAndSend( topic, message );
    }

}
