package david.backendstock.controller;

import david.backendstock.model.ItemPurchaseDto;
import david.backendstock.model.StockUpdateEvent;
import david.backendstock.service.ItemService;
import david.backendstock.service.RedisStreamProducer;
import david.model.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/item")
@RestController
public class ItemController {

    private final ItemService itemService;
    private final RedisStreamProducer redisStreamProducer;

    /* the topic configured from WebSocketConfig : "/topic/backend-stock/"
       the client will use the uri below whenever stock update is available */
    private final String WEBSOCKET_TOPIC = "/topic/backend-stock/new-item-status";
    private final String STREAM_KEY = "STREAM_KEY_PUBLISH_NEW_ITEM_STATUS";

    /*
    + item update method (stock update via db)
    + update item status using RedisStreamService.publish
    */

    @PutMapping("/purchase")
    public void handleItemPurchase(@RequestBody ItemPurchaseDto dto) {

        Item newItemStatus = itemService.updateItemStatus( dto );
        StockUpdateEvent stockUpdateEvent = StockUpdateEvent.builder()
                .topic( WEBSOCKET_TOPIC )
                .message( newItemStatus )
                .build();

        redisStreamProducer.publishNewItemStatus(
                STREAM_KEY,
                stockUpdateEvent
        );
    }


}
