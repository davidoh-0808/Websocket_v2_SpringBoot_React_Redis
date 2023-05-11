package david.backendstock.controller;

import david.backendstock.model.BuyItemDto;
import david.backendstock.model.StockUpdateEvent;
import david.backendstock.service.ItemService;
import david.backendstock.service.RedisStreamProducer;
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

    /*
    + item update method (stock update via db)
    + update item status using RedisStreamService.publish
    */

    @PutMapping("/buy")
    public void buyItem(@RequestBody BuyItemDto buyItemDto) {

        StockUpdateEvent event = itemService.buyItem( buyItemDto );

        redisStreamProducer.publish( event );
    }


}
