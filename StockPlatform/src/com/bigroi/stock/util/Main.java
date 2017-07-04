package com.bigroi.stock.util;

import java.io.IOException;
//import java.util.Map;

import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.trade.EmailForConfirmation;

public class Main {

	public static void main(String[] args) throws IOException, DaoException{
//		String fileName = "customerConfirmationUTF8.txt";
//		Map<String, String> map = MessageFromFile.read(fileName);
//		
//		System.out.println(map.get("subject"));
//		System.out.println(map.get("text"));
//		System.out.println("----------");
		new EmailForConfirmation().send();

	}

}
