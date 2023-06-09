# Order Stock Status + Chat Application
</br>
Enhanced [Websocket_v1_SpringBoot_React](https://github.com/davidoh-0808/Websocket_v1_SpringBoot_React.git) by adding Redis PubSub and Redis Stream

</br>
</br>





## ROADMAP :
   1) [Websocket_v1_SpringBoot_React](https://github.com/davidoh-0808/Websocket_v1_SpringBoot_React.git)
   2) Websocket_v2_SpringBoot_React_Redis</br>
   3) [Websocket_v3_SpringBoot_React_Redis_Docker_ECS](https://github.com/davidoh-0808/Websocket_v3_SpringBoot_React_Redis_Docker_ECS.git)</br>
   4) [Websocket_v4_SpringBoot_React_Redis_MSA](https://github.com/davidoh-0808/Websocket_v4_SpringBoot_React_Redis_MSA.git)</br>

<br/>
<br/>





## TODO :
    + [Done] Why Redis?

    + [Done] The components of Websocket and Redis
    
    + [Done] A new user scenario
    
    + [DONE] Websocket endpoints for uniplex communication
                  1. ws endpoint
                  2. topic
                  
    + [planned] Websocket endpoints for duplex communication
                  1. ws endpoint (the one from uniplex communication)
                  2. app
                  3. topic
                  4. user specific endpoint
                  
    + [DONE] Redis PubSub & Streaming Channels, used in conjunction w/ websocket
                  PubSub
                  1. publishing channel
                    1-1. broadcast
                  2. subscribing 
                    1-1. broadcast
                 
                  Streaming
                  1. publishing channel
                    1-1. user specific 
                  2. subscribing channel
                    1-1. user specific
 
    + [DONE] Naming the Apis (Controllers)
    
    + [DONE] Naming the Services (including RedisServices
    
    + [DONE] switch to mvn -> gradle proj manager
    
    + [DONE] Coding Backend
    
    + [planned] Coding Frontend

    + [planned] Sequence Diagram 

<br/>
<br/>





## Web Architecture

    Server:   Spring Boot 
    
        Websocket, 
        
        STOMP Endpoint Registry, 
        
        Redis PubSub / Stream , 
        
        Postgres (planned),

        Docker & AWS ECR & AWS ECS (planned),
        
        AWS API Gateway & AWS Lambda (planned)

    Client:   ReactJS 
    
        SockJS -> open WebSocket, 
        
        STOMP client -> ws endpoint/topics and wrap/unwrap messages

<br/>
<br/>





## What is Websocket? (Recap)
<img src="https://user-images.githubusercontent.com/75977587/233829276-90c20029-8b37-4301-b739-4781ecf9d6db.png" width="700" height="400">
Websocket vs. TCP

    - WebSocket is a framed and bidirectional protocol. 
        On the contrary to this, HTTP is a unidirectional protocol functioning above the TCP protocol.
    
    - WebSocket is faster than TCP. 
        WebSocket allows uninterrupted pushing or transmitting data in the already open connection.
    
    - WebSocket uses a unified TCP connection and needs one party to terminate the connection.

<img src="https://user-images.githubusercontent.com/75977587/233829287-5fa64948-09ef-42d0-9496-cc90dcf08e91.png" width="700" height="400">
Types of Data allowed in websocket:

    - Data that can be serialized into a string or binary format, including
        - text,
        - images,
        - and multimedia

        all these in real-time
        

<br/>
<br/>






## <img src="https://user-images.githubusercontent.com/75977587/235294084-4393f6c8-4431-4279-86de-f315471750e2.png" width="100" height="100"> Why Redis?
### In short, multiplex communication among multiple instances of clients and servers"  

1) Without Redis, broadcasting a message from one websocket client to all the other clients is not possible.

   Why?
   Because in-memory Message Broker, "STOMP", is only local to the one client <--ws connection--> server connection.

   When multiple instances of frontend or browsers are present, one server cannot broadcast the messages published by the client via Websocket, to all the    other client instances connected to the socket.

> ie. Stock status of a shopping item for every customers needs access in real-time.

Stock Status w/o Redis<br/>
![websocket w_o Redis drawio](https://user-images.githubusercontent.com/75977587/235300506-6b271492-b877-445c-80dc-292a6660b37f.png)

Stock Status w/ Redis<br/>
![websocket w_redis drawio](https://user-images.githubusercontent.com/75977587/235300529-412b68c0-c1b9-4f1a-ac1e-0b1b78dd45d4.png)


<br/>

2) Without Redis, user-specific messages from each client will get lost after reaching the server (that is, if the server is made of microservices)

Again, in-memory Message Broker, "STOMP", is only local to the one client <--ws connection--> server connection.

If the server is in MSA enviornment and is a cluster of instances.  

There is no guarantee the websocket server will route client's private message back to its rightful destination.

> ie. An online shop user asks when an item will be back in stock via message chat.  
> The staff assistant replies, but the reply cannot reach its user.

User Inquiry w/o Redis<br/>
![websocket2 w_o Redis drawio](https://user-images.githubusercontent.com/75977587/235305389-c284172e-8d95-4ece-9b20-34913fdac1bd.png)

User Inquiry w/ Redis<br/>
![websocket2 w_Redis drawio](https://user-images.githubusercontent.com/75977587/235306253-a1525626-9028-4693-9f1c-d3ecb836b8f6.png)


<br/>
<br/>





## Components of Websocket and Redis

### ObjectMapper
Definition :
> Provided by Jackson Library, this class can perform two major tasks :
   1. wrap Java-oriented data into Websocket-friendly data (JSON) to broadcast the message.
   2. unwrap Websocket-friendly data (JSON) into Java-friendly object to read the message.

Related Code :
```
private final ObjectMapper mapper;

String data = mapper.writeValueAsString(socketData);

var data = mapper.readValue(message,WebSocketMessage.class);
```
<br/>
<br/>


### SimpMessagingTemplate
Definition :
> Provided by Spring as a high-level messaging interface, this class provides methods
   , such as .convertAndSend , .getUserDestinationPrefix , .convertAndSendToUser
   to allow simple message deliverance to Websocket endpoints.

Related Code :
```
private final SimpMessagingTemplate template;

// all users
template.convertAndSend("/topic/mytopic/", "message")

// to specific user
template.convertAndSendToUser("userId", "/topic/mytopic/", "message"));
```
<br/>
<br/>


### ReactiveRedisTemplate
Definition :
> This Redis class can operate and manipulate Redis data structures such as Set, Ordred Set, HashMap, and others.
   
   The operation is done so in a reactive, non-blocking way 
   (the next request-response patterns will begin, instead of waiting for the first response to complete)

   Redis serves as a global message holder which renders instance-local messages globally available for all instances.

Related Code :
```
// 1. send the message to Redis first, the global message holder, so, all websocket clients can share the identical messages.
@RequiredArgsConstructor
public class CustomWebSocketService {

    private final ReactiveRedisTemplate redisTemplate;
    private final ObjectMapper mapper;

    public void convertAndSend(String topic, Object message) {
        var socketData = new WebSocketMessage(topic, message);
        String data = mapper.writeValueAsString(socketData);
        redisTemplate.convertAndSend("<channel-name>", data);
    }

}

// 2. the global message (via Redis) can be consumed by the below code, using Spring's SimpMessagingTemplate
@RequiredArgsConstructor
public class RedisPubsubReceiver {

    private final SimpMessagingTemplate template;
    private final ObjectMapper mapper;

    public void receiveMessage(String message) {
        var data = mapper.readValue(message,WebSocketMessage.class);
        template.convertAndSend(data.getTopic(), data.getMessage());
    }

}
```
<br/>
<br/>
<br/>




## User Scenario w/ logic
### Online Vegan Store w/ only fresh items in Stock

#### List Item / Real-time Stock Update
---
1. The user connects to the landing page.
>The user is assigned UUID which will be printed on the page for clarification and used in websocket private message later.

2. The user can see a list of items.

3. The user can click on each item to see the details in modal.

>The redirection will initiate <u>the Websocket Connection for Stock Status (item count or OUT OF STOCK)</u>.
>Whenever a user buys an item, the item status (count) will change on all users connected to the list page.


#### Buy Item
---
4. The user can order items.
> Try using Eximbay PG for payment process .. :)


#### Real-time Stock Update (Uniplex via API)
---
5. A successful order sends a stock update api to the postgre DB 
   and triggers an one-directional API that updates the stock status for all the client users.

#### Chat w/ the staff (Duplex)
---
6. The item can be OUT OF STOCK.
>Buying an item will publish a stock update which is then displayed on the list page.
>     websocket pub endpoint -> Redis pub channel -> Redis sub channel -> websocket topic endpoint
>     
>An item of status, "OUT OF STOCK", cannot be purchased.
>Instead, a "talk-to-staff" button becomes available for the user to click

7. Click the "talk-to-staff" button to inquire about items.
>The user can initiate chat by clicking the btn.
>
>This opens a new websocket connection, apart from the broadcast STOCK STATUS.
>The connection is dedicated to using each 

8. The staff responds to the user's inquiry about RESTOCK schedule.  The user is now content with the online store :)


   ## TODO : Insert Diagram Here

<br/>
<br/>

## Sequence Diagram w/ WS endpointsAPIs (TODO)
   

<br/>
<br/>

                        


## Infra Architecture (Planned)


<br/>
<br/>






## Pages in action

![Insert a video]()

To start:


<br/>
<br/>






### Client
        - npm install (in the react-client folder)
        - npm start


<br/>
<br/>






### Server
        - TODO: use gradle


<br/>
<br/>






### Plan for the next Git Repo 
    +  Deploy via Docker, ECR, ECS (or K8), Service Registry 



<br/>
<br/>


