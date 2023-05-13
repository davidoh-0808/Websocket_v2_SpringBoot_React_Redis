package david.backendstock.service;

import david.backendstock.model.ItemPurchaseDto;
import david.backendstock.repo.ItemMapper;
import david.model.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemMapper itemMapper;

    @Transactional
    @Override
    public Item updateItemStatus(ItemPurchaseDto dto) {
        itemMapper.updateItemStatus(dto);

        return itemMapper.getNewItemStatus(dto);
    }


}
