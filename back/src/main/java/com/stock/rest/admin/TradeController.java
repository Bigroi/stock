package com.stock.rest.admin;

import com.stock.service.TradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/admin/trade")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://test.dpcheck.com",
        "https://dpcheck.com",
        "https://www.dpcheck.com"
})
public class TradeController {

    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @PutMapping("/expiration-check")
    public ResponseEntity<?> expirationCheck() {
        tradeService.expirationCheck();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/clean-predeal")
    public ResponseEntity<?> cleanPredeals() {
        tradeService.cleanPreDeals();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/run")
    public ResponseEntity<?> run() {
        tradeService.trade();
        return ResponseEntity.ok().build();
    }
}
