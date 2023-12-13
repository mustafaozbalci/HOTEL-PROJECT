package hotel.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hotel.employee.Notification;
import hotel.reservation.Reservation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customer", schema = "hotel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "customer_name")
    private String customerName;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Reservation> reservations;


    @Column(name = "customer_surname")
    private String customerSurname;

    @Column(name = "room_number")
    private int roomNumber;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "sex")
    private String sex;

    @Column(name = "entry_date")
    private Date entryDate;

    @Column(name = "exit_date")
    private Date exitDate;

    @Column(name = "check_id")
    private int checkId;

    @Column(name = "customer_TC")
    private String customerTC;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Notification> notifications;

    public void addNotification(Notification notification) {
        if (notifications == null) {
            notifications = new ArrayList<>();
        }
        notifications.add(notification);
    }
}
