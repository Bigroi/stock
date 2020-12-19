package com.stock.rest.user;

import com.stock.entity.business.TenderRecord;
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
public class TenderController {

    private final BidService<TenderRecord> tenderService;

    public TenderController(BidService<TenderRecord> tenderService) {
        this.tenderService = tenderService;
    }

    @GetMapping("/tenders")
    public ResponseEntity<?> getAllTenders() {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(tenderService.getByCompanyId(user.getCompanyId()));
    }

    @GetMapping("tender/{id}")
    public ResponseEntity<?> getTenderById(@PathVariable("id") UUID id) {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return tenderService.getById(id, user.getCompanyId())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("tender/{id}")
    public ResponseEntity<?> deleteTenderById(@PathVariable("id") UUID id) {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (tenderService.delete(id, user.getCompanyId())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("tender/{id}")
    public ResponseEntity<?> updateTender(@PathVariable("id") UUID id, @RequestBody TenderRecord tender) {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (tenderService.update(tender, user.getCompanyId())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("tender")
    public ResponseEntity<?> addTender(@RequestBody TenderRecord tender) {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return tenderService.add(tender, user.getCompanyId())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_MODIFIED).build());
    }

    @PutMapping("tender/{id}/deactivate")
    public ResponseEntity<?> deactivate(@PathVariable("id") UUID id) {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (tenderService.deactivate(id, user.getCompanyId())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("tender/{id}/activate")
    public ResponseEntity<?> activate(@PathVariable("id") UUID id) {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (tenderService.activate(id, user.getCompanyId())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
