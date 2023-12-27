package hotel.request;

import lombok.Data;

@Data
public class InvoiceRequest {
    private int roomNumber;
    private double price;
    private double totalAmount;
    private String orderDescription;


}
