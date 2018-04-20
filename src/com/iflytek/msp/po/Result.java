package com.iflytek.msp.po;

import java.util.List;

public class Result {
	private String status;
	private String msg;
	private ResultList data;
	
	private List<ResultList> resulList;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public ResultList getData() {
		return data;
	}
	public void setData(ResultList data) {
		this.data = data;
	}
	public List<ResultList> getResulList() {
		return resulList;
	}
	public void setResulList(List<ResultList> resulList) {
		this.resulList = resulList;
	}
	
}
