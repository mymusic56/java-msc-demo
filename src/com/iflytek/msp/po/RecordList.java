package com.iflytek.msp.po;

public class RecordList {
	
	private int userId;
	private String monId;
	private int id;
	private String path;
	private String tempPath;
	private int hasConvert;
	
	private String taskId;
	
	
	private String content;
	private String contentJson;
	
	private int queryTimes;
	private int nextQueryTime;
	
	private int fileSize;
	
	
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getMonId() {
		return monId;
	}
	public void setMonId(String monId) {
		this.monId = monId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getTempPath() {
		return tempPath;
	}
	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}
	public int getHasConvert() {
		return hasConvert;
	}
	public void setHasConvert(int hasConvert) {
		this.hasConvert = hasConvert;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContentJson() {
		return contentJson;
	}
	public void setContentJson(String contentJson) {
		this.contentJson = contentJson;
	}
	public int getQueryTimes() {
		return queryTimes;
	}
	public void setQueryTimes(int queryTimes) {
		this.queryTimes = queryTimes;
	}
	public int getNextQueryTime() {
		return nextQueryTime;
	}
	public void setNextQueryTime(int nextQueryTime) {
		this.nextQueryTime = nextQueryTime;
	}
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	
}
