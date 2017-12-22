package com.iflytek.msp.lfasr;

import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.alibaba.fastjson.JSON;
import com.iflytek.msp.cpdb.lfasr.client.LfasrClientImp;
import com.iflytek.msp.cpdb.lfasr.exception.LfasrException;
import com.iflytek.msp.cpdb.lfasr.model.Message;
import com.iflytek.msp.cpdb.lfasr.model.ProgressStatus;
import com.iflytek.msp.util.FileDownload;

public class Download {
	public static void main(String[] args) {
		Download d1 = new Download();
		HashMap<String, String> res = new HashMap<>();
		int a = 1;
		while(true){
			res = d1.test(a);
			System.out.println(res.get("id"));
			a++;
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public HashMap<String, String> test(int a){
		HashMap<String, String> res = new HashMap<>();
		res.put("bbb", " 囔 哇 谁知 嗯 囔 囡 当年 弃 香山 北 王 冬季 真 马 长 嘶 剑 气 胡 爽 十 似 黄河水 妈妈 吧 20 年 中 空间 ， 谁 能 想 刚 女童 长刀 所 想 我 我 杀生 忠 魂 埋 骨 他乡 C 拜 时 到家 ， 我 人 叹息 ， 更 无语 ， 血泪 满 筐 囡 马丁 单曲 陈 北 王 珍 备忘 ， 早前 黄 尘 飞扬 不 愿 守土 复 开 疆 堂堂 中国 要 要 死 法 。  噢 大 一 号 。  嗯 囔 ， 谁知 谁知 谁知 囡 当年 七 墙上 北 王 有 攻击 ， 这 马 长 嘶 剑 气 如 霜 ， 西 是 黄河水 妈妈 。 20 点 钟 后 见 谁 能 像 高 女王 常常 告诉 我 想 我 烧 身 忠 魂 埋 骨 大侠 大侠 西 白 死 到家 ， 国人 叹息 更 无语 ， 血泪 满 光 。 嗯 大 P 等 安 俞 振 飞 王 晨 北 望 草 青 黄 尘 飞扬 ， 不敢 守土 不 开 ， 抢 堂堂 中国 要 当 死亡 I like 嗯 地方 区域 辰 被 王 忍 北方 糙 求 方程 欢迎 ， 呀 感受 不 夸奖 他们 重要 的 司法 唉 来 ， 谁知 囔 。 ");
		res.put("ccc", " 囔 哇 谁知 嗯 囔 囡 当年 弃 香山 北 王 冬季 真 马 长 嘶 剑 气 胡 爽 十 似 黄河水 妈妈 吧 20 年 中 空间 ， 谁 能 想 刚 女童 长刀 所 想 我 我 杀生 忠 魂 埋 骨 他乡 C 拜 时 到家 ， 我 人 叹息 ， 更 无语 ， 血泪 满 筐 囡 马丁 单曲 陈 北 王 珍 备忘 ， 早前 黄 尘 飞扬 不 愿 守土 复 开 疆 堂堂 中国 要 要 死 法 。  噢 大 一 号 。  嗯 囔 ， 谁知 谁知 谁知 囡 当年 七 墙上 北 王 有 攻击 ， 这 马 长 嘶 剑 气 如 霜 ， 西 是 黄河水 妈妈 。 20 点 钟 后 见 谁 能 像 高 女王 常常 告诉 我 想 我 烧 身 忠 魂 埋 骨 大侠 大侠 西 白 死 到家 ， 国人 叹息 更 无语 ， 血泪 满 光 。 嗯 大 P 等 安 俞 振 飞 王 晨 北 望 草 青 黄 尘 飞扬 ， 不敢 守土 不 开 ， 抢 堂堂 中国 要 当 死亡 I like 嗯 地方 区域 辰 被 王 忍 北方 糙 求 方程 欢迎 ， 呀 感受 不 夸奖 他们 重要 的 司法 唉 来 ， 谁知 囔 。 ");
		res.put("id", String.valueOf(a));
		System.out.println(res.get("bbb"));
		System.out.println(res.get("bbb"));
		System.out.println(res.get("bbb"));
		System.out.println(res.get("bbb"));
		System.out.println(res.get("bbb"));
		return res;
	}
	
	
	public void test2(){

		//循环查询
		PropertyConfigurator.configure("source/log4j.properties");
		
		//查询次数对应的时间间隔
		Logger logger = Logger.getLogger(IndexMongo.class);
		HashMap<Integer, Integer> intervalTimes = new HashMap<>();
		
		// 加载配置文件
//			PropertyConfigurator.configure("log4j.properties");
		
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
		String tempFileDir = "tmp/download";
		HashMap<String, String> status = null;
		HashMap<String, String> result = null;
		while (true) {
			System.out.println("start download...");
//			String remotePath = "http://nineton.oss-cn-hangzhou.aliyuncs.com/recorder/test/20171128_002.m4a";
//			String filename = String.valueOf(new Date().getTime());
//			String localPath = FileDownload.saveUrlAs(remotePath, tempFileDir, filename, "GET");
//			System.out.println(localPath);
			String task_id = "c3b783efa21e48f9b471e48db0d08971";
			
			Download d = new Download();
			status = d.getDealStatus(lc, task_id);
			System.out.println(status.get("status"));
			
			result = d.getDealResultMsg(lc, task_id);
			System.out.println(result.get("data"));;
			
		}
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
					System.out.println("task was incomplete. task_id:" + task_id + ", status:" + progressStatus.getDesc());
					
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
//			
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
}
