package hotel.spa;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/spa-reservations")
public class SpaReservationController {

    // SpaReservationService spaReservationService; // SpaReservation servisinizin inject edildiğinden emin olun

    @PatchMapping("/{spaReservationId}")
    public ResponseEntity<String> updateSpaReservation(@PathVariable Long spaReservationId,
                                                       @RequestBody SpaReservation updatedSpaReservation) {
        // Burada spa rezervasyonunu güncelleme işlemlerini gerçekleştirin
        // spaReservationService.updateSpaReservation(spaReservationId, updatedSpaReservation);
        return ResponseEntity.ok("Spa Reservation updated successfully");
    }
}
