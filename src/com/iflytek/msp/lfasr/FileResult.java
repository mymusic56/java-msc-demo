package com.iflytek.msp.lfasr;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.iflytek.msp.cpdb.lfasr.client.LfasrClientImp;
import com.iflytek.msp.cpdb.lfasr.exception.LfasrException;
import com.iflytek.msp.cpdb.lfasr.model.Message;
import com.iflytek.msp.cpdb.lfasr.model.ProgressStatus;
import com.iflytek.msp.dao.RecordListMongoDao;
import com.iflytek.msp.po.RecordList;
import com.iflytek.msp.po.jsonbean.Content;
import com.iflytek.msp.po.jsonbean.WordsResultList;

public class FileResult extends Thread{
	
	private Xunfei xf = null; 
	private LfasrClientImp lc = null; 
	
	public FileResult(Xunfei xf, LfasrClientImp lc){
		this.xf = xf;
		this.lc = lc;
	}
	@Override
	public void run(){
		getResult();
	}
	public int getDateTime(){
		return (int)(new Date().getTime()/1000);
	}
	public Boolean getResult(){
		int times = getDateTime();
		RecordListMongoDao recordDao = new RecordListMongoDao();
		RecordList record = recordDao.getRecord(3, times);
		if (record == null) {
			return false;
		}
		String task_id = record.getTaskId();
		System.out.println("查询处理状态， 任务ID："+task_id);
		//处理结果状态
		ProgressStatus progressStatus = null;
		//JSON数据解析结果
		HashMap<String, String> jsonParseRes = null;
		//音频上传结果
		Message uploadMsg = null;
		//进度查询
		Message progressMsg = null;
		//处理结果
		Message resultMsg = null;
		//是否转换成文字，0：未处理，1：已完成，
		//2：正在上传第三方服务器，3：等待异步处理, 
		//-1:上传第三方服务器失败， 4：异常（长时间未处理）, 
		//5:已下载至本地，6：已处理完成，等待查询结果
		try {
			// 获取处理进度
			progressMsg = lc.lfasrGetProgress(task_id);
			
			// 如果返回状态不等于0，则任务失败
			if (progressMsg.getOk() != 0) {
				System.out.println("task was fail. task_id:" + task_id);
				System.out.println("ecode=" + progressMsg.getErr_no());
				System.out.println("failed=" + progressMsg.getFailed());
				
				// 服务端处理异常-服务端内部有重试机制（不排查极端无法恢复的任务）
				// 客户端可根据实际情况选择：
				// 1. 客户端循环重试获取进度
				// 2. 退出程序，反馈问题
				
				//标记失败
				recordDao.updateRecordTextInfo(record.getMonId(), 4, "Status Query: ecode=" + progressMsg.getErr_no() + "failed=" + progressMsg.getFailed(), "");
			} else {
				progressStatus = JSON.parseObject(
						progressMsg.getData(), ProgressStatus.class);
				if (progressStatus.getStatus() == 9) {
					// 处理完成
					System.out.println("task was completed. task_id:" + task_id);
					//准备获取结果
					
//					recordDao.updateUploadStatus(record.getMonId(), 6, "Status Query: 数据已处理完成，等待写入数据库");
				} else {
					// 未处理完成
					System.out.println("task was incomplete. task_id:"
							+ task_id + ", status:"
							+ progressStatus.getDesc());
					
					//更新下次处理时间
					//超过查询次数， 或者没有配置查询时间间隔， 标记异常
					if(record.getQueryTimes() > xf.getLimitQueryTime() || !xf.getIntervalTimes().containsKey(record.getQueryTimes())){
						recordDao.updateUploadStatus(record.getMonId(), 4, "Status Query: 超过查询次数");
					}else{
						//更新下次处理时间
						int tmpIntervalTime = xf.getIntervalTimes().get(record.getQueryTimes());
						
						times = getDateTime();
						
						recordDao.updateNextQueryTime(record.getMonId(), times + tmpIntervalTime, record.getQueryTimes());
					}
					//没有找到已处理完成的数据直接结束
					return true;
				}
			}
		} catch (LfasrException e) {
			// 获取进度异常处理，根据返回信息排查问题后，再次进行获取
			progressMsg = JSON.parseObject(e.getMessage(),
					Message.class);
			System.out.println("ecode=" + progressMsg.getErr_no());
			System.out.println("failed=" + progressMsg.getFailed());
			
			recordDao.updateRecordTextInfo(record.getMonId(), 4, "Status Query: ecode=" + progressMsg.getErr_no() + "failed=" + progressMsg.getFailed(), "");
		}
			
		/************************************************/
		
		/*
		 *  操作三：获取任务结果
		 */
		try {
			System.out.println("获取处理结果， 任务ID："+task_id);
			resultMsg = lc.lfasrGetResult(task_id);
			System.out.println(resultMsg.getData());
			// 如果返回状态等于0，则任务处理成功
			if (resultMsg.getOk() == 0) {
				// 打印转写结果
				//获取解析后的数据
				jsonParseRes = parseJson(resultMsg.getData());
				
				//保存解析后的数据
				recordDao.updateRecordTextInfo(record.getMonId(), 1, jsonParseRes.get("originalStr"), jsonParseRes.get("segementStr"));
				
				System.out.println(resultMsg.getData());
			} else {
				// 转写失败，根据失败信息进行处理
				System.out.println("ecode=" + resultMsg.getErr_no());
				System.out.println("failed=" + resultMsg.getFailed());
				
				//保存解析后的数据
				recordDao.updateRecordTextInfo(record.getMonId(), 4, "Result Query: ecode=" + resultMsg.getErr_no()+"failed=" + resultMsg.getFailed(), "");
				
			}
		} catch (LfasrException e) {
			// 获取结果异常处理，解析异常描述信息
			resultMsg = JSON.parseObject(e.getMessage(),
					Message.class);
			System.out.println("ecode=" + resultMsg.getErr_no());
			System.out.println("failed=" + resultMsg.getFailed());
			recordDao.updateRecordTextInfo(record.getMonId(), 4, "Result Query:ecode=" + resultMsg.getErr_no()+"failed=" + resultMsg.getFailed(), "");
		}
		return true;
	}
	/**
	 * 解析json数据
	 * 
	 * @param str
	 * @return
	 */
	public HashMap<String, String> parseJson(String str) {
		Gson gson = new Gson();
		// 把JSON数据转化为对象
		List<Content> list = new ArrayList<>();
		JsonArray array = new JsonParser().parse(str).getAsJsonArray();

		String segementStr = "";
		String originalStr = "";
		for (int i = 0; i < array.size(); i++) {

			Content content = gson.fromJson(array.get(i), Content.class);
			List<WordsResultList> wordsResultList = content
					.getWordsResultList();
			// 获取原始字符串
			originalStr += content.getOnebest();
			// 獲取分詞后的字符串
			for (int j = 0; j < wordsResultList.size(); j++) {
				segementStr += " " + wordsResultList.get(j).getWordsName();
			}
		}
		HashMap<String, String> res = new HashMap<>();
		res.put("originalStr", originalStr);
		res.put("segementStr", segementStr);
		return res;
	}
}
