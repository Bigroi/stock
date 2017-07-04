package com.bigroi.stock.util;

import java.io.IOException;
import java.util.Map;

public class main {

	public static void main(String[] args) throws IOException{
		String fileName = "customerMessageUTF8.txt";
		Map<String, String> map = messageFromFile.read(fileName);
		
		System.out.println(map.get("subject"));
		System.out.println(map.get("text"));
		System.out.println("----------");

	}

}
