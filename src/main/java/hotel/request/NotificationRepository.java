package hotel.request;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByStatus(String status);

    List<Notification> findByStatusIn(List<String> statusList);

    List<Notification> findByStatusAndRoomNumber(String status, int roomNumber);
    List<Notification> findByStatusInAndRoomNumber(List<String> statuses, int roomNumber);
}


