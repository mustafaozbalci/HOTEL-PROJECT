package hotel.spa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/spa-reservations")
public class SpaReservationController {

    @Autowired
    private SpaRepository spaRepository;


    @GetMapping
    public List<SpaReservation> getAllSpaReservations() {
        return spaRepository.findAll();
    }

}
