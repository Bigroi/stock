package com.stock.rest.publicc;

import com.stock.entity.ui.FeedBackRequest;
import com.stock.service.LabelService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/public/feedback")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://test.yourtrader.com",
        "https://yourtrader.com",
        "https://www.yourtrader.com"
})
public class FeedBackController {

    private final LabelService labelService;

    public FeedBackController(LabelService labelService) {
        this.labelService = labelService;
    }

    @PostMapping()
    public ResponseEntity<?> languages(@RequestBody FeedBackRequest feedBackRequest) {
        return ResponseEntity.ok(labelService.getLanguages());
    }
}
