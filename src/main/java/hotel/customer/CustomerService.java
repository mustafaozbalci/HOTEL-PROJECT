package hotel.customer;

import hotel.reservation.Reservation;
import hotel.reservation.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private RejectedCustomerRepository rejectedCustomerRepository;

    @Transactional
    public Customer saveCustomerWithReservation(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
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
            customerRepository.save(customer);
            return "Room number updated successfully";
        } else {
            throw new RuntimeException("Customer not found with id: " + customerId);
        }
    }

    public void rejectCustomer(Long customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            RejectedCustomer rejectedCustomer = new RejectedCustomer();
            rejectedCustomer.setCustomerName(customer.getCustomerName());
            rejectedCustomer.setCustomerSurname(customer.getCustomerSurname());
            rejectedCustomer.setCustomerTC(customer.getCustomerTC());
            rejectedCustomer.setRejectionDate(new Date());
            rejectedCustomerRepository.save(rejectedCustomer);
            customerRepository.delete(customer);
        } else {
            throw new RuntimeException("Customer not found with id: " + customerId);
        }
    }


}
