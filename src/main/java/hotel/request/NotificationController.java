// NotificationController.java
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
        // Burada gelen isteği kullanarak bildirim oluşturabilir ve veritabanına kaydedebilirsiniz
        String content = request.getContent();

        // Simüle edilmiş bildirim oluşturma ve kaydetme işlemi
        Notification notification = new Notification();
        notification.setMessage(content);
        notification.setStatus("ACTIVE");
        notificationRepository.save(notification);

        return new ResponseEntity<>("Notification processed successfully", HttpStatus.OK);
    }
}
