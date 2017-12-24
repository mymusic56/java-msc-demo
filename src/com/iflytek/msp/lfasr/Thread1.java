package com.iflytek.msp.lfasr;

import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Thread1 extends Thread{
	@Override
	public void run(){
		PropertyConfigurator.configure("source\\log4j2.properties");
		Logger logger = Logger.getLogger(Log4jTest.class);

		for (int i = 0; i < 10; i++ ) {
			String str = "Thread-[2]:"+String.valueOf((new Date().getTime()));
			System.out.println(str);
			logger.info(str);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
