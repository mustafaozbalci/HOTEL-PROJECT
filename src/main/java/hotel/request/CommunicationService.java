package hotel.request;

import hotel.employee.Employee;
import hotel.employee.Notification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommunicationService {

    private List<Notification> notifications = new ArrayList<>();

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void processCustomerRequest(CustomerRequest request) {
        // Determine the request type and take appropriate actions
        if ("RESERVATION".equals(request.getRequestType())) {
            // Handle reservation request
            sendReservationNotification(request.getContent());
        }
        // Add other conditions for different request types if needed
    }

    private void sendReservationNotification(String reservationDetails) {
        // Simulate sending a reservation notification to an employee
        Employee employee = getEmployeeToNotify(); // You should implement a method to determine the employee
        sendNotificationToEmployee(employee, "New Reservation: " + reservationDetails);
    }

    private Employee getEmployeeToNotify() {
        // Implement logic to determine the employee to notify
        // For simplicity, you can return a predefined employee for now
        return new Employee(/* employee details */);
    }

    private void sendNotificationToEmployee(Employee employee, String message) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setEmployee(employee);

        notifications.add(notification); // Add the notification to the list

        // Perform additional operations such as saving to the database

        // Send the notification, e.g., by email or other means
    }

    public void sendSpaNotification(String content) {
        // Spa talebi için özel bir işlem yapabilirsiniz
        Employee employee = getEmployeeToNotify(); // You should implement a method to determine the employee
        sendNotificationToEmployee(employee, "New Spa Reservation: " + content);
    }

    public void sendExtraPersonNotification(String content) {
        // Extra person talebi için özel bir işlem yapabilirsiniz
        Employee employee = getEmployeeToNotify(); // You should implement a method to determine the employee
        sendNotificationToEmployee(employee, "New Extra Person Request: " + content);
    }

}
