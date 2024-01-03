package hotel.request;

import lombok.Data;

@Data
public class CustomerRequest {

    private String requestType;
    private String content;
    private Integer roomNumber;
    //  STOCK PART
    private String productName;
    private Integer quantity;
    // INVOICE PART
    private double price;
    private double totalAmount;
}
