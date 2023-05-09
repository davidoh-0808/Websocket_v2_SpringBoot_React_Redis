package david.backendchat.config;

import david.backendchat.model.StockUpdateEvent;
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


@Configuration
public class RedisConfig {

    /**
     * bean that deals with Redis connection (single node or multiple nodes)
     *
     * switch to RedisClusterConfiguration if multiple Redis nodes, "servers", become required.
     */
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("localhost", 6379);
        return new LettuceConnectionFactory(config);
    }


    /**
     * register reactiveRedisTemplate for Redis Services (pub, sub, stream producer, stream consumer
     *
     * configure what kind of data the Redis template will serialize and deserialize
     */
    @Bean
    ReactiveRedisTemplate<String, StockUpdateEvent> reactiveRedisTemplate(LettuceConnectionFactory factory) {
        /* 1 */
        StringRedisSerializer keySerializer = new StringRedisSerializer();

        /* 2 */
        Jackson2JsonRedisSerializer<StockUpdateEvent> valueSerializer =
                            new Jackson2JsonRedisSerializer<>(StockUpdateEvent.class);

        /* 3 */
        RedisSerializationContext.RedisSerializationContextBuilder<String, StockUpdateEvent> builder =
                                                                                /* 1 */
                            RedisSerializationContext.newSerializationContext(keySerializer);

                                                                      /* 3 */            /* 2 */
        RedisSerializationContext<String, StockUpdateEvent> context = builder.value(valueSerializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
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















