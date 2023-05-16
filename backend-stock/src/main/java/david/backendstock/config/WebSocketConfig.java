package david.backendstock.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // the frontend clients open STOMP websocket connection via uri, "/stomp/backend-stock"
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp/backend-stock").setAllowedOrigins("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // the frontend clients listen and receive stock update event via this URL prefix
        registry.enableSimpleBroker("/topic/backend-stock");
        // registry.setApplicationDestinationPrefixes("/app/backend-stock");
    }
}
