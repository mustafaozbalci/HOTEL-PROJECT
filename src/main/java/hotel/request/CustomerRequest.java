package hotel.request;

import lombok.Data;

@Data
public class CustomerRequest {

    private String requestType;
    private String content;
    private Integer roomNumber;
    private String productName; // Yeni alan: Ürün adı
    private Integer quantity;   // Yeni alan: Miktar
}
