package com.iflytek.msp.lfasr;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleTest {
	public static void main(String[] args){
		Runnable runnable = new Runnable() {  
            public void run() {  
                // task to run goes here  
            	Task1 t1 = new Task1();
            	t1.test();
            }  
        };  
        
        Runnable runnable2 = new Runnable() {  
            public void run() {  
                // task to run goes here  
            	Task2 t2 = new Task2();
            	t2.test();
            }  
        };  

        ScheduledExecutorService service = Executors  
        		.newSingleThreadScheduledExecutor();  
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
        service.scheduleAtFixedRate(runnable, 10, 1, TimeUnit.SECONDS); 
        service.scheduleAtFixedRate(runnable2, 2, 3, TimeUnit.SECONDS); 
	}
}
