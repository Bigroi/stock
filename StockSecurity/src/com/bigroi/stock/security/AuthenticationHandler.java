package com.bigroi.stock.security;

import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.util.LabelUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Repository
@Service
public class AuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

    public static final String PRODUCT_LIST_SPR = "/product/List";

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException {
        var message = "Bad credentials".equals(exception.getMessage())
                ? "label.index.login_error"
                : exception.getMessage();
        response.getWriter().append(new ResultBean(-1, message).toString());
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        var user = (StockUser) authentication.getPrincipal();
        var locale = LabelUtil.parseString(user.getCompany().getLanguage());
        request.getSession().setAttribute("lang", locale);
        response.getWriter().append(new ResultBean(0, PRODUCT_LIST_SPR, null).toString());
    }
}
