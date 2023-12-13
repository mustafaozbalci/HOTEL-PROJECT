package hotel.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public void addNotificationToInbox(Integer employeeId, String message) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Notification notification = new Notification();
        notification.setMessage(message);

        employee.addNotification(notification);
        employeeRepository.save(employee);
    }

    // Other employee-related methods can be added here
}
