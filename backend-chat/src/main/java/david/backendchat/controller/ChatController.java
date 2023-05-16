package david.backendchat.controller;

import david.backendchat.service.RedisStreamProducer;
import david.model.StreamDataEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping
@RestController
public class ChatController {

    private final RedisStreamProducer redisStreamProducer;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chatroom/inquiry")     // the client invocation of app/chatroom/inquiry
    public StreamDataEvent recMessage(@Payload StreamDataEvent streamDataEvent){

        //TODO : START HERE ..
        // is RedisStreamProducer applicable here???
        StreamDataEvent streamDataEvent = redisStreamProducer.publishEvent();
        simpMessagingTemplate.convertAndSendToUser(streamDataEvent.getUsername(),"/private", streamDataEvent.getMessage());

        System.out.println(streamDataEvent);

        return streamDataEvent;
    }

}













