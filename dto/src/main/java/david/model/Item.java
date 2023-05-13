package david.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {

    private long itemId;
    private String itemName;
    private int price;
    private int stockCount;
    private ItemStatus itemStatus;

}
