package david.backendstock.service;

import david.backendstock.model.ItemPurchaseDto;
import david.model.Item;

public interface ItemService {

    Item updateItemStatus(ItemPurchaseDto dto);

}
