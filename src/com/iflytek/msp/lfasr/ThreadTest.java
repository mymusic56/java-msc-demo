package com.iflytek.msp.lfasr;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadTest {
	public static void main(String[] args){
		Thread1 t1 = new Thread1();
		
		System.out.println("before start Thread1 t1.isAlive() :" + t1.isAlive());
		while(true){
			if(t1.isAlive() == false){
				System.out.println("创建子线程。。。");
				t1 = new Thread1();
				t1.start();
			}else{
				System.out.println("子线程已存在。。。");
				
			}
			try {
				System.out.println("开始睡眠。。。");
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
