package hotel.employee;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
@Data
public class EmployeeController {
    private final EmployeeRepository employeeRepository;


    //TO CHECK WHETHER THE USERNAME AND THE PASSWORD ARE IN DB
    @GetMapping("/check/{username}")
    public ResponseEntity<?> checkEmployee(@PathVariable String username, @RequestParam String password) {
        Employee employee = employeeRepository.findByUsername(username);

        if (employee != null) {
            if (Objects.equals(employee.getPassword(), password)) {
                return ResponseEntity.ok(employee);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("WRONG USERNAME OR PASSWORD");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EMPLOYEE DID NOT FOUND!");
        }
    }

}