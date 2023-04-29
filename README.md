# ChatApplication
</br>
Enhanced [Websocket_v1_SpringBoot_React](https://github.com/davidoh-0808/Websocket_v1_SpringBoot_React.git) by adding Redis PubSub and Redis Stream
</br>
</br>

## ROADMAP :
   1) [Websocket_v1_SpringBoot_React](https://github.com/davidoh-0808/Websocket_v1_SpringBoot_React.git)
   2) Websocket_v2_SpringBoot_React_Redis</br>
   3) [Websocket_v3_SpringBoot_React_Redis_Docker_ECS](https://github.com/davidoh-0808/Websocket_v3_SpringBoot_React_Redis_Docker_ECS.git)</br>
   4) [Websocket_v4_SpringBoot_React_Redis_MSA](https://github.com/davidoh-0808/Websocket_v4_SpringBoot_React_Redis_MSA.git)</br>


## TODO :
    + [*** planned ***] Why Redis?

    + [*** planned ***] The components of Websocket and Redis
    
    + [*** planned ***] A new user scenario
    
    + [planned] Websocket endpoints for uniplex communication
                  1. ws endpoint
                  2. topic
                  
    + [planned] Websocket endpoints for duplex communication
                  1. ws endpoint (the one from uniplex communication)
                  2. topic
                  3. user specific endpoint
                  
    + [planned] Redis PubSub Channels, used in conjunction w/ websocket
                  1. publishing channel
                    1-1. broadcast
                    1-2. user specific
                  2. subscribing channel
                    1-1. broadcast
                    1-2. user specific
                    
    + [planned] Naming the Apis (Controllers)
    
    + [planned] Naming the Services (including RedisService)
    
    + [planned] The sequence diagram
    
    + [planned] switch to mvn -> gradle proj manager
    
    + [planned] Actual Coding
    
    + [planned] Write out logic in words
    
    + [planned] 
    
    + [asdf]    
    + [asdf]    


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
        

## What is Websocket? (Recap)

---
![image](https://user-images.githubusercontent.com/75977587/233829276-90c20029-8b37-4301-b739-4781ecf9d6db.png)
Websocket vs. TCP

    - WebSocket is a framed and bidirectional protocol. 
        On the contrary to this, HTTP is a unidirectional protocol functioning above the TCP protocol.
    
    - WebSocket is faster than TCP. 
        WebSocket allows uninterrupted pushing or transmitting data in the already open connection.
    
    - WebSocket uses a unified TCP connection and needs one party to terminate the connection.

![image](https://user-images.githubusercontent.com/75977587/233829287-5fa64948-09ef-42d0-9496-cc90dcf08e91.png)
Types of Data allowed in websocket:

    - Data that can be serialized into a string or binary format, including
        - text,
        - images,
        - and multimedia

        all these in real-time
        

## Why Redis?
### "The message between publisher and subscriber must be MULTIPLEXED between all instances."  

1) In-memory Message Broker, "STOMP", is local to client <--ws connection--> server connection.
When multiple instances of frontend (browser), it cannot broadcast the messages published by the client via Websocket, to all other instances connected to the socket.
>> ie. Stock status of a shopping item which every customers needs access in real-time.

[Diagram Here]()

2) In-memory Message Broker, "STOMP", is local to client.  

Thus, when multiple instances of frontend (browser), it cannot relay the messages published by the client via Websocket, back to the publishing client instance.  

>> ie. 

[Diagram Here]()


## Websocket Components (w/ STOMP)
   asdfasdf


## Redis PubSub Components
   asdfasdf


## User Scenario
>>1.
   
>>2.
>>3.
>>4.
>>5.

   Insert Diagram Here


## Sequence Diagram w/ WS endpointsAPIs (TODO)
   
   
    

## Frontend and Backend Logic (TODO)

    **Frontend Logic (React)**
    -------------------------------------------------------------------------------------------
        1. 
        
        2.
        
        3.
        
        4.


    **Backend Logic (Spring Boot)**
    ----------------------------------------------------------------------------------------

        1.

        2.

        3.

        4.
            
            


## Infra Architecture (Planned)










## Pages in action

![Chat screen](img/chat_screen.jpg "Chat screen")

To start:
    
### Client
        - npm install (in the react-client folder)
        - npm start
    
### Server
        - TODO: use gradle


### Plan for the next Git Repo 
    +  Deploy via Docker, ECR, ECS (or K8), Service Registry 





