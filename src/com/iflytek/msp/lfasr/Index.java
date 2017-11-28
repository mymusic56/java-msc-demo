package com.iflytek.msp.lfasr;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.PropertyConfigurator;

import com.alibaba.fastjson.JSON;
import com.iflytek.msp.cpdb.lfasr.client.LfasrClientImp;
import com.iflytek.msp.cpdb.lfasr.exception.LfasrException;
import com.iflytek.msp.cpdb.lfasr.model.LfasrType;
import com.iflytek.msp.cpdb.lfasr.model.Message;
import com.iflytek.msp.cpdb.lfasr.model.ProgressStatus;
import com.iflytek.msp.dao.RecordListDao;
import com.iflytek.msp.po.RecordList;
import com.iflytek.msp.util.FileDownload;


public class Index {
	
	// 原始音频存放地址
//	private static final String local_file = "F:/share_centos/8-201707241824025297.mp3";
	/*
	 * 转写类型选择：标准版和电话版分别为：
	 * LfasrType.LFASR_STANDARD_RECORDED_AUDIO 和 LfasrType.LFASR_TELEPHONY_RECORDED_AUDIO
	 * */
	private static final LfasrType type = LfasrType.LFASR_STANDARD_RECORDED_AUDIO;
	// 等待时长（秒）
	private static int sleepSecond = 20;
	
	private static int limitQueryTime = 6;
	
	public static void main(String[] args) {
		//查询次数对应的时间间隔
		
		HashMap<Integer, Integer> intervalTimes = new HashMap<>();
		intervalTimes.put(1, 60*1);
		intervalTimes.put(2, 60*5);
		intervalTimes.put(3, 60*10);
		intervalTimes.put(4, 60*30);
		intervalTimes.put(5, 60*60);
		intervalTimes.put(6, 60*60*6);
		
		
		// 加载配置文件
		PropertyConfigurator.configure("log4j.properties");
		
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
		Index index = new Index();
		RecordList record = null;
		
		
		String filePath = "";
		String filename = "";
		String tempFileDir = "D:\\workspace-java\\MscDemo\\src\\com\\iflytek\\msp\\lfasr\\";
		while(true){
			RecordListDao recordDao = new RecordListDao();
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
					
					
					continue;
				}
				
//				String[] nameArr = record.getPath().split("/");
//				String na = nameArr[nameArr.length - 1];
				filename = String.valueOf(new Date().getTime())+"-"+record.getPath().substring(record.getPath().lastIndexOf("/")+1);
				
				System.out.println("start download file: "+filename);
				
				filePath = FileDownload.saveUrlAs(record.getPath(), tempFileDir, filename, "GET");
				System.out.println("end download file:"+filePath);
				
				if("".equals(filePath)){
					
					//标记为异常
					
					
					continue;
				}
				
				
				//开始上传
				boolean a1 = recordDao.updateUploadStatus(record.getId(), 2);
				HashMap<String, String> res = index.upload(lc, filePath);
				
				//上传完成
				if(res.get("status") == "1"){
					//保存记录的任务ID和下次查询时间
					int next_query_time = (int)new Date().getTime()/1000+60;
					boolean a2 = recordDao.updateUploadStatus(record.getId(), 3, res.get("task_id"), next_query_time);
				}else{
					boolean a3 = recordDao.updateUploadStatus(record.getId(), -1);
				}
			}else{
				System.out.println("没有需要上传的数据！");
			}
			
			
			/*
			 * 操作二：
			 * 获取处理状态， 如果已处理，就获取处理后的内容
			 * 获取第一条的处理状态
			 */
			int times = (int)(new Date().getTime()/1000);
			record = recordDao.getRecord(3, times);
			String task_id = "";
			if(record != null){
				task_id = record.getTaskId();
				HashMap<String, String> dealSatus = index.getDealStatus(lc, task_id);
				
				/*
				 * 获取处理结果
				 * 自定义状态： 1:成功， 2：处理中，-1：失败
				 */
				if(dealSatus.get("status") == "1"){
					HashMap<String, String> dealRes = index.getDealResultMsg(lc, task_id);
					
					//处理成功, 并更新数据库
					int s = dealRes.get("status") == "1" ? 1 : -1;
					recordDao.updateRecordInfo(record.getId(), s, dealRes.get("data"));
					
				}else if(dealSatus.get("status") == "-1"){
					recordDao.updateRecordInfo(record.getId(), -1, dealSatus.get("data"));
					
				}else if(dealSatus.get("status") == "-1"){
					//更新下次处理时间
					int intervalTime = 0;
					
					//超过查询次数， 或者没有配置查询时间间隔， 标记异常
					if(record.getQueryTimes() > limitQueryTime || intervalTimes.get(record.getQueryTimes()) == null){
						recordDao.updateUploadStatus(record.getId(), 4);
					}else{
						recordDao.updateNextQueryTime(record.getId(), times + intervalTime);
					}
					
				}
			}
			
			
			//每执行完一次睡眠20s.
			try {
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
				System.out.println("task_id=" + task_id);
			} else {
				// 创建任务失败-服务端异常
				System.out.println("ecode=" + uploadMsg.getErr_no());
				System.out.println("failed=" + uploadMsg.getFailed());
				
				res.put("status", String.valueOf(uploadMsg.getErr_no()));
				res.put("msg", uploadMsg.getFailed());
				res.put("task_id", "");
			}
		} catch (LfasrException e) {
			// 上传异常，解析异常描述信息
			Message uploadMsg = JSON.parseObject(e.getMessage(), Message.class);
			System.out.println("ecode=" + uploadMsg.getErr_no());
			System.out.println("failed=" + uploadMsg.getFailed());					
			
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
				System.out.println("task was fail. task_id:" + task_id);
				System.out.println("ecode=" + progressMsg.getErr_no());
				System.out.println("failed=" + progressMsg.getFailed());
				
				
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
					System.out.println("task was completed. task_id:" + task_id);
					
					res.put("status", "1");
					res.put("msg", "success");
					res.put("data", "");
					
					
				} else {
					// 未处理完成
					System.out.println("task was incomplete. task_id:" + task_id + ", status:" + progressStatus.getDesc());
					
					res.put("status", "2");
					res.put("msg", "success");
					res.put("data", "ecode=" + progressMsg.getErr_no() + ",failed=" + progressMsg.getFailed());
					
				}
			}
		} catch (LfasrException e) {
			// 获取进度异常处理，根据返回信息排查问题后，再次进行获取
			Message progressMsg = JSON.parseObject(e.getMessage(), Message.class);
			System.out.println("ecode=" + progressMsg.getErr_no());
			System.out.println("failed=" + progressMsg.getFailed());
			
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
			System.out.println(resultMsg.getData());	
			// 如果返回状态等于0，则任务处理成功
			if (resultMsg.getOk() == 0) {
				// 打印转写结果
				System.out.println(resultMsg.getData());
				
				res.put("status", "1");
				res.put("msg", "success");
				res.put("data", resultMsg.getData());
			} else {
				// 转写失败，根据失败信息进行处理
				System.out.println("ecode=" + resultMsg.getErr_no());
				System.out.println("failed=" + resultMsg.getFailed());
				
				res.put("status", "-1");
				res.put("msg", "failed");
				res.put("data", "ecode=" + resultMsg.getErr_no() + ",failed=" + resultMsg.getFailed());
			}
		} catch (LfasrException e) {
			// 获取结果异常处理，解析异常描述信息
			Message resultMsg = JSON.parseObject(e.getMessage(), Message.class);
			System.out.println("ecode=" + resultMsg.getErr_no());
			System.out.println("failed=" + resultMsg.getFailed());
			
			res.put("status", "-1");
			res.put("msg", "failed");
			res.put("data", "ecode=" + resultMsg.getErr_no() + ",failed=" + resultMsg.getFailed());
		}
		return res;
	}
	
	public String parseJson(){
		
		return "";
	}
	
	
}
