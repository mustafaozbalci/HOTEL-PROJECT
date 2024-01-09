package hotel.request;

import hotel.Expenses.Invoice;
import hotel.Expenses.InvoiceRepository;
import hotel.spa.SpaRepository;
import hotel.spa.SpaReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/process-request")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private SpaRepository spaRepository;

    //TO SAVE VARY NOTIFICATIONS
    @PostMapping("/notifications")
    public ResponseEntity<String> processNotificationRequest(@RequestBody CustomerRequest request) {
        return processRequest(request, "Notification processed successfully");
    }

    //TO SAVE SPA REQUESTS
    @PostMapping("/spa-requests")
    public ResponseEntity<String> processSpaRequest(@RequestBody CustomerRequest request) {
        try {
            String content = request.getContent();
            int roomNumber = request.getRoomNumber();

            // Create a new SpaReservation object
            SpaReservation spaReservation = new SpaReservation();
            spaReservation.setRoomNumber(roomNumber);
            spaReservation.setMessage(content);

            // Save the SpaReservation to the database using SpaRepository
            spaRepository.save(spaReservation);

            // Simulate request processing and save to the database
            Notification notification = new Notification();
            notification.setMessage(request.getRequestType() + ": " + content);
            notification.setStatus("ACTIVE");
            notification.setRoomNumber(roomNumber);
            notificationRepository.save(notification);

            return new ResponseEntity<>("Spa Request processed successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error processing Spa Request. Please check server logs.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //TO SAVE EXTEND ACCOMMODATION REQUEST
    @PostMapping("/extend-accommodation-requests")
    public ResponseEntity<String> processExtendAccommodationRequest(@RequestBody CustomerRequest request) {
        return processRequest(request, "Extend Accommodation Request processed successfully");
    }

    private ResponseEntity<String> processRequest(CustomerRequest request, String successMessage) {
        String content = request.getContent();
        int roomNumber = request.getRoomNumber();

        // Simulate request processing and save to the database
        Notification notification = new Notification();
        notification.setMessage(request.getRequestType() + ": " + content);
        notification.setStatus("ACTIVE");
        notification.setRoomNumber(roomNumber);
        notificationRepository.save(notification);

        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    //TO FIND THE STATUS : "ACTIVE" NOTIFICATIONS
    @GetMapping
    public ResponseEntity<List<Notification>> getActiveNotifications() {
        List<Notification> activeNotifications = notificationRepository.findByStatus("ACTIVE");
        return new ResponseEntity<>(activeNotifications, HttpStatus.OK);
    }

    //TO CHANE THE STATUS OF THE NOTIFICATION TO ACCEPTED IF CONFIRMED,REJECTED IF REJECT
    @PostMapping("/deactivate-notification")
    public ResponseEntity<String> deactivateNotification(@RequestBody DeactivateRequest request) {
        Integer notificationId = Integer.parseInt(request.getNotificationId());
        String action = request.getAction();

        try {
            Optional<Notification> optionalNotification = notificationRepository.findById(notificationId);

            if (optionalNotification.isPresent()) {
                Notification notification = optionalNotification.get();

                if ("confirm".equals(action)) {
                    notification.setStatus("ACCEPTED");
                    notificationRepository.save(notification);
                    return new ResponseEntity<>("Notification deactivated successfully", HttpStatus.OK);
                } else if ("reject".equals(action)) {
                    notification.setStatus("REJECTED");
                    notificationRepository.save(notification);
                    return new ResponseEntity<>("Notification rejected successfully", HttpStatus.OK);
                } else {
                    // Handle other actions if needed
                    return new ResponseEntity<>("Invalid action specified", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("Notification not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error during deactivation. Please check server logs.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //TO LIST THE PASSIVE AND REJECTED NOTIFICATIONS
    @GetMapping("/notification-history")
    public ResponseEntity<List<Notification>> getNotificationHistory() {
        List<Notification> historyNotifications = notificationRepository.findByStatusIn(List.of("PASSIVE", "REJECTED"));
        return new ResponseEntity<>(historyNotifications, HttpStatus.OK);
    }

    //TO LIST THE ACTIVE NOTIFICATIONS
    @GetMapping("/active-notifications")
    public ResponseEntity<List<Notification>> getActiveNotificationsByRoomNumber(@RequestParam int roomNumber) {
        List<Notification> activeNotifications =
                notificationRepository.findByStatusAndRoomNumber("ACTIVE", roomNumber);
        return new ResponseEntity<>(activeNotifications, HttpStatus.OK);
    }

    //TO LIST THE REJECTED OR ACCEPTED NOTIFICATIONS BY ROOM NUMBER FILTER
    @GetMapping("/passive-notifications")
    public ResponseEntity<List<Notification>> getPassiveNotificationsByRoomNumber(@RequestParam int roomNumber) {
        List<Notification> passiveNotifications =
                notificationRepository.findByStatusInAndRoomNumber(List.of("REJECTED", "ACCEPTED"), roomNumber);
        return new ResponseEntity<>(passiveNotifications, HttpStatus.OK);
    }


    //TO GET THE REQUEST AND PROCESS MENU ORDER
    @PostMapping("/place-menu-order")
    public ResponseEntity<Map<String, String>> placeMenuOrder(@RequestBody CustomerRequest request) {
        Long invoiceId = processMenuOrder(request);

        Map<String, String> response = new HashMap<>();
        if (invoiceId != null) {
            response.put("message", "Menu Order processed successfully");
            response.put("invoiceId", String.valueOf(invoiceId));
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Error processing menu order. Please check server logs.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    //TO SAVE THE MENU ORDER AS A NOTIFICATION
    private Long processMenuOrder(CustomerRequest request) {
        try {
            // Simulate request processing and save to the database
            Notification notification = new Notification();
            notification.setMessage("Received menu order: " + request.getProductName() +
                    " - Quantity: " + request.getQuantity());
            notification.setStatus("ACTIVE");
            notification.setRoomNumber(request.getRoomNumber());
            notificationRepository.save(notification);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    //TO CALCULATE THE ORDER PRICES AND SAVE IT WITH ROOM NUMBER
    @PostMapping("/save-invoice")
    public ResponseEntity<Map<String, String>> saveInvoice(@RequestBody InvoiceRequest invoiceRequest) {
        try {
            // Check if an invoice with the given room number already exists
            Optional<Invoice> existingInvoice = invoiceRepository.findByRoomNumber(invoiceRequest.getRoomNumber());

            if (existingInvoice.isPresent()) {
                // Update the existing invoice record
                Invoice invoice = existingInvoice.get();
                double existingTotalAmount = invoice.getTotalAmount();

                // Concatenate the existing "price" with the new "price" from the request
                String existingPrice = invoice.getPrice();
                String newPrice = String.valueOf(invoiceRequest.getPrice());
                String updatedPrice = existingPrice + " + " + newPrice;

                // Concatenate the existing "orderDescription" with the new "orderDescription" from the request
                String existingOrderDescription = invoice.getOrderDescription();
                String newOrderDescription = invoiceRequest.getOrderDescription();
                String updatedOrderDescription = existingOrderDescription + " - " + newOrderDescription;

                // Calculate the new total amount by adding the existing total amount and the new amount
                double newTotalAmount = existingTotalAmount + invoiceRequest.getTotalAmount();

                // Update the invoice with the new price, total amount, and updated order description
                invoice.setPrice(updatedPrice);
                invoice.setTotalAmount(newTotalAmount);
                invoice.setOrderDescription(updatedOrderDescription); // Set the updated order description
                invoiceRepository.save(invoice);

                // Create a description of how the total amount is calculated
                String description = "Existing total amount: " + existingTotalAmount +
                        ", New amount added: " + invoiceRequest.getTotalAmount() +
                        ", Updated total amount: " + newTotalAmount +
                        ", Updated price history: " + updatedPrice +
                        ", Updated order description: " + updatedOrderDescription;
                System.out.println(description);
            } else {
                // Create a new invoice record
                Invoice invoice = new Invoice();
                invoice.setRoomNumber(invoiceRequest.getRoomNumber());
                // Convert the price to a string
                invoice.setPrice(String.valueOf(invoiceRequest.getPrice()));
                invoice.setTotalAmount(invoiceRequest.getTotalAmount());
                invoice.setOrderDescription(invoiceRequest.getOrderDescription()); // Set the order description
                invoiceRepository.save(invoice);
            }

            Map<String, String> response = new HashMap<>();
            response.put("message", "Invoice saved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error saving invoice. Please check server logs.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
