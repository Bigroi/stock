package com.bigroi.stock.timertest.TimerStock;

import org.junit.Test;

import com.bigroi.stock.bean.TimerStock;

public class TimerStockTest {
	
	@Test
	public void start(){
		Thread thread = new Thread();
		TimerStock ts = new TimerStock(thread);
		

	}

}
