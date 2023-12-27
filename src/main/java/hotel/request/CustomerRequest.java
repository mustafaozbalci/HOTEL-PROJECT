package hotel.request;

import lombok.Data;

@Data
public class CustomerRequest {

    private String requestType;
    private String content;
    private Integer roomNumber;
//  STOCK PART
    private String productName; // Yeni alan: Ürün adı
    private Integer quantity;   // Yeni alan: Miktar
// INVOICE PART
    private double price;
    private double totalAmount;
}
