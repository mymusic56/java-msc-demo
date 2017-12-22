package com.iflytek.msp.lfasr;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.iflytek.msp.cpdb.lfasr.client.LfasrClientImp;
import com.iflytek.msp.cpdb.lfasr.exception.LfasrException;
import com.iflytek.msp.cpdb.lfasr.model.LfasrType;
import com.iflytek.msp.cpdb.lfasr.model.Message;
import com.iflytek.msp.cpdb.lfasr.model.ProgressStatus;
import com.iflytek.msp.dao.RecordListDao;
import com.iflytek.msp.dao.RecordListExtraDao;
import com.iflytek.msp.dao.RecordListMongoDao;
import com.iflytek.msp.po.RecordList;
import com.iflytek.msp.po.RecordListExtra;
import com.iflytek.msp.po.jsonbean.Content;
import com.iflytek.msp.po.jsonbean.WordsResultList;
import com.iflytek.msp.util.FileDownload;


public class IndexMongo {
	
	// 原始音频存放地址
//	private static final String local_file = "F:/share_centos/8-201707241824025297.mp3";
	/*
	 * 转写类型选择：标准版和电话版分别为：
	 * LfasrType.LFASR_STANDARD_RECORDED_AUDIO 和 LfasrType.LFASR_TELEPHONY_RECORDED_AUDIO
	 * */
	private static final LfasrType type = LfasrType.LFASR_STANDARD_RECORDED_AUDIO;
	// 等待时长（秒）
	private static int sleepSecond = 2;
	
	private static int limitQueryTime = 9;
	
	//默认查询间隔时间
	private static int intervalTime = 30;
	
	private static String basePath = "http://nineton.oss-cn-hangzhou.aliyuncs.com/";
	
	public static void main(String[] args) {
		
		PropertyConfigurator.configure("source/log4j.properties");
		
		//查询次数对应的时间间隔
		Logger logger = Logger.getLogger(IndexMongo.class);
		HashMap<Integer, Integer> intervalTimes = new HashMap<>();
		intervalTimes.put(0, 60*1);//分钟
		intervalTimes.put(1, 60*5);
		intervalTimes.put(2, 60*10);
		intervalTimes.put(3, 60*30);
		intervalTimes.put(4, 60*60);//1小时
		intervalTimes.put(5, 60*2*60);//2小时
		intervalTimes.put(6, 60*6*60);//6小时
		intervalTimes.put(7, 60*12*60);//12小时
		intervalTimes.put(8, 60*24*60);//24小时
		
		
		// 加载配置文件
//		PropertyConfigurator.configure("log4j.properties");
		
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
		IndexMongo index = new IndexMongo();
		
		//文件同步记录
		RecordList record = null;
		
		//远程下载下来的本地文件
		String filePath = "";
		String filename = "";
		String tempFileDir = "tmp/download";
		
		//文件上传状态
		HashMap<String, String> uploadRes = null;
		
		//获取处理结果
		HashMap<String, String> dealRes = null;
		
		//处理状态
		HashMap<String, String> dealSatus = null;
		
		//JSON数据解析结果
		HashMap<String, String> jsonParseRes = null;
		
		File file = null;//下载的文件对象
		int tmpIntervalTime = 0;//查询时间间隔
		int times = 0;//当前时间
		int next_query_time = 0;//下次查询时间
		
		//任务ID
		String task_id = "";
		//数据库连接对象
		RecordListMongoDao recordDao = new RecordListMongoDao();
		while(true){
			record = recordDao.getRecord(0);
			/*
			 * 操作一：
			 * 上传
			 * 文件处理状态 ： 0：未处理，1：已完成，2：正在上传第三方服务器，3：等待异步处理，  4：异常（长时间未处理）
			 */
			if(record != null){
				//下载文件
				System.out.println(record.getPath()+"---"+record.getHasConvert());
				//路径为空
				if("".equals(record.getPath())){
					
					//标记为异常
					recordDao.updateRecordTextInfo(record.getMonId(), 4, "文件路径为空", "");
					continue;
				}
				
//				String[] nameArr = record.getPath().split("/");
//				String na = nameArr[nameArr.length - 1];
				filename = record.getUserId()+"-"+String.valueOf(new Date().getTime())+"-"+record.getPath().substring(record.getPath().lastIndexOf("/")+1);
				
//				System.out.println("start download file: "+filename);
//				logger.info("start download file: "+filename);
				filePath = FileDownload.saveUrlAs(basePath+record.getPath(), tempFileDir, filename, "GET");
				System.out.println("end download file:"+filePath);
//				logger.info("end download file:"+filePath);
				if("".equals(filePath)){
					
					//标记为异常
					recordDao.updateRecordTextInfo(record.getMonId(), 4, "下载失败，文件路径为空["+basePath+record.getPath()+"]", "");
					
					continue;
				}
				
				
				//开始上传
				recordDao.updateUploadStatus(record.getMonId(), 2);
				uploadRes = index.upload(lc, filePath);
				
				//上传完成
				if(uploadRes != null && uploadRes.get("status") == "1"){
					//保存记录的任务ID和下次查询时间
					tmpIntervalTime = record.getFileSize() / 1024 / 1024;
					tmpIntervalTime = tmpIntervalTime <= intervalTime ? intervalTime : tmpIntervalTime;
					next_query_time = (int)(new Date().getTime()/1000) + tmpIntervalTime;
					recordDao.updateUploadStatus(record.getMonId(), 3, uploadRes.get("task_id"), next_query_time);
				}else{
					recordDao.updateUploadStatus(record.getMonId(), -1);
				}
				
				
				//删除文件
				if (filePath != "" && filePath != null) {
					file = new File(filePath);
					if(file != null && file.exists()){
						file.delete();
					}
				}
				 
			}else{
				logger.info("没有需要上传的数据！");

				System.out.println("没有需要上传的数据！");
			}
			
			
			/*
			 * 操作二： 获取处理状态
			 * 
			 * 如果已处理，就获取处理后的内容
			 * 获取第一条的处理状态
			 */
			
			times = (int)(new Date().getTime()/1000);
			record = recordDao.getRecord(3, times);
			if(record != null){
				task_id = record.getTaskId();
				dealSatus = index.getDealStatus(lc, task_id);
				
				/*
				 * 操作三： 获取处理结果
				 * 
				 * 自定义状态： 1:成功， 2：处理中，-1：失败
				 */
				if(dealSatus.get("status") == "1"){
					dealRes = index.getDealResultMsg(lc, task_id);
					
					//处理成功, 并更新数据库
//					int s = dealRes.get("status") == "1" ? 1 : -1;
					
					//获取解析后的数据
					jsonParseRes = index.parseJson(dealRes.get("data"));
					
					//保存解析后的数据
					recordDao.updateRecordTextInfo(record.getMonId(), 1, jsonParseRes.get("originalStr"), jsonParseRes.get("segementStr"));
					
				}else if(dealSatus.get("status") == "-1"){
					recordDao.updateRecordTextInfo(record.getMonId(), 3, dealSatus.get("data"), "");
					
				}else if(dealSatus.get("status") == "2"){
					
					//超过查询次数， 或者没有配置查询时间间隔， 标记异常
					if(record.getQueryTimes() > limitQueryTime || !intervalTimes.containsKey(record.getQueryTimes())){
						recordDao.updateUploadStatus(record.getMonId(), 4);
					}else{
						//更新下次处理时间
						tmpIntervalTime = intervalTimes.get(record.getQueryTimes());
						
						times = (int)(new Date().getTime()/1000);
						
						recordDao.updateNextQueryTime(record.getMonId(), times + tmpIntervalTime, record.getQueryTimes());
					}
				}
			}
			
			
			//每执行完一次睡眠20s.
			try {
				logger.info("休息中。。。");
				uploadRes = null;
				//获取处理结果
				dealRes = null;
				//处理状态
				dealSatus = null;
				//JSON数据解析结果
				jsonParseRes = null;
				Thread.sleep(sleepSecond * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	public HashMap<String, String> upload(LfasrClientImp lc, String local_file){
		try {
			lc = LfasrClientImp.initLfasrClient();
		} catch (LfasrException e) {
			// 初始化异常，解析异常描述信息
			Message initMsg = JSON.parseObject(e.getMessage(), Message.class);
			System.out.println("ecode=" + initMsg.getErr_no());
			System.out.println("failed=" + initMsg.getFailed());
		}
				
		// 获取上传任务ID
		String task_id = "";
		HashMap<String, String> params = new HashMap<>();
		params.put("has_participle", "true");
		
		HashMap<String, String> res = new HashMap<>();
		
		try {
			// 上传音频文件
			Message uploadMsg = lc.lfasrUpload(local_file, type, params);
			
			// 判断返回值
			int ok = uploadMsg.getOk();
			if (ok == 0) {
				// 创建任务成功
				task_id = uploadMsg.getData();
				res.put("status", "1");
				res.put("msg", "success");
				res.put("task_id", task_id);
//				System.out.println("task_id=" + task_id);
			} else {
				// 创建任务失败-服务端异常
//				System.out.println("ecode=" + uploadMsg.getErr_no());
//				System.out.println("failed=" + uploadMsg.getFailed());
				
				res.put("status", String.valueOf(uploadMsg.getErr_no()));
				res.put("msg", uploadMsg.getFailed());
				res.put("task_id", "");
			}
		} catch (LfasrException e) {
			// 上传异常，解析异常描述信息
			Message uploadMsg = JSON.parseObject(e.getMessage(), Message.class);
//			System.out.println("ecode=" + uploadMsg.getErr_no());
//			System.out.println("failed=" + uploadMsg.getFailed());					
			
			res.put("status", String.valueOf(uploadMsg.getErr_no()));
			res.put("msg", uploadMsg.getFailed());
			res.put("task_id", "");
		}
		
		return res;
	}
	
	public HashMap<String, String> getDealStatus(LfasrClientImp lc, String task_id){
		HashMap<String, String> res = new HashMap<>();
		try {
			// 获取处理进度
			Message progressMsg = lc.lfasrGetProgress(task_id);
					
			// 如果返回状态不等于0，则任务失败
			if (progressMsg.getOk() != 0) {
//				System.out.println("task was fail. task_id:" + task_id);
//				System.out.println("ecode=" + progressMsg.getErr_no());
//				System.out.println("failed=" + progressMsg.getFailed());
				
				
				res.put("status", "-1");
				res.put("msg", "failed");
				res.put("data", "ecode=" + progressMsg.getErr_no() + ",failed=" + progressMsg.getFailed());
				
				// 服务端处理异常-服务端内部有重试机制（不排查极端无法恢复的任务）
				// 客户端可根据实际情况选择：
				// 1. 客户端循环重试获取进度
				// 2. 退出程序，反馈问题
			} else {
				ProgressStatus progressStatus = JSON.parseObject(progressMsg.getData(), ProgressStatus.class);
				if (progressStatus.getStatus() == 9) {
					// 处理完成
//					System.out.println("task was completed. task_id:" + task_id);
					
					res.put("status", "1");
					res.put("msg", "success");
					res.put("data", "");
					
					
				} else {
					// 未处理完成
//					System.out.println("task was incomplete. task_id:" + task_id + ", status:" + progressStatus.getDesc());
					
					res.put("status", "2");
					res.put("msg", "success");
					res.put("data", "ecode=" + progressMsg.getErr_no() + ",failed=" + progressMsg.getFailed());
					
				}
			}
		} catch (LfasrException e) {
			// 获取进度异常处理，根据返回信息排查问题后，再次进行获取
			Message progressMsg = JSON.parseObject(e.getMessage(), Message.class);
//			System.out.println("ecode=" + progressMsg.getErr_no());
//			System.out.println("failed=" + progressMsg.getFailed());
			
			res.put("status", "-1");
			res.put("msg", "failed");
			res.put("data", "ecode=" + progressMsg.getErr_no() + ",failed=" + progressMsg.getFailed());
		}
		
		return res;
	}
	
	public HashMap<String, String> getDealResultMsg(LfasrClientImp lc, String task_id){
		// 获取任务结果
		HashMap<String, String> res = new HashMap<>();
		try {
			Message resultMsg = lc.lfasrGetResult(task_id);
//			System.out.println(resultMsg.getData());	
			// 如果返回状态等于0，则任务处理成功
			if (resultMsg.getOk() == 0) {
				// 打印转写结果
//				System.out.println(resultMsg.getData());
				
				res.put("status", "1");
				res.put("msg", "success");
				res.put("data", resultMsg.getData());
			} else {
				// 转写失败，根据失败信息进行处理
//				System.out.println("ecode=" + resultMsg.getErr_no());
//				System.out.println("failed=" + resultMsg.getFailed());
				
				res.put("status", "-1");
				res.put("msg", "failed");
				res.put("data", "ecode=" + resultMsg.getErr_no() + ",failed=" + resultMsg.getFailed());
			}
		} catch (LfasrException e) {
			// 获取结果异常处理，解析异常描述信息
			Message resultMsg = JSON.parseObject(e.getMessage(), Message.class);
//			System.out.println("ecode=" + resultMsg.getErr_no());
//			System.out.println("failed=" + resultMsg.getFailed());
			
			res.put("status", "-1");
			res.put("msg", "failed");
			res.put("data", "ecode=" + resultMsg.getErr_no() + ",failed=" + resultMsg.getFailed());
		}
		return res;
	}
	
	/**
	 * 解析json数据
	 * @param str
	 * @return
	 */
	public HashMap<String, String> parseJson(String str){
		Gson gson = new Gson();    
        //把JSON数据转化为对象
    	List<Content> list = new ArrayList<>();
    	JsonArray array = new JsonParser().parse(str).getAsJsonArray();
    	
    	String segementStr = "";
    	String originalStr = "";
    	for(int i=0; i < array.size(); i++){
    		
    		Content content = gson.fromJson(array.get(i), Content.class);
    		List<WordsResultList> wordsResultList = content.getWordsResultList();
    		//获取原始字符串
    		originalStr += content.getOnebest();
    		//獲取分詞后的字符串
    		for(int j=0; j < wordsResultList.size(); j++){
    			segementStr += " "+wordsResultList.get(j).getWordsName();
    		}
    	}
    	HashMap<String, String> res = new HashMap<>();
    	res.put("originalStr", originalStr);
    	res.put("segementStr", segementStr);
    	return res;
	}
	
	/**
	 * 用户存储用户数据的目录， 作备份使用
	 * @param userId
	 * @return
	 */
	public String getUserDir(int userId){	
		
		String uid = String.valueOf(userId);
		String dir = "";
		int len = uid.length();
		for (int i = 0; i < len; i ++) {
			if(i % 2 == 0){
				int endIndex = i + 2;
				if(endIndex > len){
					endIndex = len;
				}
				dir += String.format("%02d", Integer.valueOf(uid.substring(i, endIndex))) + "/";
				if(endIndex == len){
					break;
				}
			}
		}
		
		return dir;
	}
}
