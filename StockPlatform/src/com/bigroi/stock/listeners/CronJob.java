package com.bigroi.stock.listeners;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class CronJob {

	public CronJob(Runnable runnable){
		runnable = new Runnable() {

			@SuppressWarnings("resource")
			@Override
			public void run() {
				new ClassPathXmlApplicationContext("spring-timer.xml");
				try {
					Thread.sleep(50000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		};
	}

}
