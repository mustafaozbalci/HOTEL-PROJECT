package hotel.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RejectedCustomerRepository extends JpaRepository<RejectedCustomer, Integer> {
}
