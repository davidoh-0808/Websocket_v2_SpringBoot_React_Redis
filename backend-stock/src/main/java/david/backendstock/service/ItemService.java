package david.backendstock.service;

import david.backendstock.model.BuyItemDto;
import david.backendstock.model.StockUpdateEvent;

public interface ItemService {

    StockUpdateEvent buyItem(BuyItemDto dto);

}
