package com.bigroi.stock.dao.conection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatasourceHelper {
	
	private static DatasourceHelper instance;
	private Properties properties;

	public DatasourceHelper getInstance() throws IOException {
		if (instance == null) {
			synchronized (DatasourceHelper.class) {
				if (instance == null) {
					instance = new DatasourceHelper();
				}
			}
		}
		return instance;
	}

	public DatasourceHelper() throws IOException {
		InputStream inputStream = null;
		try {
			inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("datasourse.properties");
			properties = new Properties();
			properties.load(inputStream);
		} catch (IOException e) {

		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException ignore) {
				}
			}
		}
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

}
