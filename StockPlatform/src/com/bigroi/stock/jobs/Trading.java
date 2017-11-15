package com.bigroi.stock.jobs;

import com.bigroi.stock.jobs.trade.TradeJob;

public class Trading implements Runnable {
	
	@Override
	public void run() {
		new TradeJob().run();
	}

}
