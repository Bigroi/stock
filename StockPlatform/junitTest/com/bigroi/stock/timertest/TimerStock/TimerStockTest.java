package com.bigroi.stock.timertest.TimerStock;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TimerStockTest {
	
	@SuppressWarnings("resource")
	@Test
	public void start() throws InterruptedException{
		new ClassPathXmlApplicationContext("spring-timer.xml");
		Thread.sleep(50000);

	}

}
