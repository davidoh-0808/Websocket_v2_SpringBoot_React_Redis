package david.backendchat.config;

import david.model.StreamDataEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;


@Configuration
public class RedisStreamConfig {


    @Value("server.servlet.application-display-name")
    private String applicationName;
    private StreamListener<String, ObjectRecord<String, StreamDataEvent>> streamListener;

    

    private StreamMessageListenerContainer<String, ObjectRecord<String, StreamDataEvent>> initListenerContainer() {

        return StreamMessageListenerContainer.create(redis);
    }


}
