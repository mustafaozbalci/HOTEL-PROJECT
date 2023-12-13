package hotel.customer;

import hotel.request.CommunicationService;
import hotel.request.CustomerRequest;
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
    @Autowired
    private CommunicationService communicationService;

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

    @GetMapping("/notifyEmployee")
    public String notifyEmployee() {
        // Simulate a customer clicking a button
        CustomerRequest request = new CustomerRequest();
        request.setRequestType("RESERVATION");
        request.setContent("Reservation details"); // You can set relevant content

        communicationService.processCustomerRequest(request);

        return "notificationSuccess"; // Redirect to a success page or handle accordingly
    }

    @PostMapping("/request")
    public ResponseEntity<String> processRequest(@RequestBody CustomerRequest request) {
        // İstek tipine göre bildirim gönderme işlemini gerçekleştir
        if ("spa".equals(request.getRequestType())) {
            communicationService.sendSpaNotification(request.getContent());
        } else if ("extraPerson".equals(request.getRequestType())) {
            communicationService.sendExtraPersonNotification(request.getContent());
        }
        // Diğer istek tiplerini kontrol et...

        return ResponseEntity.ok("Request processed successfully");
    }
}
