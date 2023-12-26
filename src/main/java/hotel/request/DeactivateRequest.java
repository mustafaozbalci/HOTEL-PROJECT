package hotel.request;

import lombok.Data;

@Data
public class DeactivateRequest {
    private String notificationId;
    private String employeeId;
    private String action;
}
