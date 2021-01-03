package com.stock.rest.user;

import com.stock.entity.PartnerChoice;
import com.stock.entity.business.UserRecord;
import com.stock.entity.ui.UserComment;
import com.stock.service.DealService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("api/user")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://test.yourtrader.com",
        "https://yourtrader.com",
        "https://www.yourtrader.com"
})
public class DealController {

    private final DealService dealService;

    public DealController(DealService dealService) {
        this.dealService = dealService;
    }

    @GetMapping("/deals")
    public ResponseEntity<?> getAllLots() {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(dealService.getByCompanyId(user.getCompanyId()));
    }

    @GetMapping("deal/{id}")
    public ResponseEntity<?> getLotById(@PathVariable("id") UUID id) {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return dealService.getById(id, user.getCompanyId())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("deal/{id}/choice/{choice}")
    public ResponseEntity<?> changeChoice(@PathVariable("id") UUID id, @PathVariable("choice") PartnerChoice choice) {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return dealService.setState(id, user.getCompanyId(), choice)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }

    @PostMapping("deal/{id}/comment")
    public ResponseEntity<?> addComment(@PathVariable("id") UUID id, @RequestBody UserComment comment) {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (dealService.createComment(id, user.getCompanyId(), comment)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping("deal/{id}/comment")
    public ResponseEntity<?> updateComment(@PathVariable("id") UUID id, @RequestBody UserComment comment) {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (dealService.updateComment(id, user.getCompanyId(), comment)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("deal/{id}/comment")
    public ResponseEntity<?> getComment(@PathVariable("id") UUID id) {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return dealService.getComment(id, user.getCompanyId())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
