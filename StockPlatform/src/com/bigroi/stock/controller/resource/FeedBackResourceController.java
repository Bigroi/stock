package com.bigroi.stock.controller.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.bean.ui.FeedBack;
import com.bigroi.stock.controller.BaseResourseController;
import com.bigroi.stock.json.GsonUtil;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.messager.message.FeedBackMessage;
import com.bigroi.stock.messager.message.MessageException;

@Controller
@RequestMapping("feedback/json")
public class FeedBackResourceController extends BaseResourseController {

	private static final String FB_SUCCESS_LABEL = "label.account.fb_success";
	
	@Autowired
	private FeedBackMessage feedBackMessage;
	
	@RequestMapping("Form.spr")
	@ResponseBody
	public String getForm(Authentication loggedInUser){
		FeedBack message = new FeedBack();
		if (loggedInUser != null){ 
			Object user = loggedInUser.getPrincipal();
			if (user instanceof StockUser){
				message.setEmail(((StockUser) user).getUsername());
		
			}
		}
		return new ResultBean(1, message, null).toString();
	}
	
	@RequestMapping("Save.spr")
	@ResponseBody
	public String getForm(String json) throws MessageException{
		FeedBack message = GsonUtil.getGson().fromJson(json, FeedBack.class);
		feedBackMessage.send(message);
		return new ResultBean(1, message, FB_SUCCESS_LABEL).toString();
	}
	
}
