package hotel.Expenses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/invoice-status")
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    //TO SHOW INVOICE HISTORY BY ROOM NUMBER FILTER
    @GetMapping("/getByRoomNumber/{roomNumber}")
    public ResponseEntity<Invoice> getInvoiceByRoomNumber(@PathVariable int roomNumber) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findByRoomNumber(roomNumber);
        if (optionalInvoice.isPresent()) {
            Invoice invoice = optionalInvoice.get();
            return new ResponseEntity<>(invoice, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //TO LIST ALL INVOICES
    @GetMapping("/getAll")
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    //TO RESET THE INVOICE HISTORY OF ROOM NUMBER
    @PostMapping("/close/{id}")
    public ResponseEntity<String> closeInvoice(@PathVariable int id) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);
        if (optionalInvoice.isPresent()) {
            Invoice invoice = optionalInvoice.get();
            invoice.setOrderDescription("");
            invoice.setTotalAmount(0);
            invoiceRepository.save(invoice);

            return new ResponseEntity<>("Invoice with ID " + id + " is closed.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invoice with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }
}
