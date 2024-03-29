package com.stock.rest.admin;

import com.stock.entity.ui.Label;
import com.stock.service.LabelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/admin/")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://test.dpcheck.com",
        "https://dpcheck.com",
        "https://www.dpcheck.com"
})
public class LabelsController {

    private final LabelService labelService;

    public LabelsController(LabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping("/labels")
    public ResponseEntity<?> all() {
        return ResponseEntity.ok(labelService.getAllLabelAsAdmin());
    }

    @PutMapping("/label/{name}")
    public ResponseEntity<?> update(@PathVariable("name") String name, @RequestBody Label label) {
        if (labelService.updateAsAdmin(label)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

    @PostMapping("/label/{name}")
    public ResponseEntity<?> create(@PathVariable("name") String name, @RequestBody Label label) {
        if (labelService.addAsAdmin(label)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

    @DeleteMapping("/label/{name}")
    public ResponseEntity<?> create(@PathVariable("name") String name) {
        if (labelService.deleteAsAdmin(name)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }
}
