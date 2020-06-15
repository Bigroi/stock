package com.bigroi.stock.controller.resource;

import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.bean.ui.FeedBack;
import com.bigroi.stock.controller.BaseResourceController;
import com.bigroi.stock.json.GsonUtil;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.messager.email.EmailClient;
import com.bigroi.stock.messager.email.EmailType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("feedback/json")
public class FeedBackResourceController extends BaseResourceController {

    private static final String FB_SUCCESS_LABEL = "label.account.fb_success";

    private final EmailClient emailClient;

    public FeedBackResourceController(EmailClient emailClient) {
        this.emailClient = emailClient;
    }

    @RequestMapping("Form")
    @ResponseBody
    public String getForm(Authentication loggedInUser) {
        var message = new FeedBack();
        if (loggedInUser != null) {
            var user = loggedInUser.getPrincipal();
            if (user instanceof StockUser) {
                message.setEmail(((StockUser) user).getUsername());
            }
        }
        return new ResultBean(1, message, null).toString();
    }

    @RequestMapping("Save")
    @ResponseBody
    public String getForm(String json) {
        var message = GsonUtil.getGson().fromJson(json, FeedBack.class);
        emailClient.sendMessage(
                getLanguage(),
                emailClient.getSupport(),
                EmailType.FEED_BACK,
                Map.of("email", message.getEmail(), "message", message.getMessage())
        );
        return new ResultBean(2, message, FB_SUCCESS_LABEL).toString();
    }

}
