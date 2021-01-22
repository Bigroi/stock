package com.stock.service;

import com.stock.entity.business.UserRecord;
import com.stock.entity.ui.AccountData;
import com.stock.entity.ui.LoginResponse;
import com.stock.entity.ui.RegistrationRequest;

import java.util.Optional;

public interface UserService {

    Optional<LoginResponse> authenticate(String username, String password);

    Optional<LoginResponse> create(RegistrationRequest registrationRequest);

    Optional<UserRecord> getByUsername(String username);

    Optional<LoginResponse> refreshTokens(String refreshToken);

    Optional<String> checkUserForCreation(RegistrationRequest request);

    AccountData getAccountData(UserRecord user);

    Optional<String> editAccountData(UserRecord user, AccountData accountData);

    LoginResponse createLoginResponse(UserRecord user);
}
