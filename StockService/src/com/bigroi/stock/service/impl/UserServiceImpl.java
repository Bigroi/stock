package com.bigroi.stock.service.impl;

import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.bean.common.Role;
import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.bean.db.UserRole;
import com.bigroi.stock.dao.*;
import com.bigroi.stock.messager.email.EmailClient;
import com.bigroi.stock.messager.email.EmailType;
import com.bigroi.stock.service.UserService;
import com.bigroi.stock.util.Generator;
import com.bigroi.stock.util.LabelUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final CompanyDao companyDao;
    private final UserRoleDao userRoleDao;
    private final GenerateKeyDao keysDao;
    private final AddressDao addressDao;
    private final EmailClient emailClient;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserDao userDao,
            CompanyDao companyDao,
            UserRoleDao userRoleDao,
            GenerateKeyDao keysDao,
            AddressDao addressDao,
            EmailClient emailClient,
            PasswordEncoder passwordEncoder
    ) {
        this.userDao = userDao;
        this.companyDao = companyDao;
        this.userRoleDao = userRoleDao;
        this.keysDao = keysDao;
        this.addressDao = addressDao;
        this.emailClient = emailClient;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void addUser(StockUser user) {
        user.getCompany().setStatus(CompanyStatus.NOT_VERIFIED);

        companyDao.add(user.getCompany());
        user.setCompanyId(user.getCompany().getId());
        if (user.getCompany().getCompanyAddress() != null) {
            user.getCompany().getCompanyAddress().setCompanyId(user.getCompanyId());
            addressDao.addAddress(user.getCompany().getCompanyAddress());
            user.getCompany().setAddressId(user.getCompany().getCompanyAddress().getId());
            companyDao.update(user.getCompany());
        }
        userDao.add(user);

        var listRole = new ArrayList<UserRole>();
        for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
            var userRole = new UserRole();
            userRole.setRole(Role.valueOf(grantedAuthority.getAuthority()));
            userRole.setUserId(user.getId());
            listRole.add(userRole);
        }
        userRoleDao.add(listRole);
    }

    @Override
    @Transactional
    public void update(StockUser user) {
        userDao.update(user);
        user.getCompany().setAddressId(user.getCompany().getCompanyAddress().getId());
        companyDao.update(user.getCompany());
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        var user = userDao.getByUsernameWithRoles(username);
        if (user == null) {
            throw new UsernameNotFoundException("label.user.not_found");
        } else {
            return user;
        }
    }

    @Override
    public void deleteGenerateKeys() {
        keysDao.deleteGenerateKeysByDate();
    }

    @Override
    @Transactional
    public void sendLinkResetPassword(String username) {
        var user = userDao.getByUsernameWithRoles(username);
        if (user != null) {
            var key = keysDao.generateKey();
            user.setKeyId(key.getId());
            userDao.updateKeyById(user);

            emailClient.sendMessage(
                    LabelUtil.parseString(user.getCompany().getLanguage()),
                    user.getUsername(),
                    EmailType.LINK_FOR_PASSWORD_RESET,
                    Map.of("code", key.getGeneratedKey(), "email", user.getUsername())
            );
        }
    }

    @Override
    @Transactional
    public boolean changePassword(String email, String code) {
        if (keysDao.checkResetKey(email, code)) {
            var user = userDao.getByUsernameWithRoles(email);
            var newPassword = Generator.generatePass(8);
            user.setPassword(passwordEncoder.encode(newPassword));
            userDao.updatePassword(user);
            keysDao.deleteGenerateKey(code);
            user.setPassword(newPassword);

            var params = new HashMap<String, Object>();
            params.put("username", user.getUsername());
            params.put("password", user.getPassword());

            emailClient.sendMessage(
                    LabelUtil.parseString(user.getCompany().getLanguage()),
                    user.getUsername(),
                    EmailType.PASSWORD_RESET,
                    params
            );
            return true;
        } else {
            return false;
        }
    }

    @Override
    public StockUser getByUsername(String username) {
        return userDao.getByUsernameWithRoles(username);
    }
}
