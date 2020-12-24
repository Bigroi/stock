package com.stock.rest.admin;

import com.stock.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("api/admin/")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://test.dpcheck.com",
        "https://dpcheck.com",
        "https://www.dpcheck.com"
})
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/companies")
    public ResponseEntity<?> all() {
        return ResponseEntity.ok(companyService.getCompanies());
    }

    @PutMapping("/company/{id}/revoke")
    public ResponseEntity<?> revoke(@PathVariable("id") UUID id) {
        if (companyService.deactivate(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

    @PutMapping("/company/{id}/activate")
    public ResponseEntity<?> activate(@PathVariable("id") UUID id) {
        if (companyService.activate(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }
}
