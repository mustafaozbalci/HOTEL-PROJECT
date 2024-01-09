package hotel.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class CustomerDataLoader implements CommandLineRunner {
    @Autowired
    CustomerRepository customerRepository;


    //ADD DEFAULT CUSTOMERS
    public void run(String... args) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (customerRepository.count() > 0) {
            return;
        } else {
            Customer customer = new Customer();
            customer.setCustomerName("Mahmut");
            customer.setCustomerSurname("Tuncer");
            customer.setRoomNumber("101");
            customer.setBirthDate(dateFormat.parse("1971-01-01"));
            customer.setSex("Male");
            customer.setEntryDate(dateFormat.parse("2024-01-02"));
            customer.setExitDate(dateFormat.parse("2024-01-09"));
            customer.setCustomerTC("1234567890");
            customer.setEmail("mahmut.tuncer@example.com");
            customer.setPhoneNumber("5555555555");

            Customer customer2 = new Customer();
            customer2.setCustomerName("Murat");
            customer2.setCustomerSurname("Orhun");
            customer2.setRoomNumber("202");
            customer2.setBirthDate(dateFormat.parse("1983-03-23"));
            customer2.setSex("Male");
            customer2.setEntryDate(dateFormat.parse("2024-01-02"));
            customer2.setExitDate(dateFormat.parse("2024-01-09"));
            customer2.setCustomerTC("9876543210");
            customer2.setEmail("murat.orhun@example.com");
            customer2.setPhoneNumber("7777777777");

            Customer customer3 = new Customer();
            customer3.setCustomerName("Hadise");
            customer3.setCustomerSurname("Açıkgoz");
            customer3.setRoomNumber("303");
            customer3.setBirthDate(dateFormat.parse("1985-12-22"));
            customer3.setSex("Female");
            customer3.setEntryDate(dateFormat.parse("2024-01-02"));
            customer3.setExitDate(dateFormat.parse("2024-01-09"));
            customer3.setCustomerTC("5555555555");
            customer3.setEmail("hadise@example.com");
            customer3.setPhoneNumber("9999999999");
            customerRepository.save(customer);
            customerRepository.save(customer2);
            customerRepository.save(customer3);
        }

    }

}
