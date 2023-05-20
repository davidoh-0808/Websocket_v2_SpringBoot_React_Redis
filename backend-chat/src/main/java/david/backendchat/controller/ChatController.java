package david.backendchat.controller;

import david.backendchat.service.RedisStreamProducer;
import david.model.ItemInquiryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping
@RestController
public class ChatController {

    private final RedisStreamProducer redisStreamProducer;

    @MessageMapping("/chatroom/inquiry")     // the client invocation of app/chatroom/inquiry
    public void receiveSocketMessage(@Payload ItemInquiryEvent itemInquiryEvent){

        // publish the streaming message into Redis PubSub Server -> the message is "globalized"
        // (so the websocket connection at the receiving end can receive the message)
        redisStreamProducer.publishEvent( "TEST_REDIS_STREAM_KEY", itemInquiryEvent );

    }

}













