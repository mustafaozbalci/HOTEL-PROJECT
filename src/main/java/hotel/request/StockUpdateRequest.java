package hotel.request;

import lombok.Data;

@Data
public class StockUpdateRequest {
    private String productName;
    private int quantity;
}
