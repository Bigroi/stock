package com.stock.service.transactional;

import com.stock.dao.Transactional;
import com.stock.entity.business.UserRecord;
import com.stock.entity.ui.AccountData;
import com.stock.entity.ui.LoginResponse;
import com.stock.entity.ui.RegistrationRequest;
import com.stock.service.UserService;

import java.util.Optional;

public class UserServiceTransactional implements UserService {

    private final Transactional transactional;
    private final UserService service;

    public UserServiceTransactional(Transactional transactional, UserService service) {
        this.transactional = transactional;
        this.service = service;
    }

    public Optional<LoginResponse> authenticate(String username, String password) {
        return service.authenticate(username, password);
    }

    public Optional<LoginResponse> create(RegistrationRequest request) {
        return transactional.inTransaction(() -> service.create(request));
    }

    public Optional<UserRecord> getByUsername(String username) {
        return service.getByUsername(username);
    }

    public Optional<LoginResponse> refreshTokens(String refreshToken) {
        return service.refreshTokens(refreshToken);
    }

    @Override
    public Optional<String> checkUserForCreation(RegistrationRequest request) {
        return service.checkUserForCreation(request);
    }

    @Override
    public AccountData getAccountData(UserRecord user) {
        return service.getAccountData(user);
    }

    @Override
    public Optional<String> editAccountData(UserRecord user, AccountData accountData) {
        return transactional.inTransaction(() -> service.editAccountData(user, accountData));
    }

    @Override
    public LoginResponse createLoginResponse(UserRecord user) {
        return service.createLoginResponse(user);
    }
}
