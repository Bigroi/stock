package com.stock.rest.publicc;

import com.stock.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("api/public/")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://test.yourtrader.com",
        "https://yourtrader.com",
        "https://www.yourtrader.com"
})
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/categories")
    public ResponseEntity<?> getProducts() {
        return ResponseEntity.ok(productService.getActiveProducts());
    }

    @GetMapping("/statistics/products")
    public ResponseEntity<?> getProductsStatistics() {
        return ResponseEntity.ok(productService.getProductsStatistics());
    }

    @GetMapping("/statistics/product/{id}/details")
    public ResponseEntity<?> getProductStatisticsDetails(@PathVariable("id") UUID productId) {
        return productService.getProductStatisticsDetails(productId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
