package hotel.spa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaRepository extends JpaRepository<SpaReservation, Integer> {
}
