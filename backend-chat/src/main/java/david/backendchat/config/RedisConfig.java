package david.backendchat.config;

import david.model.ItemInquiryEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveKeyCommands;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.ReactiveStringCommands;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * The starting point for all Redis related config
 *      - LettuceConnectionFactory is set up to handle the connections to Redis Server
 */
@Configuration
public class RedisConfig {

    /**
     * bean that deals with Redis connection (single node or multiple nodes)
     *
     * switch to RedisClusterConfiguration if multiple Redis nodes, "servers", become required.
     */
    @Bean
    public LettuceConnectionFactory getRedisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("localhost", 6379);
        return new LettuceConnectionFactory(config);
    }


    /**
     * register reactiveRedisTemplate for Redis Pub/Sub services ("backendchat" services for stock status inquiry chat)
     *
     * configure what kind of data the Redis template will serialize and deserialize
     */
    @Bean
    public ReactiveRedisTemplate<String, ItemInquiryEvent> reactiveRedisTemplate() {

        StringRedisSerializer keySerializer = new StringRedisSerializer();

        Jackson2JsonRedisSerializer<ItemInquiryEvent> valueSerializer =
                            new Jackson2JsonRedisSerializer<>(ItemInquiryEvent.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, ItemInquiryEvent> builder =
                            RedisSerializationContext.newSerializationContext( keySerializer );

        RedisSerializationContext<String, ItemInquiryEvent> context = builder.value (valueSerializer ).build();

        return new ReactiveRedisTemplate<>(getRedisConnectionFactory(), context);
    }


    /**
     * allows some command operations on Redis data(key, value) via the data key
     */
    @Bean
    ReactiveKeyCommands keyCommands(ReactiveRedisConnectionFactory factory) {
        return factory.getReactiveConnection().keyCommands();
    }


    /**
     * allows some command operations on the value of Redis data(key, value)
     */
    @Bean
    ReactiveStringCommands stringCommands(ReactiveRedisConnectionFactory factory) {
        return factory.getReactiveConnection().stringCommands();
    }





}















