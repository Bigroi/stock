package com.stock.rest.publicc;

import com.stock.entity.ui.LoginRequest;
import com.stock.entity.ui.RegistrationRequest;
import com.stock.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("api/public")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://test.yourtrader.com",
        "https://yourtrader.com",
        "https://www.yourtrader.com"
})
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest request) {
        return userService.authenticate(request.getUsername(), request.getPassword())
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.FORBIDDEN));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> body) {
        return userService.refreshTokens(body.get("token"))
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.FORBIDDEN));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registrar(@RequestBody RegistrationRequest request) {
        var errorResponse = userService.checkUserForCreation(request)
                .map(error -> ResponseEntity.status(HttpStatus.FORBIDDEN).body(error));
        if (errorResponse.isPresent()) {
            return errorResponse.get();
        } else {
            return userService.create(request)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.FORBIDDEN));
        }
    }
}
