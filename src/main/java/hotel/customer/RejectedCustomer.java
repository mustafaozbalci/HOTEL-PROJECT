package hotel.customer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Table(name = "rejected_customers")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class RejectedCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rejected_customer_id")
    private Long rejected_id;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_surname")
    private String customerSurname;

    @Column(name = "customer_tc")
    private String customerTC;

    @Column(name = "rejection_date")
    private Date rejectionDate;
}
