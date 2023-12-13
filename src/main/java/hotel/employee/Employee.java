package hotel.employee;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employee", schema = "hotel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role; // e.g., "ADMIN", "RECEPTION", "SPA"
    @Column(name = "inbox")
    private String inbox;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Notification> notifications;

    public void addNotification(Notification notification) {
        if (notifications == null) {
            notifications = new ArrayList<>();
        }
        notifications.add(notification);
    }

}
