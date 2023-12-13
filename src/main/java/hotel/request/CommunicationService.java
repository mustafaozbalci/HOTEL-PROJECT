// CommunicationService.java
package hotel.request;

import hotel.employee.Notification;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommunicationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Getter
    private List<Notification> notifications = new ArrayList<>();

    private final String NOTIFICATION_API_URL = "http://localhost:8080/process-request/notifications";

    public void sendNotificationFromButtonClick(String requestType) {
        // Button click method
        String content = "Button Clicked - " + requestType;

        // Simulate server-side behavior locally
        Notification notification = new Notification();
        notification.setMessage(content);
        notification.setStatus("ACTIVE");
        notificationRepository.save(notification);

        // Optional: Add the notification to the list
        notifications.add(notification);
    }

    // Diğer metotlar
}
