package hotel.Expenses;

import hotel.request.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/invoice-status")
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @GetMapping("/all")
    public ResponseEntity<List<Invoice>> getInvoices() {
        List<Invoice> activeNotifications = invoiceRepository.findAll();
        return new ResponseEntity<>(activeNotifications, HttpStatus.OK);
    }
}
