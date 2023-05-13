package david.backendstock.model;

import lombok.*;

@Getter
@Setter
@Builder
public class ItemPurchaseDto {

    private long itemId;
    private String itemName;
    private int purhcaseCount;
    private int stockCount;

}
