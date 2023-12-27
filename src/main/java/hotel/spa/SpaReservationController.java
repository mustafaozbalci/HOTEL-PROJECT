package hotel.spa;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/spa-reservations")
public class SpaReservationController {


    @PatchMapping("/{spaReservationId}")
    public ResponseEntity<String> updateSpaReservation(@PathVariable Long spaReservationId,
                                                       @RequestBody SpaReservation updatedSpaReservation) {
        return ResponseEntity.ok("Spa Reservation updated successfully");
    }
}
