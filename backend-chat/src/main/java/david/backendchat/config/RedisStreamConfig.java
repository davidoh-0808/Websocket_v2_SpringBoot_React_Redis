package david.backendchat.config;

import david.model.ItemInquiryEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;

import java.time.Duration;


@Configuration
public class RedisStreamConfig {

    //TODO: CHECK how does it get injected..??
    private StreamListener<String, ObjectRecord<String, ItemInquiryEvent>> streamListener;


    @Bean
    public LettuceConnectionFactory getRedisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("localhost", 6379);
        return new LettuceConnectionFactory(config);
    }


    private StreamMessageListenerContainer<String, ObjectRecord<String, ItemInquiryEvent>> initListenerContainer() {
        var options =
            StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ofSeconds(1))
                .targetType(ItemInquiryEvent.class)
                .build();

        return StreamMessageListenerContainer.create( getRedisConnectionFactory(), options );
    }


    @Bean("TestSubcriptionForItemInquiryEvent")
    public Subscription subscription(RedisConnectionFactory redisConnectionFactory) {
        var listenerContainer = initListenerContainer();

        var subscription = listenerContainer.receive(
                StreamOffset.latest("TEST_SUBSCRIPTION_FOR_ITEM_INQUIRY_EVENT"),
                /*TODO: CHECK does it get injected..?? */ streamListener);

        listenerContainer.start();
        return subscription;
    }



}
