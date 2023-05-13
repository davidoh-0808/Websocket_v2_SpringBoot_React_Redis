package david.backendstock.repo;

import david.backendstock.model.ItemPurchaseDto;
import david.model.Item;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemMapper {

    int updateItemStatus(ItemPurchaseDto dto);
    Item getNewItemStatus(ItemPurchaseDto dto);

}
