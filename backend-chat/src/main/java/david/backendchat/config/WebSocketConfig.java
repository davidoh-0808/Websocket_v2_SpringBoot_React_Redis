package david.backendchat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp/backend-chat").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // URL prefix the frontend clients use to send message
        registry.setApplicationDestinationPrefixes("/app/chatroom/inquiry");

        // URL prefix for message topic the frontend clients listen to and receive messages coming from the backend
        registry.enableSimpleBroker("/chatroom/inquiry");

        /* User specific prefixes (helps redirect messages to either customer user or staff user)
           ie. the frontend client will listen to this URL : /chatroom/user/{username} */
        registry.setUserDestinationPrefix("/chatroom/inquiry");


    }

}
