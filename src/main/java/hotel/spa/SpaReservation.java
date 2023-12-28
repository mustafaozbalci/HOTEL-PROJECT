package hotel.spa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "spa_reservation", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpaReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_number")
    private int roomNumber;

    @Column(name = "request_date")
    private String message;

}
