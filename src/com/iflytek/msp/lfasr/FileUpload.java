package com.iflytek.msp.lfasr;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import com.iflytek.msp.cpdb.lfasr.client.LfasrClientImp;
import com.iflytek.msp.cpdb.lfasr.exception.LfasrException;
import com.iflytek.msp.cpdb.lfasr.model.Message;
import com.iflytek.msp.dao.RecordListMongoDao;
import com.iflytek.msp.po.RecordList;

public class FileUpload extends Thread{

	private Xunfei xf = null; 
	private LfasrClientImp lc = null; 
	
	public FileUpload(Xunfei xf, LfasrClientImp lc){
		this.xf = xf;
		this.lc = lc;
	}
	
	@Override
	public void run(){
		upload();
	}
	
	public Boolean upload(){
		RecordListMongoDao recordDao = new RecordListMongoDao();
		//获取已下载的文档
		RecordList record = recordDao.getRecord(5);
		if(record == null){
			return false;
		}
		
		// 获取上传任务ID
		String local_file = record.getTempPath();
		String task_id = "";
		
		try {
			//正在上傳
			recordDao.updateUploadStatus(record.getMonId(), 2, "开始上传");
			// 上传音频文件
			Message uploadMsg = lc.lfasrUpload(local_file, xf.getType(), xf.getParams());
			
			// 判断返回值
			int ok = uploadMsg.getOk();
			if (ok == 0) {
				// 创建任务成功
				task_id = uploadMsg.getData();
				System.out.println("task_id=" + task_id);
				
				//保存记录的任务ID和下次查询时间
				int tmpIntervalTime = record.getFileSize() / 1024 / 1024;
				tmpIntervalTime = tmpIntervalTime <= xf.getFirstIntervalTime() ? xf.getFirstIntervalTime() : tmpIntervalTime;
				int next_query_time = (int)(new Date().getTime()/1000) + tmpIntervalTime;
				recordDao.updateUploadStatus(record.getMonId(), 3, task_id, next_query_time, "上传成功");
				
				//删除文件
				if (local_file != "" && local_file != null) {
					File file = new File(local_file);
					if(file != null && file.exists()){
						file.delete();
					}
				}
				
			} else {
				// 创建任务失败-服务端异常
				System.out.println("ecode=" + uploadMsg.getErr_no());
				System.out.println("failed=" + uploadMsg.getFailed());
				
				recordDao.updateUploadStatus(record.getMonId(), -1, "ecode=" + uploadMsg.getErr_no()+"failed=" + uploadMsg.getFailed());
				return false;
			}
		} catch (LfasrException e) {
			// 上传异常，解析异常描述信息
			Message uploadMsg = JSON.parseObject(e.getMessage(), Message.class);
			System.out.println("ecode=" + uploadMsg.getErr_no());
			System.out.println("failed=" + uploadMsg.getFailed());					
			
			recordDao.updateUploadStatus(record.getMonId(), -1, "ecode=" + uploadMsg.getErr_no()+"failed=" + uploadMsg.getFailed());
			return false;
		}
		return true;
	}
}
