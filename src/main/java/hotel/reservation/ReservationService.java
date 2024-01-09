package hotel.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;


    //TO SAVE RESERVATION WHILE ADDING A CUSTOMER
    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }
}