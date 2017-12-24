package com.iflytek.msp.lfasr;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.alibaba.fastjson.JSON;
import com.iflytek.msp.cpdb.lfasr.client.LfasrClientImp;
import com.iflytek.msp.cpdb.lfasr.exception.LfasrException;
import com.iflytek.msp.cpdb.lfasr.model.LfasrType;
import com.iflytek.msp.cpdb.lfasr.model.Message;
import com.iflytek.msp.dao.RecordListMongoDao;
import com.iflytek.msp.po.RecordList;
import com.iflytek.msp.util.FileDownload;

public class Xunfei {

	// 原始音频存放地址
	// private static final String local_file =
	// "F:/share_centos/8-201707241824025297.mp3";
	/*
	 * 转写类型选择：标准版和电话版分别为： LfasrType.LFASR_STANDARD_RECORDED_AUDIO 和
	 * LfasrType.LFASR_TELEPHONY_RECORDED_AUDIO
	 */
	private final LfasrType type = LfasrType.LFASR_STANDARD_RECORDED_AUDIO;
	// 等待时长（秒）
	private int sleepSecond = 2;

	private int limitQueryTime = 9;

	// 首次默认查询间隔时间
	private int firstIntervalTime = 30;

	//轮询时间策略
	private HashMap<Integer, Integer> intervalTimes = new HashMap<>();

	private String basePath = "http://nineton.oss-cn-hangzhou.aliyuncs.com/";

	private String tempDir = "tmp/download";

	private HashMap<String, String> params = new HashMap<>();

	public static void main(String[] args) {
		Xunfei xf = new Xunfei();
		HashMap<String, String> params = xf.getParams();
		params.put("has_participle", "true");
		xf.setParams(params);
		// 查询时间间隔
		HashMap<Integer, Integer> intervalTimes = xf.getIntervalTimes();
		intervalTimes.put(0, 60 * 1);// 分钟
		intervalTimes.put(1, 60 * 5);
		intervalTimes.put(2, 60 * 10);
		intervalTimes.put(3, 60 * 30);
		intervalTimes.put(4, 60 * 60);// 1小时
		intervalTimes.put(5, 60 * 2 * 60);// 2小时
		intervalTimes.put(6, 60 * 6 * 60);// 6小时
		intervalTimes.put(7, 60 * 12 * 60);// 12小时
		intervalTimes.put(8, 60 * 24 * 60);// 24小时
		xf.setIntervalTimes(intervalTimes);
		
		// 初始化LFASR实例
		LfasrClientImp lc = null;
		try {
			lc = LfasrClientImp.initLfasrClient();
		} catch (LfasrException e) {
			// 初始化异常，解析异常描述信息
			Message initMsg = JSON.parseObject(e.getMessage(), Message.class);
			System.out.println("ecode=" + initMsg.getErr_no());
			System.out.println("failed=" + initMsg.getFailed());
			System.exit(0);
		}
		
		FileDown fileDown = new FileDown(xf);
		FileUpload fileUpload = new FileUpload(xf, lc);
		FileResult fileResult = new FileResult(xf, lc);
		while (true) {
			if (fileDown.isAlive() == false) {
				System.out.println("start filedown thread...");
				fileDown = null;
				fileDown = new FileDown(xf);
				fileDown.start();
			}else{
				System.out.println("-- filedown thread is running...");
			}
			
			if (fileUpload.isAlive() == false) {
				fileUpload = null;
				fileUpload = new FileUpload(xf, lc);
				System.out.println("start upload thread...");
				fileUpload.start();
			}else{
				System.out.println("-- fileUpload thread is running...");
			}
			if (fileResult.isAlive() == false) {
				System.out.println("start query result thread...");
				fileResult = null;
				fileResult = new FileResult(xf, lc);
				fileResult.start();
			}else{
				System.out.println("-- fileResult thread is running...");
			}
			
			try {
				System.out.println("....main sleep....");
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public int getSleepSecond() {
		return sleepSecond;
	}

	public void setSleepSecond(int sleepSecond) {
		this.sleepSecond = sleepSecond;
	}

	public int getLimitQueryTime() {
		return limitQueryTime;
	}

	public void setLimitQueryTime(int limitQueryTime) {
		this.limitQueryTime = limitQueryTime;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public LfasrType getType() {
		return type;
	}

	public String getTempDir() {
		return tempDir;
	}

	public void setTempDir(String tempDir) {
		this.tempDir = tempDir;
	}

	public HashMap<String, String> getParams() {
		return params;
	}

	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}

	public int getFirstIntervalTime() {
		return firstIntervalTime;
	}

	public void setFirstIntervalTime(int firstIntervalTime) {
		this.firstIntervalTime = firstIntervalTime;
	}

	public HashMap<Integer, Integer> getIntervalTimes() {
		return intervalTimes;
	}

	public void setIntervalTimes(HashMap<Integer, Integer> intervalTimes) {
		this.intervalTimes = intervalTimes;
	}

	
}
