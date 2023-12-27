package hotel.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDataLoader implements CommandLineRunner {
    @Autowired
    EmployeeRepository employeeRepository;


    public void run(String... args) throws Exception {
        if (employeeRepository.findByUsername("admin") == null) {
            // Veritabanına default bir kayıt eklemek için
            Employee admin = new Employee();
            admin.setUsername("admin");
            admin.setPassword("admin"); // Şifreyi düz metin olarak ekliyorum, güvenlik için daha fazla önlem alınmalıdır.
            admin.setRole("ADMIN");

            employeeRepository.save(admin);
        }
    }

}
