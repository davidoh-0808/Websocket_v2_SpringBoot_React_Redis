package david.backendstock.service;

import david.model.StockUpdateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisStreamProducer {

    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    /*
        Record :
            key for stream,
            actual value
    */
    public void publishNewItemStatus(String streamKey, StockUpdateEvent event) {
        ObjectRecord<String, StockUpdateEvent> record =
                StreamRecords.newRecord().ofObject( event ).withStreamKey( streamKey );

        reactiveRedisTemplate.opsForStream().add( record ).subscribe();
    }

}
