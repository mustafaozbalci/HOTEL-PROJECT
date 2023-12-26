package hotel.stock;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stock", schema = "hotel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private int stockId;

    @Column(name = "productName")
    private String productName;

    @Column(name = "productType")
    private String productType;

    @Column(name = "currentStock")
    private int currentStock;
}