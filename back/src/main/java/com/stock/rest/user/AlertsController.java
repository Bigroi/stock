package com.stock.rest.user;

import com.stock.entity.business.UserRecord;
import com.stock.service.AlertsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/user/alerts")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://test.yourtrader.com",
        "https://yourtrader.com",
        "https://www.yourtrader.com"
})
public class AlertsController {

    private final AlertsService alertsService;

    public AlertsController(AlertsService alertsService) {
        this.alertsService = alertsService;
    }

    @GetMapping()
    public ResponseEntity<?> getAlerts() {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(alertsService.getAlerts(user.getCompanyId()));
    }

    @PutMapping("/lots")
    public ResponseEntity<?> resetLotsAlerts() {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (alertsService.resetLotsAlerts(user.getCompanyId())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

    @PutMapping("/tenders")
    public ResponseEntity<?> resetTenderAlerts() {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (alertsService.resetTenderAlerts(user.getCompanyId())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

    @PutMapping("/deals")
    public ResponseEntity<?> resetDealAlerts() {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (alertsService.resetDealAlerts(user.getCompanyId())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }
}
