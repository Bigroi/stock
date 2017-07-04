package com.bigroi.stock.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MessageFromFile {

	public static Map<String, String> read(String fileName) throws IOException {
		Map<String, String> map = new HashMap<>();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
				Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName),
				StandardCharsets.UTF_8))) {
			String subject = "";
			String text = "";
			boolean flag = true;
			String line;
			while ((line = reader.readLine()) != null) {
				if (flag) {
					subject = line;
					flag = false;
				} else {
					text = text + line + System.lineSeparator();
				}
			}
			map.put("subject", subject);
			if (text.endsWith(System.lineSeparator())) {
				text = text.substring(0, text.length() - 2);
			}
			map.put("text", text);
		}
		return map;
	}
}
