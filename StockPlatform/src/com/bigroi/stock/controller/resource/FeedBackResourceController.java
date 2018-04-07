package com.bigroi.stock.controller.resource;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.FeedBack;
import com.bigroi.stock.bean.StockUser;
import com.bigroi.stock.json.GsonUtil;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.messager.message.Message;
import com.bigroi.stock.messager.message.MessageException;

@Controller
@RequestMapping("feedback/json")
public class FeedBackResourceController extends BaseResourseController {

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
		return new ResultBean(1, message, "").toString();
	}
	
	@RequestMapping("Save.spr")
	@ResponseBody
	public String getForm(String json) throws MessageException{
		FeedBack message = GsonUtil.getGson().fromJson(json, FeedBack.class);
		Message<FeedBack> feedBackMessage = MessagerFactory.getFeedBackMessage();
		feedBackMessage.setDataObject(message);
		feedBackMessage.send();
		return new ResultBean(1, "lable.account.fb_success").toString();
	}
	
}
