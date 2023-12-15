package hotel.customer;

import hotel.reservation.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long customerId) {
        Optional<Customer> customerOptional = customerService.getCustomerById(customerId);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{customerId}/reservations")
    public ResponseEntity<List<Reservation>> getReservationsByCustomerId(@PathVariable Long customerId) {
        List<Reservation> reservations = customerService.getReservationsByCustomerId(customerId);

        if (!reservations.isEmpty()) {
            return ResponseEntity.ok(reservations);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomerWithReservation(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.saveCustomerWithReservation(customer);
        return ResponseEntity.ok(savedCustomer);
    }
}
