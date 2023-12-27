package hotel.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoomDataLoader implements CommandLineRunner {
    @Autowired
    private RoomRepository roomRepository;


    public void run(String... args) throws Exception {
        // Check if rooms already exist in the database
        if (roomRepository.count() > 0) {
            return;
        }

        // Load sample data into the database
        Room room1 = new Room(null, 101, 2, 150.0, "Standard", true);
        Room room2 = new Room(null, 202, 3, 200.0, "Deluxe", true);
        Room room3 = new Room(null, 303, 4, 250.0, "Suite", true);
        Room room4 = new Room(null, 404, 2, 180.0, "Standard", true);
        Room room5 = new Room(null, 505, 3, 220.0, "Deluxe", true);

        roomRepository.saveAll(List.of(room1, room2, room3, room4, room5));
    }
}
