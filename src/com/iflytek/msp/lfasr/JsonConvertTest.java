package com.iflytek.msp.lfasr;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iflytek.msp.po.Result;
import com.iflytek.msp.po.ResultList;

/**
 * 
 * @author Administrator
 *
 */
public class JsonConvertTest {
	public static void main(String[] args){
		JsonConvertTest test = new JsonConvertTest();
		test.aaa();
		test.bbb();
	}
	
	public void aaa(){
		System.out.println("数据转换aaa");
		try {
			
			String str = "{\"status\":\"1\",\"msg\":\"success\",\"data\":{\"id\":1,\"name\":\"zhangsan\"}}";
			String str2 = "{\"status\":\"1\",\"msg\":\"success\",\"data\":null}";
			Gson gson = new Gson();
			JsonObject jsonObject = new JsonParser().parse(str2).getAsJsonObject();
//			System.out.println(jsonObject.get("status"));
//			System.out.println(jsonObject.get("msg"));
			Result result = gson.fromJson(jsonObject, Result.class);
			
			System.out.println(result.getStatus());
			System.out.println(result.getMsg());
			if (result.getData() != null) {
				System.out.println(result.getData().getName());
			}
			System.out.println("data 对象为空。");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// TODO: handle exception
		}
	}
	
	public void bbb(){
		System.out.println("数据转换bbb");
		try {
			
			String str = "{\"status\":\"1\",\"msg\":\"success\",\"resulList\":[{\"id\":1,\"name\":\"zhangsan\"}]}";
			String str2 = "{\"status\":\"1\",\"msg\":\"success\",\"resulList\":[]}";
			Gson gson = new Gson();
			JsonObject jsonObject = new JsonParser().parse(str2).getAsJsonObject();
//			System.out.println(jsonObject.get("status"));
//			System.out.println(jsonObject.get("msg"));
			Result result = gson.fromJson(jsonObject, Result.class);
			
			System.out.println(result.getStatus());
			System.out.println(result.getMsg());
			
			List<ResultList> rsList = result.getResulList();
			
			if (rsList.size() > 0) {
				for (ResultList resultList : rsList) {
					System.out.println(resultList.getId()+"--"+resultList.getName());
				}
				System.out.println("----------------------------");
				System.exit(0);
			}
			System.out.println("resultList 对象为空。");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// TODO: handle exception
		}
	}
	
}
