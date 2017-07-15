package com.bigroi.stock.util;

import static com.bigroi.stock.bean.common.MessagePart.SUBJECT;
import static com.bigroi.stock.bean.common.MessagePart.TEXT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.bigroi.stock.bean.common.MessagePart;

public class MessageFromFile {

	public static Map<MessagePart, String> read(String fileName) throws IOException {
		Map<MessagePart, String> map = new HashMap<>();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
				Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName),
				StandardCharsets.UTF_8))) {
			String subject = null;
			StringBuilder text = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				if (subject == null) {
					subject = line;
				} else if (text.length() == 0){
					text.append(line);
				} else {
					text.append(System.lineSeparator()).append(line);
				}
			}
			map.put(SUBJECT, subject);
			map.put(TEXT, text.toString());
		}
		return map;
	}
}
