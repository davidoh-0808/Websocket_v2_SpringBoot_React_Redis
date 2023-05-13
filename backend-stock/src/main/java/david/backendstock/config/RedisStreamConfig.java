package david.backendstock.config;


import david.model.StreamDataEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;

import java.time.Duration;

/**
 * Redis Config for live stock status update.
 * This is an additional configuration to the "backendchat" config where the RedisStreamListenerContainer is already defined.
 * We need another redis stream subscription, just for the live stock status.
 *
 */
@Configuration
public class RedisStreamConfig {


    @Value("server.servlet.application-display-name")
    private String applicationName;

    /* ObjectRecord
       S : Stream key
       V : Object Record can be a simple String value or custom object
     */
    private StreamListener<String, ObjectRecord<String, StreamDataEvent>> streamListener;


    @Bean
    public LettuceConnectionFactory getRedisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("localhost", 6379);
        return new LettuceConnectionFactory(config);
    }


    @Bean
    public Subscription subscription( /* TODO: check if LettuceConnectionFactory is alright?? */ ) {
        // configure options ( timeout when messages are blocked , what type of message the listener awaits )
        var options = StreamMessageListenerContainer
                .StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ofSeconds(1))
                .targetType(StreamDataEvent.class)
                .build();

        /* generate a listener w/ the redis factory and the options
        use getRedisConnecitonFactory() to open connection upon subscription */
        var listenerContainer =
                StreamMessageListenerContainer.create( getRedisConnectionFactory(), options );

        // when the listener receives the message, the redis stream automatically removes (acknowledge) the message
        var subscription = listenerContainer.receiveAutoAck(
                Consumer.from("GROUP_REALTIME_ITEM_STATUS", applicationName),
                /* the stream key (or id) the controller uses to broadcast the real time item status */
                StreamOffset.create("STREAM_PUBLISH_ITEM_STATUS", ReadOffset.lastConsumed()), streamListener
        );

        listenerContainer.start();
        return subscription;
    }

}
