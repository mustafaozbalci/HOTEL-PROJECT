package hotel.request;

import hotel.employee.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
