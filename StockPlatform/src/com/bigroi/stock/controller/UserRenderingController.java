package com.bigroi.stock.controller;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.User;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;
import com.bigroi.stock.messager.Message;

@Controller
public class UserRenderingController {

	private static final Logger logger = Logger.getLogger(UserRenderingController.class);

	@RequestMapping(value = "/ChangeUserPass.spr")
	public ModelAndView listOfUser() throws DaoException {
		logger.info("exection UserRenderingController.listOfUser");
		List<User> user = DaoFactory.getUserDao().getAllUser();
		logger.info("exection UserRenderingController.listOfUser successfully finished");
		return new ModelAndView("changeUserPass", "listOfUser", user);
	}

	@RequestMapping(value = "/ChangeThisUserPass.spr")
	public ModelAndView changePass(@RequestParam("login") String login) throws DaoException, IOException {
		logger.info("exection UserRenderingController.changePass");
		logger.info(login);
		User user = DaoFactory.getUserDao().getByLogin(login);
		user.setPassword(generatePass(8));
		DaoFactory.getUserDao().updateById(user);
		sendMessage(user);
		logger.info("exection UserRenderingController.changePass successfully finished");
		return listOfUser();
	}
	
	private String generatePass(int lengthOfPass) {
		String charsCaps = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String chars = "abcdefghijklmnopqrstuvwxyz";
		String nums = "0123456789";
		String symbols = "!@#$%^&*_=+-/ˆ.?<>)";

		String passSymbols = charsCaps + chars + nums + symbols;
		Random rnd = new Random();

		char[] password = new char[lengthOfPass];
		for (int i = 0; i < lengthOfPass; i++) {
			password[i] = passSymbols.charAt(rnd.nextInt(passSymbols.length()));
		}
		String newPass = new String(password);
		return newPass;
	}
	
	private void sendMessage(User user) throws IOException, DaoException{
		new Message().sendMessageChangeUserPass(user);
	}
}
