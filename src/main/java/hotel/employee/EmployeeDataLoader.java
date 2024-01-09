package hotel.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDataLoader implements CommandLineRunner {
    @Autowired
    EmployeeRepository employeeRepository;


    //TO ADD AN EMPLOYEE AS DEFAULT SUCH USERNAME : admin , PASSWORD : admin

    public void run(String... args) throws Exception {
        if (employeeRepository.findByUsername("admin") == null) {
            Employee admin = new Employee();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setRole("ADMIN");

            employeeRepository.save(admin);
        }
    }

}
