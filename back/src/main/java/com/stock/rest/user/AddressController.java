package com.stock.rest.user;

import com.stock.entity.business.AddressRecord;
import com.stock.entity.business.UserRecord;
import com.stock.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("api/user/")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://test.yourtrader.com",
        "https://yourtrader.com",
        "https://www.yourtrader.com"
})
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/addresses")
    public ResponseEntity<?> getUserAddresses() {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(addressService.getCompanyAddresses(user.getCompanyId()));
    }

    @PutMapping("/address")
    public ResponseEntity<?> updateAddresses(@RequestBody AddressRecord addressRecord) {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (addressService.updateAddress(addressRecord, user.getCompanyId())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/address")
    public ResponseEntity<?> addAddresses(@RequestBody AddressRecord addressRecord) {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return addressService.addAddress(addressRecord, user.getCompanyId())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/address/{id}")
    public ResponseEntity<?> addAddresses(@PathVariable("id") UUID addressId) {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (addressService.deleteAddress(addressId, user.getCompanyId())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/address/{id}/primary")
    public ResponseEntity<?> setPrimaryAddress(@PathVariable("id") UUID addressId) {
        var user = (UserRecord) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (addressService.setPrimary(addressId, user.getCompanyId())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
