package hotel.customer;

import hotel.reservation.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private RejectedCustomerRepository rejectedCustomerRepository;

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();

        if (!customers.isEmpty()) {
            return ResponseEntity.ok(customers);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/waitingReservation")
    public ResponseEntity<List<Customer>> waitingCustomers() {
        List<Customer> customers = customerService.getAllCustomers();

        List<Customer> filteredCustomers = customers.stream()
                .filter(customer -> customer.getRoomNumber() == 0)
                .collect(Collectors.toList());

        if (!filteredCustomers.isEmpty()) {
            return ResponseEntity.ok(filteredCustomers);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> createCustomerWithReservation(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.saveCustomerWithReservation(customer);
        return ResponseEntity.ok(savedCustomer);
    }

    @GetMapping("/check/{customerTC}")
    public ResponseEntity<?> checkCustomerByTC(@PathVariable String customerTC) {
        Customer customer = customerService.getCustomerByTC(customerTC);

        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Müşteri bulunamadı. TC Kimlik No kontrol ediniz.");
        }
    }

    @PutMapping("/updateRoomNumber/{customerId}")
    public ResponseEntity<String> updateRoomNumber(
            @PathVariable Long customerId,
            @RequestParam String newRoomNumber
    ) {
        try {
            customerService.updateRoomNumber(customerId, newRoomNumber);
            return new ResponseEntity<>("Room number updated successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteCustomer/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long customerId) {
        try {

            customerService.rejectCustomer(customerId);
            return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/rejected")
    public ResponseEntity<List<RejectedCustomer>> getRejectedCustomers() {
        List<RejectedCustomer> rejectedCustomers = rejectedCustomerRepository.findAll();

        if (!rejectedCustomers.isEmpty()) {
            return ResponseEntity.ok(rejectedCustomers);
        } else {
            return ResponseEntity.noContent().build();
        }
    }


}
