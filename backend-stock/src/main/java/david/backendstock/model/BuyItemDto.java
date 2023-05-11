package david.backendstock.model;

import lombok.*;

@Getter
@Setter
@Builder
public class BuyItemDto {

    private long itemId;
    private int purhcaseCount;

}
