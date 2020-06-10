package com.yuansong.test.sqlite2mysqlspeed.controller.message;

public class BaseMessage<T> extends SimpleMessage {
	
	private T data;
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
}
