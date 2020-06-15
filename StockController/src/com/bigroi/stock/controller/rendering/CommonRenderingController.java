package com.bigroi.stock.controller.rendering;

import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.controller.BaseRenderingController;
import com.bigroi.stock.security.AuthenticationHandler;
import com.bigroi.stock.service.UserService;
import com.bigroi.stock.util.LabelUtil;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class CommonRenderingController extends BaseRenderingController implements ErrorController {

    private static final String RESET_PASSW_PAGE = "resetPassw";

    private final UserService userService;

    public CommonRenderingController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        var statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == 404) {
            return createModelAndView("404");
        }
        return createModelAndView("500");

    }

    @RequestMapping("/Login")
    public ModelAndView login() {
        return createModelAndView("login");
    }

    @RequestMapping("/AnotherBrowser/")
    public ModelAndView anotherBrowser() {
        return createModelAndView("anotherBrowser");
    }

    @RequestMapping("/")
    public ModelAndView welcome() {
        var url = "Index?lang=" + getLanguage().toString().toLowerCase();
        return new ModelAndView("redirect:" + url);
    }

    @RequestMapping("/robots.txt")
    public void robot(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var path = request.getServletContext().getRealPath("robots.txt");
        try (var inputStream = new FileInputStream(path);
             var out = response.getOutputStream()
        ) {
            var buffer = new byte[inputStream.available()];
            var count = inputStream.read(buffer);
            if (count != buffer.length) {
                throw new RuntimeException();
            }
            response.setContentType("text/plain");
            out.write(buffer);
        }
    }

    @RequestMapping("/Index")
    public ModelAndView index(
            @RequestParam("lang") String locale,
            Authentication loggedInUser
    ) {
        setLanguage(LabelUtil.parseString(locale));
        if (loggedInUser != null) {
            var user = loggedInUser.getPrincipal();
            if (user instanceof StockUser) {
                return new ModelAndView("redirect:" + AuthenticationHandler.PRODUCT_LIST_SPR);
            }
        }
        return createModelAndView("index");
    }

    @RequestMapping("/Registration")
    public ModelAndView goToRegistrationPage() {
        return createModelAndView("registration");
    }

    @RequestMapping(value = "/ResetForm")
    public ModelAndView resetForm() {
        return createModelAndView("resetForm");
    }

    @RequestMapping(value = "/ResetPassword")
    public ModelAndView resetPassword(@RequestParam("code") String code, @RequestParam("email") String email) {
        var modelAndView = createModelAndView(RESET_PASSW_PAGE);
        var message = userService.changePassword(email, code)
                ? labelService.getLabel(RESET_PASSW_PAGE, "success", getLanguage())
                : labelService.getLabel(RESET_PASSW_PAGE, "error", getLanguage());

        modelAndView.addObject("message", message).addObject("user", null);
        return modelAndView;
    }

    @RequestMapping("/account/Form")
    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public ModelAndView form() {
        var modelAndView = createModelAndView("account");
        modelAndView.addObject("languages", LabelUtil.getPassibleLanguages(getLanguage()));
        return modelAndView;
    }
}
