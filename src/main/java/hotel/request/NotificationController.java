package hotel.request;

import hotel.Expenses.Invoice;
import hotel.Expenses.InvoiceRepository;
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

    @PostMapping("/notifications")
    public ResponseEntity<String> processNotificationRequest(@RequestBody CustomerRequest request) {
        return processRequest(request, "Notification processed successfully");
    }

    @PostMapping("/spa-requests")
    public ResponseEntity<String> processSpaRequest(@RequestBody CustomerRequest request) {
        return processRequest(request, "Spa Request processed successfully");
    }

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


    @GetMapping
    public ResponseEntity<List<Notification>> getActiveNotifications() {
        List<Notification> activeNotifications = notificationRepository.findByStatus("ACTIVE");
        return new ResponseEntity<>(activeNotifications, HttpStatus.OK);
    }

    @PostMapping("/deactivate-notification")
    public ResponseEntity<String> deactivateNotification(@RequestBody DeactivateRequest request) {
        Integer notificationId = Integer.parseInt(request.getNotificationId());
        String action = request.getAction();

        try {
            Optional<Notification> optionalNotification = notificationRepository.findById(notificationId);

            if (optionalNotification.isPresent()) {
                Notification notification = optionalNotification.get();

                if ("confirm".equals(action)) {
                    notification.setStatus("PASSIVE");
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


    @GetMapping("/notification-history")
    public ResponseEntity<List<Notification>> getNotificationHistory() {
        List<Notification> historyNotifications = notificationRepository.findByStatusIn(List.of("PASSIVE", "REJECTED"));
        return new ResponseEntity<>(historyNotifications, HttpStatus.OK);
    }
    @GetMapping("/active-notifications")
    public ResponseEntity<List<Notification>> getActiveNotificationsByRoomNumber(@RequestParam int roomNumber) {
        List<Notification> activeNotifications = notificationRepository.findByStatusAndRoomNumber("ACTIVE", roomNumber);
        return new ResponseEntity<>(activeNotifications, HttpStatus.OK);
    }



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
            return null; // Handle error appropriately, e.g., return an error code
        }
        return null;
    }
    // Add a new endpoint to save invoice information
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
