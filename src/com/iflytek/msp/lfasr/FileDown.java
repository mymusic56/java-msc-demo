package com.iflytek.msp.lfasr;

import java.util.Date;
import com.iflytek.msp.dao.RecordListMongoDao;
import com.iflytek.msp.po.RecordList;
import com.iflytek.msp.util.FileDownload;

public class FileDown extends Thread{

	private Xunfei xf = null; 
	
	public FileDown(Xunfei xf){
		this.xf = xf;
	}
	
	@Override
	public void run(){
		System.out.println("file down ...............");
		download();
	}
	
	/**
	 * 完成下载工作后，把本地路径写入数据库
	 * @return
	 */
	public void download(){
		//获取待下载的数据
		RecordListMongoDao recordDao = new RecordListMongoDao();
		RecordList record = recordDao.getRecord(0);
		if(record != null){
			System.out.println(record.getPath()+"---"+record.getHasConvert());
			//路径为空
			if("".equals(record.getPath())){
				
				//标记为异常
				recordDao.updateRecordTextInfo(record.getMonId(), 4, "文件路径为空", "");
			}
			
//			String[] nameArr = record.getPath().split("/");
//			String na = nameArr[nameArr.length - 1];
			String filename = record.getUserId()+"-"+String.valueOf(new Date().getTime())+"-"+record.getPath().substring(record.getPath().lastIndexOf("/")+1);
			
//			System.out.println("start download file: "+filename);
//			logger.info("start download file: "+filename);
			String filePath = FileDownload.saveUrlAs(xf.getBasePath()+record.getPath(), xf.getTempDir(), filename, "GET");
			System.out.println("end download file:"+filePath);
//			logger.info("end download file:"+filePath);
			if("".equals(filePath)){
				
				//标记为异常
				recordDao.updateRecordTextInfo(record.getMonId(), 4, "下载失败，文件路径为空["+xf.getBasePath()+record.getPath()+"]", "");
			}
			
			//标记下载完成
			recordDao.updateRecordDownInfo(record.getMonId(), 5, "下载完成", filePath);
		}
		try {
			Thread.sleep(xf.getSleepSecond());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
