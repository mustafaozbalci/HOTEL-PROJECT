package hotel.Expenses;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "invoice", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "room_number")
    private int roomNumber;

    @Column(name = "order_description", length = 999999999)
    private String orderDescription;

    @Column(name = "price")
    private String price;

    @Column(name = "total_amount")
    private double totalAmount;

}