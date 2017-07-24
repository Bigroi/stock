package com.bigroi.stock.bean;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TimerStock {
	
	 TimerStock timerStock;
	
	public TimerStock() {//init()
	}
	
	public TimerStock(Runnable runnable) {
		 timerStock = (TimerStock) getContext();
		 runnable = new Runnable() {
			@Override
			public void run() {
				System.out.println("Executed task on: " + new Date());
			}	
		};
	}
	
	public ApplicationContext getContext(){
		return  new ClassPathXmlApplicationContext("spring-timer.xml");
	}
	
	public static void main(String[] args){
		Thread thread = new Thread();
		TimerStock ts = new TimerStock(thread);
		System.out.println("executed: "+ts);
		
	}
	
	
//---------------------------JavaSE------------------------------------	
	
	/*public TimerStock(Runnable runnable) {
		 runnable = new Runnable() {
			@Override
			public void run() {
				try {
					timerTask();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				 System.out.println("запустился run() Runnable");
			}
		};
		runnable.run();
		System.out.println("runnable.run();");
		
	}
	
	
	public void timerTask() throws InterruptedException{
		  Timer time = new Timer();
		time.schedule(new TimerTask() {
			@Override
			public void run() {
				Date now = new Date();
		        System.out.println("new Date(): " + now);
			}
		}, 5000);// created task on 5 sec
		
		for (int i = 0; i <= 5; i++) {
           Thread.sleep(3000);  
           System.out.println("Execution in Main Thread: " + i);
           if (i == 5) {
               System.out.println("Application Terminates");
               //System.exit(0);
           }
		}
	}
	
	public static void main(String[] args){
		Thread td = new Thread();
		TimerStock ts = new TimerStock(td);
		System.out.println("executed :"+ ts);
	}
*/
}
