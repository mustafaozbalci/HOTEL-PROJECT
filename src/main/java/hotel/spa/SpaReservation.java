package hotel.spa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "spa_reservation", schema = "hotel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpaReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spa_reservation_id")
    private Long spaReservationId;

    @Column(name ="Date")
    private Date date;

    @Column(name = "between_12_13", columnDefinition = "VARCHAR(255) DEFAULT 'available'")
    private String startTime12_13;

    @Column(name = "between_13_14", columnDefinition = "VARCHAR(255) DEFAULT 'available'")
    private String startTime13_14;

    @Column(name = "between_14_15", columnDefinition = "VARCHAR(255) DEFAULT 'available'")
    private String startTime14_15;


    // Add any other necessary fields or relationships

    // Getter and Setter methods
}
