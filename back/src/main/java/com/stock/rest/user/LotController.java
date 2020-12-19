package com.stock.rest.user;

import com.stock.entity.business.LotRecord;
import com.stock.entity.business.UserRecord;
import com.stock.service.BidService;
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
public class LotController {

    private final BidService<LotRecord> lotService;

    public LotController(BidService<LotRecord> lotService) {
        this.lotService = lotService;
    }

    @GetMapping("/lots")
    public ResponseEntity<?> getAllLots() {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(lotService.getByCompanyId(user.getCompanyId()));
    }

    @GetMapping("lot/{id}")
    public ResponseEntity<?> getLotById(@PathVariable("id") UUID id) {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return lotService.getById(id, user.getCompanyId())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("lot/{id}")
    public ResponseEntity<?> deleteLotById(@PathVariable("id") UUID id) {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (lotService.delete(id, user.getCompanyId())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("lot/{id}")
    public ResponseEntity<?> updateLot(@PathVariable("id") UUID id, @RequestBody LotRecord lot) {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (lotService.update(lot, user.getCompanyId())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("lot")
    public ResponseEntity<?> addLot(@RequestBody LotRecord lot) {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return lotService.add(lot, user.getCompanyId())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_MODIFIED).build());
    }

    @PutMapping("lot/{id}/deactivate")
    public ResponseEntity<?> deactivate(@PathVariable("id") UUID id) {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (lotService.deactivate(id, user.getCompanyId())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("lot/{id}/activate")
    public ResponseEntity<?> activate(@PathVariable("id") UUID id) {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (lotService.activate(id, user.getCompanyId())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
