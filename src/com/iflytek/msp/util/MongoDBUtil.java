package com.iflytek.msp.util;

import java.util.Arrays;
import java.util.ResourceBundle;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBUtil {
	
	//声明获取连接和驱动所需要的参数
	private static String databaseName = null;
	private static String url = null;
	private static String user = null;
	private static String password = null;
	
	
	private static MongoClient mongoClient = null;
	private static MongoDatabase database = null;
	
	
	static {
		try{
			ResourceBundle rb = ResourceBundle.getBundle("mdbconfig");
			String databaseType = rb.getString("database");
			url = rb.getString(databaseType+"Url");
			user = rb.getString(databaseType+"User");
			databaseName = rb.getString(databaseType+"Database");
			password = rb.getString(databaseType+"Password");
			
//			char[] passwords = password.toCharArray();
//			MongoCredential credential = MongoCredential.createScramSha1Credential(user,
//					databaseName,
//					passwords);
//			mongoClient = new MongoClient(new ServerAddress("192.168.88.128", 27017),
//					Arrays.asList(credential));
			
			
			/*
			 * The MongoClient() instance represents a pool of connections to the database; 
			 * you will only need one instance of class MongoClient even with multiple threads.
			 */
			mongoClient = new MongoClient(new MongoClientURI(url));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static MongoDatabase getDataBase(){
		
		if (mongoClient == null) {
			System.out.println("connect database...");
			mongoClient = new MongoClient(new MongoClientURI(url));
		}
		
		return mongoClient.getDatabase(databaseName);
	}
	
	/**
	 * 关闭数据库连接
	 */
	public static void closeAll(){
		mongoClient.close();
		mongoClient = null;
	}
}
