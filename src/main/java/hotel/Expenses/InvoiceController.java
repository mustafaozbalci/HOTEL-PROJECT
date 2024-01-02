package hotel.Expenses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/invoice-status")
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @GetMapping("/getByRoomNumber/{roomNumber}")
    public ResponseEntity<Invoice> getInvoiceByRoomNumber(@PathVariable int roomNumber) {
        // Find invoice by room number
        Optional<Invoice> optionalInvoice = invoiceRepository.findByRoomNumber(roomNumber);

        // Check if the invoice exists
        if (optionalInvoice.isPresent()) {
            Invoice invoice = optionalInvoice.get();
            return new ResponseEntity<>(invoice, HttpStatus.OK);
        } else {
            // Invoice not found for the given room number
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

}
