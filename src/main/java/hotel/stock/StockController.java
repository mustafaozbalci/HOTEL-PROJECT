package hotel.stock;

import hotel.request.StockUpdateRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
@Data
public class StockController {

    @Autowired
    private final StockRepository stockRepository;

    @PostMapping("/updateStock")
    public ResponseEntity<String> updateStock(@RequestBody StockUpdateRequest request) {
        Stock stock = stockRepository.findByProductName(request.getProductName());

        if (stock != null) {
            int currentStock = stock.getCurrentStock();

            if (currentStock >= request.getQuantity()) {
                stock.setCurrentStock(currentStock - request.getQuantity());
                stockRepository.save(stock);
                return ResponseEntity.ok("Stock updated successfully.");
            } else {
                throw new RuntimeException("Insufficient stock.");
            }
        } else {
            throw new RuntimeException("Product not found.");
        }
    }
    @GetMapping("/allStock")
    public ResponseEntity<List<Stock>> getAllStock() {
        List<Stock> allStock = stockRepository.findAll();
        return ResponseEntity.ok(allStock);
    }
    @PostMapping("/addStock")
    public ResponseEntity<String> addStock(@RequestParam String productName, @RequestParam String productType, @RequestParam int currentStock) {
        Stock existingStock = stockRepository.findByProductName(productName);
        if (existingStock != null) {
            // Eğer ürün zaten varsa, mevcut stok miktarını arttır
            existingStock.setCurrentStock(existingStock.getCurrentStock() + currentStock);
            stockRepository.save(existingStock);
            return ResponseEntity.ok("Stock updated successfully.");
        } else {
            // Eğer ürün yoksa, yeni bir ürün ekle
            Stock newStock = new Stock();
            newStock.setProductName(productName);
            newStock.setProductType(productType);
            newStock.setCurrentStock(currentStock);
            stockRepository.save(newStock);
            return ResponseEntity.ok("Stock added successfully.");
        }
    }
}
