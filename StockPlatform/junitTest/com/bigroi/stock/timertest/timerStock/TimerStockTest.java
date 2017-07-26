package com.bigroi.stock.timertest.timerStock;

import org.junit.Test;

import com.bigroi.stock.listeners.CronJob;

public class TimerStockTest {
	

	@Test
	public void start() throws InterruptedException{
		Thread myThread = new Thread();
		 new CronJob(myThread);
		

	}

}
