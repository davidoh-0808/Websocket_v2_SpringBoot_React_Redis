package david.model;

public enum ItemStatus {

    INSTOCK("INSTOCK"),
    OUTOFSTOCK("OUTOFSTOCK");

    public final String itemStatus;

    private ItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

}
