package com.iflytek.msp.lfasr;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4jTest {
	public static void main(String args[]) {
		PropertyConfigurator.configure("source\\log4j2.properties");
		Logger logger = Logger.getLogger(Log4jTest.class);

		logger.info(" debug ");
//		logger.info(String.format("[COMPENT]-%s [PROCESS]-%s [ID]-%s [STATUS]-%s [MEASURE]-%s [DEF]-%s",
//				new Object[] { "CLIENT", "Test2", "", "", "(-1) ms",
//						"{\"ok\":\"-1\", \"err_no\":\"26201\", \"failed\":\"转写参数上传文件不能为空或文件不存在!\", \"data\":\"\"}" }));
		
	}
}
