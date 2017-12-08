package com.iflytek.msp.po;

import java.util.Date;

public class RecordListExtra {
	
	private int id;
	private String taskId;
	
	private String content;
	private String contentJson;
	private String contentWords;
	private int status;
	private Date created;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getContentWords() {
		return contentWords;
	}
	public void setContentWords(String contentWords) {
		this.contentWords = contentWords;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	
}
