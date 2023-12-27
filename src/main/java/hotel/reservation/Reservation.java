package hotel.reservation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hotel.customer.Customer;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "reservation", schema = "public")
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id", insertable = false, updatable = false)
    private Long customerId;

    @Column(name = "room_number")
    private int roomNumber;

    @Column(name = "check_in_date")
    private Date checkInDate;

    @Column(name = "check_out_date")
    private Date checkOutDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;
}