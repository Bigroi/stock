package com.bigroi.stock.messager.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import com.bigroi.stock.util.exception.StockRuntimeException;

public class MessageTemplate {

	private final String text;
	
	private final String subject;
	
	public MessageTemplate(String fileName, Locale locale, String fileExtention){
		fileName = fileName + "-" + locale + "." + fileExtention;
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName),
						StandardCharsets.UTF_8))) {
			subject = reader.readLine();
			StringBuilder textBuld = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				if (textBuld.length() == 0) {
					textBuld.append(line);
				} else {
					textBuld.append(System.lineSeparator()).append(line);
				}
			}
			text = textBuld.toString();
		} catch (IOException e) {
			throw new StockRuntimeException(e);
		}
	}
	
	public String getText() {
		return text;
	}
	
	public String getSubject() {
		return subject;
	}
	
}
