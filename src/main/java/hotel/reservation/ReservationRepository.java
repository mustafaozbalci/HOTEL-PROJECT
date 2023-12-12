package hotel.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByCustomerId(Long customerId);

    @Query("SELECT r FROM Reservation r WHERE r.roomNumber = :roomNumber " +
            "AND ((r.checkInDate BETWEEN :startDate AND :endDate) OR (r.checkOutDate BETWEEN :startDate AND :endDate))")
    List<Reservation> findOccupiedIntervals(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("roomNumber") int roomNumber);

    List<Reservation> findByCustomer_CustomerId(Long customerId);
}