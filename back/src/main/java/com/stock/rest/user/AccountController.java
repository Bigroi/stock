package com.stock.rest.user;

import com.stock.entity.business.UserRecord;
import com.stock.entity.ui.AccountData;
import com.stock.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/user/account")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://test.yourtrader.com",
        "https://yourtrader.com",
        "https://www.yourtrader.com"
})
public class AccountController {

    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<?> getAccountData() {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userService.getAccountData(user));
    }

    @PutMapping()
    public ResponseEntity<?> editAccountData(@RequestBody AccountData accountData) {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var error = userService.editAccountData(user, accountData)
                .map(ResponseEntity.status(HttpStatus.NOT_MODIFIED)::body);
        if (error.isPresent()) {
            return error.get();
        } else {
            return ResponseEntity.ok(userService.createLoginResponse(user));
        }
    }
}
