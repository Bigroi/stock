package com.stock.rest.publicc;

import com.stock.entity.Language;
import com.stock.service.LabelService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/public/label")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://test.yourtrader.com",
        "https://yourtrader.com",
        "https://www.yourtrader.com"
})
public class LabelController {

    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping("/{lang}")
    public ResponseEntity<?> all(@PathVariable("lang") Language language) {
        return ResponseEntity.ok(labelService.getLabels(language));
    }

    @GetMapping("/languages")
    public ResponseEntity<?> languages() {
        return ResponseEntity.ok(labelService.getLanguages());
    }
}
