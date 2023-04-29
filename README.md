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
    + [*** planned ***] The components of Websocket and Redis
    
    + [*** planned ***] A new user scenario
    
    + [planned] Websocket endpoints for 
                  1. ws endpoint
                  2. topic
                  3. user specific endpoint
                  
    + [planned] Websocket-complimentary Redis PubSub Channels
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



## Sequence Diagram w/ WS endpoints and APIs (TODO)
   
   
    

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



## Websocket Principles

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





## ReactJS screen

![Chat screen](img/chat_screen.jpg "Chat screen")

To start:
    
### Client
        - npm install (in the react-client folder)
        - npm start
    
### Server
        - TODO: use gradle


### Plan for the next Git Repo 
    +  Deploy via Docker, ECR, ECS (or K8), Service Registry 





