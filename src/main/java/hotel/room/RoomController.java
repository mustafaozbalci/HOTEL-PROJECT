package hotel.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @PutMapping("/updatePrice")
    public ResponseEntity<String> updateRoomPrice(
            @RequestParam Long roomId,
            @RequestParam double newPrice
    ) {
        try {
            System.out.println("Updating room with ID: " + roomId + " to new price: " + newPrice);

            Room roomToUpdate = roomRepository.findById(roomId)
                    .orElseThrow(() -> new RuntimeException("Room not found with id: " + roomId));
            roomToUpdate.setPrice(newPrice);
            roomRepository.save(roomToUpdate);

            System.out.println("Room updated successfully");

            return ResponseEntity.ok("Room updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error updating room: " + e.getMessage());
        }
    }
}
