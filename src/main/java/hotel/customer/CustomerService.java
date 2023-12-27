package hotel.customer;

import hotel.reservation.Reservation;
import hotel.reservation.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ReservationService reservationService;

    @Transactional
    public Customer saveCustomerWithReservation(Customer customer) {
        // Müşteriyi kaydet ve customerId'yi elde et
        Customer savedCustomer = customerRepository.save(customer);

        // Rezervasyonu kaydet ve müşteri ile ilişkilendir
        Reservation reservation = new Reservation();
        reservation.setCustomer(savedCustomer);
        reservation.setRoomNumber(customer.getRoomNumber());
        reservation.setCheckInDate(customer.getEntryDate());
        reservation.setCheckOutDate(customer.getExitDate());
        reservationService.saveReservation(reservation);

        return savedCustomer;
    }

    public Optional<Customer> getCustomerById(Long customerId) {
        return customerRepository.findById(customerId);
    }

    public List<Reservation> getReservationsByCustomerId(Long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        return customerOptional.map(Customer::getReservations).orElse(List.of());
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerByTC(String customerTC) {
        return customerRepository.findByCustomerTC(customerTC);
    }

    public String updateRoomNumber(Long customerId, String newRoomNumber) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setRoomNumber(newRoomNumber);
            customerRepository.save(customer); // Save the updated customer
            return "Room number updated successfully";
        } else {
            // You can customize the error message based on your application's requirements
            throw new RuntimeException("Customer not found with id: " + customerId);
        }
    }

}
