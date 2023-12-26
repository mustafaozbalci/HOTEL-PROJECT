package hotel.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        int roomNumber = request.getRoomNumber();

        // Simulate request processing and save to the database
        Notification notification = new Notification();
        notification.setMessage(request.getRequestType() + ": " + content);
        notification.setStatus("ACTIVE");
        notification.setRoomNumber(roomNumber);
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
        Integer notificationId = Integer.parseInt(request.getNotificationId());
        String action = request.getAction();

        try {
            Optional<Notification> optionalNotification = notificationRepository.findById(notificationId);

            if (optionalNotification.isPresent()) {
                Notification notification = optionalNotification.get();

                if ("confirm".equals(action)) {
                    notification.setStatus("PASSIVE");
                    notificationRepository.save(notification);
                    return new ResponseEntity<>("Notification deactivated successfully", HttpStatus.OK);
                } else if ("reject".equals(action)) {
                    notification.setStatus("REJECTED");
                    notificationRepository.save(notification);
                    return new ResponseEntity<>("Notification rejected successfully", HttpStatus.OK);
                } else {
                    // Handle other actions if needed
                    return new ResponseEntity<>("Invalid action specified", HttpStatus.BAD_REQUEST);
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
        List<Notification> historyNotifications = notificationRepository.findByStatusIn(List.of("PASSIVE", "REJECTED"));
        return new ResponseEntity<>(historyNotifications, HttpStatus.OK);
    }
    @GetMapping("/active-notifications")
    public ResponseEntity<List<Notification>> getActiveNotificationsByRoomNumber(@RequestParam int roomNumber) {
        List<Notification> activeNotifications = notificationRepository.findByStatusAndRoomNumber("ACTIVE", roomNumber);
        return new ResponseEntity<>(activeNotifications, HttpStatus.OK);
    }



    @PostMapping("/place-menu-order")
    public ResponseEntity<Map<String, String>> placeMenuOrder(@RequestBody CustomerRequest request) {
        String responseMessage = processMenuOrder(request);
        Map<String, String> response = new HashMap<>();
        response.put("message", responseMessage);
        return ResponseEntity.ok(response);
    }

    private String processMenuOrder(CustomerRequest request) {
        try {
            // Burada sipariş bilgileri işlenir, örneğin veritabanına kaydedilebilir.

            // Simulate request processing and save to the database
            Notification notification = new Notification();
            notification.setMessage("Received menu order: " + request.getProductName() +
                    " - Quantity: " + request.getQuantity());
            notification.setStatus("ACTIVE");
            notification.setRoomNumber(request.getRoomNumber());
            notificationRepository.save(notification);

            return "Menu Order processed successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing menu order. Please check server logs.";
        }
    }
}
