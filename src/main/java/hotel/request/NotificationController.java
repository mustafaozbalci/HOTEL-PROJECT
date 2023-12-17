package hotel.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/process-request")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @PostMapping("/notifications")
    public ResponseEntity<String> processNotificationRequest(@RequestBody CustomerRequest request) {
        return processRequest(request, "Notification processed successfully");
    }

    @PostMapping("/spa-requests")
    public ResponseEntity<String> processSpaRequest(@RequestBody CustomerRequest request) {
        return processRequest(request, "Spa Request processed successfully");
    }

    @PostMapping("/extend-accommodation-requests")
    public ResponseEntity<String> processExtendAccommodationRequest(@RequestBody CustomerRequest request) {
        return processRequest(request, "Extend Accommodation Request processed successfully");
    }

    private ResponseEntity<String> processRequest(CustomerRequest request, String successMessage) {
        String content = request.getContent();

        // Simulate request processing and save to the database
        Notification notification = new Notification();
        notification.setMessage(request.getRequestType() + ": " + content);
        notification.setStatus("ACTIVE");
        notificationRepository.save(notification);

        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getActiveNotifications() {
        List<Notification> activeNotifications = notificationRepository.findByStatus("ACTIVE");
        return new ResponseEntity<>(activeNotifications, HttpStatus.OK);
    }

    @PostMapping("/deactivate-notification")
    public ResponseEntity<String> deactivateNotification(@RequestBody DeactivateRequest request) {
        String employeeId = request.getEmployeeId();
        Integer notificationId = Integer.parseInt(request.getNotificationId());

        try {
            Optional<Notification> optionalNotification = notificationRepository.findById(notificationId);

            if (optionalNotification.isPresent()) {
                Notification notification = optionalNotification.get();

                // Check if the employee ID is 1
                if ("1".equals(employeeId)) {
                    notification.setStatus("PASSIVE");
                    notificationRepository.save(notification);
                    return new ResponseEntity<>("Notification deactivated successfully", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Permission denied. You don't have the right to deactivate notifications.", HttpStatus.FORBIDDEN);
                }
            } else {
                return new ResponseEntity<>("Notification not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error during deactivation. Please check server logs.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/notification-history")
    public ResponseEntity<List<Notification>> getNotificationHistory() {
        List<Notification> historyNotifications = notificationRepository.findByStatus("PASSIVE");
        return new ResponseEntity<>(historyNotifications, HttpStatus.OK);
    }



}
