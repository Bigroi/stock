package com.bigroi.stock.controller.resource;

import com.bigroi.stock.bean.common.Role;
import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.controller.BaseResourceController;
import com.bigroi.stock.json.GsonUtil;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.security.AuthenticationHandler;
import com.bigroi.stock.service.CompanyService;
import com.bigroi.stock.service.UserService;
import com.bigroi.stock.util.LabelUtil;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/account/json")
public class AccountResourceController extends BaseResourceController {

    private final UserService userService;
    private final CompanyService companyService;
    private final PasswordEncoder passwordEncoder;

    public AccountResourceController(
            UserService userService,
            CompanyService companyService,
            PasswordEncoder passwordEncoder
    ) {
        this.userService = userService;
        this.companyService = companyService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "/Form")
    @ResponseBody
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public String accountPage(Authentication loggedInUser) {
        var user = (StockUser) loggedInUser.getPrincipal();
        user = user.cloneUser();
        user.setPassword("");
        user.setPasswordRepeat("");
        return new ResultBean(1, user, "").toString();
    }

    @RequestMapping(value = "/Save")
    @ResponseBody
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public String editAccount(@RequestParam("json") String json) {
        var newUser = GsonUtil.getGson().fromJson(json, StockUser.class);
        var loggedIn = (StockUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (newUser.getPassword() != null && !newUser.getPassword().equals("")) {
            if (!newUser.getPassword().equals(newUser.getPasswordRepeat())) {
                return new ResultBean(-1, "label.account.error_password").toString();
            }
            loggedIn.setPassword(passwordEncoder.encode(newUser.getPassword()));
        }

        if (!newUser.getUsername().equals(loggedIn.getUsername())
                && userService.getByUsername(newUser.getUsername()) != null) {
            return new ResultBean(-1, "label.account.error_login").toString();
        }

        var changeLanguage = !loggedIn.getCompany().getLanguage().equals(newUser.getCompany().getLanguage());

        loggedIn.getCompany().setPhone(newUser.getCompany().getPhone());
        loggedIn.getCompany().setLanguage(newUser.getCompany().getLanguage());
        loggedIn.setUsername(newUser.getUsername());
        userService.update(loggedIn);

        if (changeLanguage) {
            setLanguage(LabelUtil.parseString(newUser.getCompany().getLanguage()));
            return new ResultBean(0, null).toString();
        } else {
            return new ResultBean(1, loggedIn, "label.account.edit_success").toString();
        }
    }

    @RequestMapping(value = "/Registration")
    @ResponseBody
    public String registration(@RequestParam("json") String json) {

        var user = GsonUtil.getGson().fromJson(json, StockUser.class);
        user.addAuthority(new SimpleGrantedAuthority(Role.ROLE_USER.name()));

        if (userService.getByUsername(user.getUsername()) != null) {
            return new ResultBean(-1, "label.account.error_login").toString();
        }

        if (companyService.getByName(user.getCompany().getName()) != null) {
            return new ResultBean(-1, "label.account.error_name").toString();
        }

        if (companyService.getByRegNumber(user.getCompany().getRegNumber()) != null) {
            return new ResultBean(-1, "label.account.error_reg_number").toString();
        }

        if (!user.getPassword().equals(user.getPasswordRepeat())) {
            return new ResultBean(-1, "label.account.error_password").toString();
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setPasswordRepeat("");
        }
        user.setUsername(user.getUsername().toLowerCase());
        user.getCompany().setType("TRADER");
        user.getCompany().setLanguage(getLanguage().toString());

        userService.addUser(user);

        var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        return new ResultBean(0, AuthenticationHandler.PRODUCT_LIST_SPR, null).toString();
    }

    @RequestMapping(value = "/ResetPassword")
    @ResponseBody
    public String resetPassword(@RequestParam("json") String json) {
        var user = GsonUtil.getGson().fromJson(json, StockUser.class);
        userService.sendLinkResetPassword(user.getUsername());
        return new ResultBean(2, user, "label.account.password_reset").toString();
    }
}
