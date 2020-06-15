package com.bigroi.stock.service;

import com.bigroi.stock.bean.db.StockUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void addUser(StockUser user);

    void update(StockUser user);

    void deleteGenerateKeys();

    void sendLinkResetPassword(String username);

    boolean changePassword(String email, String code);

    StockUser getByUsername(String username);

}
