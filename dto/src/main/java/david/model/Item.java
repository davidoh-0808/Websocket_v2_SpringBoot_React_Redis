package david.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {

    private long id;
    private String name;
    private int price;
    private int stockCount;
    private ItemStatus itemStatus;

}
