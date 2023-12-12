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
        // Burada customer kaydedilir ve customerId elde edilir
        Customer savedCustomer = customerRepository.save(customer);

        // Reservation kaydedilir ve customer ile ilişkilendirilir
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
}