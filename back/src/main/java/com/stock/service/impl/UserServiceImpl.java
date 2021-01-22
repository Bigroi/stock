package com.stock.service.impl;

import com.stock.client.email.EmailClient;
import com.stock.dao.AddressDao;
import com.stock.dao.CompanyDao;
import com.stock.dao.UserDao;
import com.stock.entity.CompanyStatus;
import com.stock.entity.business.AddressRecord;
import com.stock.entity.business.CompanyRecord;
import com.stock.entity.business.UserRecord;
import com.stock.entity.ui.AccountData;
import com.stock.entity.ui.LoginResponse;
import com.stock.entity.ui.RegistrationRequest;
import com.stock.security.JwtTokenProcessor;
import com.stock.security.Role;
import com.stock.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final CompanyDao companyDao;
    private final AddressDao addressDao;
    private final JwtTokenProcessor tokenProcessor;
    private final PasswordEncoder passwordEncoder;
    private final EmailClient emailClient;

    public UserServiceImpl(
            UserDao userDao,
            CompanyDao companyDao,
            AddressDao addressDao,
            JwtTokenProcessor tokenProcessor,
            PasswordEncoder passwordEncoder,
            EmailClient emailClient
    ) {
        this.userDao = userDao;
        this.companyDao = companyDao;
        this.addressDao = addressDao;
        this.tokenProcessor = tokenProcessor;
        this.passwordEncoder = passwordEncoder;
        this.emailClient = emailClient;
    }

    @Override
    public Optional<LoginResponse> authenticate(String username, String password) {
        return userDao.getByUsername(username)
                .filter(u -> passwordEncoder.matches(password, u.getPassword()))
                .map(u -> {
                    u.setRefreshTokenIssued(new Date());
                    userDao.updateRefreshSecret(u.getUserName(), u.getRefreshTokenIssued());
                    return createLoginResponse(u);
                });
    }

    @Override
    public Optional<LoginResponse> create(RegistrationRequest request) {
        if (userDao.getByUsername(request.getUsername()).isPresent()) {
            return Optional.empty();
        }

        var company = new CompanyRecord(
                UUID.randomUUID(),
                request.getCompanyName(),
                request.getPhone(),
                request.getRegNumber(),
                CompanyStatus.NOT_VERIFIED
        );

        var user = new UserRecord(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                company.getId(),
                List.of(Role.USER),
                new Date(),
                request.getLanguage()
        );

        var address = new AddressRecord(
                UUID.randomUUID(),
                request.getCity(),
                request.getCountry(),
                request.getAddress(),
                request.getLatitude(),
                request.getLongitude(),
                company.getId(),
                true
        );

        companyDao.create(company);
        userDao.create(user);
        addressDao.create(address);

        return Optional.of(createLoginResponse(user));
    }

    @Override
    public Optional<LoginResponse> refreshTokens(String refreshToken) {
        if (refreshToken == null) {
            return Optional.empty();
        }
        var userName = tokenProcessor.getUsernameFromToken(refreshToken);
        return userDao.getByUsername(userName)
                .filter(u -> tokenProcessor.validateRefreshToken(refreshToken, u.getRefreshTokenIssued()))
                .map(u -> {
                    u.setRefreshTokenIssued(new Date());
                    userDao.updateRefreshSecret(u.getUserName(), u.getRefreshTokenIssued());
                    return createLoginResponse(u);
                });
    }

    @Override
    public Optional<UserRecord> getByUsername(String username) {
        return userDao.getByUsername(username);
    }

    @Override
    public LoginResponse createLoginResponse(UserRecord user) {
        var accessToken = tokenProcessor.generateAccessToken(user.getUserName());
        var refreshToken = tokenProcessor.generateRefreshToken(user.getUserName(), user.getRefreshTokenIssued());

        return new LoginResponse(
                accessToken,
                refreshToken,
                user.getUserName(),
                user.getRoles(),
                user.getLanguage().name(),
                companyDao.getByID(user.getCompanyId()).getName()
        );
    }

    @Override
    public Optional<String> checkUserForCreation(RegistrationRequest request) {
        return userDao.getByUsername(request.getUsername()).map(c -> "label.account.error_login")
                .or(() -> companyDao.getByName(request.getCompanyName()).map(c -> "label.account.error_name"))
                .or(() -> companyDao.getByRegNumber(request.getRegNumber()).map(c -> "label.account.error_reg_number"));
    }

    @Override
    public AccountData getAccountData(UserRecord user) {
        var company = companyDao.getByID(user.getCompanyId());
        var address = addressDao.getPrimaryByCompanyId(user.getCompanyId());

        return new AccountData(
                user.getUserName(),
                company.getPhone(),
                user.getLanguage(),
                company.getName(),
                company.getRegNumber(),
                "",
                address.getLatitude(),
                address.getLongitude()
        );
    }

    @Override
    public Optional<String> editAccountData(UserRecord user, AccountData accountData) {
        return Optional.of(accountData.getUsername())
                .filter(un -> !un.equals(user.getUserName()))
                .flatMap(userDao::getByUsername)
                .map(c -> "label.account.error_login")
                .or(() -> {
                    userDao.updateAccountData(
                            accountData.getUsername(),
                            accountData.getPassword().isEmpty()
                                    ? user.getPassword()
                                    : passwordEncoder.encode(accountData.getPassword()),
                            accountData.getLanguage(),
                            user.getUserName());
                    companyDao.updatePhone(accountData.getPhone(), user.getCompanyId());
                    user.setUserName(accountData.getUsername());
                    user.setLanguage(accountData.getLanguage());
                    return Optional.empty();
                });


    }
}
