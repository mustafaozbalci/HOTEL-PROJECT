package hotel.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class StockDataLoader implements CommandLineRunner {

    private final StockRepository stockRepository;
    private boolean dataLoaded = false;

    @Autowired
    public StockDataLoader(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!dataLoaded) {
            List<String> stockItems = Arrays.asList("Fanta", "Coca Cola", "Italian Pasta", "Grilled Salmon",
                    "Breakfast Burger", "Fruit Smoothie", "Caesar Salad", "Margarita Pizza",
                    "Vegetarian Wrap", "Chocolate Brownie Sundae", "Chicken Alfredo", "Green Detox Smoothie");

            // Stokta her üründen 500 adet ekleyin
            for (String stockItem : stockItems) {
                Stock existingStock = stockRepository.findByProductName(stockItem);

                if (existingStock == null) {
                    Stock stock = new Stock();
                    stock.setProductName(stockItem);
                    stock.setProductType(getProductType(stockItem));
                    stock.setCurrentStock(500);

                    stockRepository.save(stock);
                } else {
                    existingStock.setCurrentStock(existingStock.getCurrentStock() + 500);
                    stockRepository.save(existingStock);
                }
            }
            dataLoaded = true;
        }
    }

    private String getProductType(String productName) {
        if (productName.toLowerCase().contains("cola") || productName.toLowerCase().contains("smoothie") || productName.toLowerCase().contains("fanta")) {
            return "drink";
        } else {
            return "food";
        }
    }
}
