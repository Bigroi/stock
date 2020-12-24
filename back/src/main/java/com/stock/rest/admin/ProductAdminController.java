package com.stock.rest.admin;

import com.stock.entity.business.ProductCategoryRecord;
import com.stock.entity.business.ProductRecord;
import com.stock.service.CategoryService;
import com.stock.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("api/admin/")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://test.yourtrader.com",
        "https://yourtrader.com",
        "https://www.yourtrader.com"
})
public class ProductAdminController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductAdminController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/products")
    public ResponseEntity<?> getProduct() {
        return ResponseEntity.ok(productService.getProductsAsAdmin());
    }

    @PutMapping("/product/{id}/activate")
    public ResponseEntity<?> activateProduct(@PathVariable("id") UUID id) {
        if (productService.activate(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/product/{id}/deactivate")
    public ResponseEntity<?> deactivateProduct(@PathVariable("id") UUID id) {
        if (productService.deactivate(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestBody ProductRecord record) {
        return ResponseEntity.ok(productService.create(record));
    }

    @GetMapping("/product/{productId}/categories")
    public ResponseEntity<?> getCategories(@PathVariable("productId") UUID productId) {
        return ResponseEntity.ok(categoryService.getCategories(productId));
    }

    @PutMapping("/product/{productId}/category/{id}/activate")
    public ResponseEntity<?> activateCategory(
            @PathVariable("productId") UUID productId,
            @PathVariable("id") UUID id
    ) {
        if (categoryService.activate(productId, id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/product/{productId}/category/{id}/deactivate")
    public ResponseEntity<?> deactivateCategory(
            @PathVariable("productId") UUID productId,
            @PathVariable("id") UUID id
    ) {
        if (categoryService.deactivate(productId, id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/product/{productId}/category")
    public ResponseEntity<?> addCategory(
            @PathVariable("productId") UUID productId,
            @RequestBody ProductCategoryRecord record
    ) {
        return ResponseEntity.ok(categoryService.create(record, productId));
    }
}
