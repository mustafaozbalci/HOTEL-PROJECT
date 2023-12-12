package hotel.employee;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee", schema = "hotel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    // You might want to implement a role-based access control system
    // For simplicity, I'm using a single field here, but you can extend this based on your needs
    @Column(name = "role")
    private String role; // e.g., "ADMIN", "MANAGER", "EMPLOYEE"

    // Add any other necessary fields or relationships

}
