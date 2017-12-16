package com.iflytek.msp.util;

import java.util.Arrays;
import java.util.ResourceBundle;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
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
			
			mongoClient = new MongoClient(new MongoClientURI(url));
			database = mongoClient.getDatabase(databaseName);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static MongoDatabase getDataBase(){
		if(database == null){
			database = mongoClient.getDatabase(databaseName);
		}
		return database;
	}
	
	
}
