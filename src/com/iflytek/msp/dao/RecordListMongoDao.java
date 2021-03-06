package com.iflytek.msp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.iflytek.msp.po.RecordList;
import com.iflytek.msp.po.RecordListExtra;
import com.iflytek.msp.util.DBUtil;
import com.iflytek.msp.util.MongoDBUtil;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;

import static com.mongodb.client.model.Updates.set;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;

public class RecordListMongoDao {
	
	private static Connection connection = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	
	private static MongoDatabase database = null;
	
	/**
	 * 保存转换后的文字信息
	 * @return
	 */
	public boolean updateRecordTextInfo(String mId, int convertStatus, String originalStr, String segementStr){
		long count = 0;
		try {
			database = MongoDBUtil.getDataBase();
			MongoCollection<Document> collection = database.getCollection("rec_record_lists");
			
			count = collection.updateOne(
					eq("_id", new ObjectId(mId)), 
					combine(
							set("has_convert", convertStatus),
							set("content", originalStr),
							set("content_words", segementStr)
					)
			).getMatchedCount();
			System.out.println(count);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			MongoDBUtil.closeAll();
		}
		
		if(count > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 保存下载路径
	 * @return
	 */
	public boolean updateRecordDownInfo(String mId, int convertStatus, String msg, String tmpPath){
		long count = 0;
		try {
			database = MongoDBUtil.getDataBase();
			MongoCollection<Document> collection = database.getCollection("rec_record_lists");
			
			count = collection.updateOne(
					eq("_id", new ObjectId(mId)), 
					combine(
							set("has_convert", convertStatus),
							set("content", msg),
							set("temp_path", tmpPath)
					)
			).getMatchedCount();
			System.out.println(count);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			MongoDBUtil.closeAll();
		}
		
		if(count > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 更具上传第三方服务器状态来获取录音记录
	 * 
	 * @param has_convert
	 * @return RecordList rList
	 */
	public RecordList getRecord(int has_convert){
		RecordList rList = null;
		try {
			database = MongoDBUtil.getDataBase();
			MongoCollection<Document> collection = database.getCollection("rec_record_lists");
			Document doc = collection.find(
					new Document("enabled",1)
					.append("up_status", 1)
					.append("has_convert", has_convert)
			).sort(Sorts.descending("c_date")).first();
			
			if(doc != null){
				rList = new RecordList();
				rList.setMonId(doc.getObjectId("_id").toString());
				rList.setPath(doc.getString("file_path"));
				rList.setTempPath(doc.getString("temp_path"));
				rList.setUserId(doc.getInteger("user_id"));
				rList.setHasConvert(doc.getInteger("has_convert"));
				rList.setFileSize(doc.getInteger("file_size"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			MongoDBUtil.closeAll();
		}

		return rList;
	}

	/**
	 * 
	 * 更具上传第三方服务器状态来获取录音记录[查询录音文件的处理状态时使用]
	 * 
	 * 1、必须是当前时间大于待处理时间 2、查询次数和时间间隔限制
	 * 
	 * @param has_convert
	 * @return RecordList rList
	 */
	public RecordList getRecord(int has_convert, int times) {
		RecordList rList = null;
		try {
			
			database = MongoDBUtil.getDataBase();
			MongoCollection<Document> collection = database.getCollection("rec_record_lists");
			Document doc = collection.find(
					new Document("enabled",1)
					.append("up_status", 1)
					.append("has_convert", has_convert)
					.append("next_query_time", new Document("$lte",times))
			).sort(Sorts.ascending("created")).first();
			
			if(doc != null){
				rList = new RecordList();
				rList.setMonId(doc.getObjectId("_id").toString());
				rList.setPath(doc.getString("file_path"));
				rList.setTaskId(doc.getString("task_id"));
				rList.setHasConvert(doc.getInteger("has_convert"));
				rList.setFileSize(doc.getInteger("file_size"));
				rList.setQueryTimes(doc.getInteger("query_times"));;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			MongoDBUtil.closeAll();
		}

		return rList;
	}

	public boolean updateUploadStatus(String mId, int status, String msg) {
		long count = 0;
		try {
			database = MongoDBUtil.getDataBase();
			MongoCollection<Document> collection = database.getCollection("rec_record_lists");
			
			count = collection.updateOne(
					eq("_id", new ObjectId(mId)), 
					combine(
							set("has_convert", status),
							set("content", msg)
					)
			).getMatchedCount();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			MongoDBUtil.closeAll();
		}
		if (count > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 更新更新task_id
	 * 
	 * @param mId 	mongodb _id
	 * @param status
	 * @param task_id
	 * @return
	 */
	public boolean updateUploadStatus(String mId, int status, String task_id,
			int next_query_time, String msg) {
		long count = 0;
		try {
			
			database = MongoDBUtil.getDataBase();
			MongoCollection<Document> collection = database.getCollection("rec_record_lists");
			
			count = collection.updateOne(
					eq("_id", new ObjectId(mId)), 
					combine(
							set("has_convert", status), 
							set("task_id", task_id),
							set("next_query_time", next_query_time),
							set("content", msg)
					)
			).getMatchedCount();
			System.out.println(count);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			MongoDBUtil.closeAll();
		}
		if (count > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 更新录音转文字，下一次处理的时间
	 * 
	 * @param id
	 * @param times
	 * @return
	 */
	public boolean updateNextQueryTime(String mId, int times, int queryTimes) {
		long count = 0;
		try {

			database = MongoDBUtil.getDataBase();
			MongoCollection<Document> collection = database.getCollection("rec_record_lists");
			
			count = collection.updateOne(
					eq("_id", new ObjectId(mId)), 
					combine(
							set("query_times", queryTimes+1),//查询次数加1
							set("next_query_time", times)
					)
			).getMatchedCount();
			System.out.println(count);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			MongoDBUtil.closeAll();
		}
		if (count > 0) {
			return true;
		}
		return false;
	}

}
