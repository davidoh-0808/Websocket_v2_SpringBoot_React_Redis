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

When multiple instances of frontend or browsers are present, one server cannot broadcast the messages published by the client via Websocket, to all the other client instances connected to the socket.

> ie. Stock status of a shopping item for every customers needs access in real-time.

[Stock Status w/o Redis](https://viewer.diagrams.net/?tags=%7B%7D&highlight=0000ff&edit=_blank&layers=1&nav=1&title=websocket%20w%2Fo%20Redis#R7VrbctowEP0az6QPZHwHHrmk6S3TTMlM0kdhK1iNsagsAvTrK9mSbzLgQAik5QlrJa3lPWfPSho0azBdXhMwC26wD0PN1P2lZg010zR012I%2F3LJKLY5tp4YJQb4YlBtG6A%2BUM4V1jnwYlwZSjEOKZmWjh6MIerRkA4TgRXnYIw7Lb52BCVQMIw%2BEqvUe%2BTRIrR1Hz%2B2fIJoE8s2GLnqmQA4WhjgAPl4UTNaVZg0IxjR9mi4HMOTBk3FJ531c05stjMCINplwZT4vB4tfX37c37m9of%2B5b3793BLoPINwLj74I8ERbV1Fvma6IXPcHxO%2BeLoSEXF%2Fz3FixsSHpOXhEBPN6iVOyEWrVbR%2FYOPyGexpwn8vBiHiazb1PscHsmFpgLJ3mATPIx%2FyhRtswiJAFI5mwOO9C8YzZgvoNBTdaiBEbJ4hoXBZMInAXEM8hZSs2BDRK%2BFcSfKJ9iKH3JA4BgW4XWEDgmWTzHMOBHsQWLwAl%2FYZlzpcusfGpXPGpQYXs3NsXLoKLvdwPMLeE6RFXNKADtJagXCkBJK9ilUW%2BCZBtMxTUx1DLQdq9PrAe6ryPe0phFwfQcIioY5Zb3nECcfVzOEdrTjZGfC0MfTZsjZPRnffb25V%2FyM0nTGPpn4D45hX%2BySPnvLFsVCl75brOXJyuVaFF0ZDXmRbjdcnhppfaupEfo9vuFgrwhFPIR%2FEQRa3QozygOqsBZeIPoge%2FvyT2y8d0RouC8OGK9mI2Fc9FBuFWbyZT0tact5adGI8Jx7cXpApIBNIN4yzhUPol%2FaVKtgFMJ0aLKWNwBBQ9FzejdbhK95wi1GSRpJp3YpS2xWKpB8uZhV3jxVHZrtCynbFURoZxVFCt%2Byzd2egVcdAkeo7K0lnjZI8rJWGzDxeKxcc%2BW9gzM5BJdKDEE0i9uwx6jHtsfpcEBA7aPRExxT5PvfRJ5AtEYwTf5y0Mx7TJMpOX3OGtTTemLXibCRcaplMNJck%2FVJ3THs%2FRsoh%2BPExhgfhSPZd%2F61KdZqqlHlaKlURF0vfVaU6FbmrVsQDq5QkXI1K7S1Kitw91MtRs03N6aiUhGh%2FlWoxmXItp1ynTl603BeJlheCOEbe%2B5cqq6FUdRsq1b4SVFEOw%2BxeOq8jQjWuDi1D6vWRwqk4ADP%2BOBWnohKh8JyGKIKD7FK1HuvNhN6et4XqUneeMfbccUhZMMp4yL38G%2BS2el20b25neXqps%2FCUclXvdrZk687KcAsJYsHgFWHPtO82THt5FXHovLerV1nOjlsPp1MuPNZbbz0aHNEPl%2FOS6OeclxcA7%2Fsq8ugX7fKE8q7DePx78bb9ogr0Dx6JZT6%2BXsV5mzOx7ZTrCcvJXTekrr7V1YGLU9upoeH59m5jTZWp%2B7%2Fc3rVfdhB%2Bf1J1KsJiVy%2FbdhcWa6urQwtL3Un3LCybhcX9V4RF4%2F%2B%2BkP%2BfSofn%2F0Kzrv4C)

[Stock Status w/ Redis](https://viewer.diagrams.net/?tags=%7B%7D&highlight=0000ff&edit=_blank&layers=1&nav=1&title=websocket%20w%2Fredis#R7Vpdc5s4FP01nuk%2B0OFL2H5M7DTtTtNm1jvT7FMHg2wzwYgVcmzvr18JSXyJFIKxSTp5Mlykizj3nKurOx5Zs%2B3hFrvx5g75MByZun8YWfORaRq6Y9EfZjlyC7BtbljjwBeDcsMi%2BA%2FKmcK6C3yYlAYShEISxGWjh6IIeqRkczFG%2B%2FKwFQrLb43dNVQMC88NVeuPwCcbbp0APbd%2FhsF6I99s6OLJ1pWDhSHZuD7aF0zWzciaYYQIv9oeZjBk4Elc%2BLxPzzzNFoZhRNpMeNjcO1eHP79dL1cPh5%2BzL7EFbzUbcDdPbrgTX%2FwJo4hoN5E%2FMp2Qer5eYrZ6chSQOP%2FuUGpG2IdY81CI8Mi6Sp3gD5pWtP9Bx%2BUz6NWa%2FX6YhQFbtKlfswBBOowjlL3DxGgX%2BZCt3KAT9puAwEXseuzpnhKN2jZkG4rHKhICnCeICTwUTAKZW4i2kOAjHSKejkWQBEtt4WCfh9yQcdwUwu0ImytYts4c54GgFyIWL4mL8x6XmrgY1uCBGb8HpiYwmYfhAjNRAvMDLhfIe4SkGBiO6IxvFwGKFCTpu%2BjmAi%2BComW%2FtrwzVVBUwbt2vccq3%2FmTAuL6AmIKhDrmecsKpRxXlcMeaElaGzDZGHp8qNXJ4u%2Fvd%2Feq%2F0WwjalHU7%2BDScL2%2B1RHj%2FniKFT83XI9A4vLAWVagJa0yGqN3nkhF1TgxV%2FQDxIFKlrlxOzSO4YBxQxbzYAtObpfl5mB8mudYv59R6gbKOycAXMD0GuMiJvK15rr%2FYAOzOa9xqkBPav4%2BgfdUEBXs1XkX7Eyl0EeukkSeGVwc%2BoymOAhIA%2FiCbv%2Bh9k%2FAnE3PxSGzY%2FyJqLf8lC8Kcxit%2Fm09O7YFJIE7bAHWxSlxMVrSFrkfOiXing1xBiGlC1P5Zq%2BLl5i6j0K0lQkqGHqZWqM2deXfPBvEtOKRXjFk9XoiX%2B04iklUPZFJ3DKbOaU1PBWpMsSoRCX5Cw7b9XH%2BteEbhZlQXR1mc4Qp8gTA6sZ5XBo47IHtFol8DxxsHrXdqbTjzqFp6RVfTppUGvnzHAPcUDBoJvpybKftJX99DKyt6unj2nFRVvVOzooOTKdiqNzi94eUvSS6e%2BiB2q3RYmDt6Nlsyw1O2YAo6edur8NuBBZUBNZaTsxskoJN%2B4o2Mm0sk230yuNlXssDIvZgOQF69WNCu24x35JqLaW3t5BefhGEFAbQW8PxuHbNkBt27ztlCjl9VpT4qRjSrQnDbn1XDnRuUROrGt79XLSltdt6ulnSddcOretiIchnal3JB2oku7ChbOjtr16zU41JDGepYhmgBMyGnPT31lNEqU5HY5fFzOlUiQzp9OurRx7WiW52bKX8%2BKUqFcWDeSiz5oUpdaGTYq97%2BDN3HZacttuu9X33H7snE4t0ODo3OnUbGbU2foQTvtq%2BXfvQzjnaz5ervXQdmM5MUrKkQl0FJ%2BiYnDp3r8zZBvQeW8DZlC0aAO%2BevmBYeTXtaNnGg3n4HNrT%2B26XVB7knC%2Fs%2FZG7I9Y8s%2BUfHj%2Bl1Tr5n8%3D)

2) Without Redis, user-specific messages from each client will get lost after reaching the server (that is, if the server is made of microservices)

Again, in-memory Message Broker, "STOMP", is only local to the one client <--ws connection--> server connection.

If the server is in MSA enviornment and is a cluster of instances.  

There is no guarantee the websocket server will route client's private message back to its rightful destination.

> ie. An online shop user asks when an item will be back in stock via message chat.  The staff assistant replies, but the reply cannot reach its user.

[Diagram Here]()

<br/>
<br/>





## Websocket Components (w/ STOMP)
   asdfasdf


<br/>
<br/>





## Redis PubSub Components
   asdfasdf

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

#### Real-time Stock Update
(Uniplex communication via API)
5. A successful order sends a stock update api to the postgre DB 
   and triggers an one-directional API that updates the stock status for all the client users.

#### Chat w/ the staff 
(Duplex communication)
6. The item can be OUT OF STOCK.
>Buying an item will publish a stock update which is then displayed on the list page.
>     websocket pub endpoint -> Redis pub channel -> Redis sub channel -> websocket topic endpoint
>     
>An item of status, "OUT OF STOCK", cannot be purchased.
>Instead, a "talk-to-staff" button becomes available for the user to click

6. Click the "talk-to-staff" button to inquire about items.
>The user can initiate chat by clicking the btn.
>
>This opens a new websocket connection, apart from the broadcast STOCK STATUS.
>The connection is dedicated to using each 

7. The staff responds to the user's inquiry about RESTOCK schedule.  The user is now content with the online store :)


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


