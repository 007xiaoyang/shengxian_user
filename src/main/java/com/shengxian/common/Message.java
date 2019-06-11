package com.shengxian.common;

import java.util.HashMap;

/**
 * 消息对象
 * @author SHZ
 *
 */
public class Message extends HashMap<String, Object>{

	String keyCode = "code",keyMessage = "info",keyData = "data";


	public Message getResponseBody(){
		return  this;
	}
	/**
	 * @param code 状态码
	 * @param info 消息内容
	 */
	public Message(int code, String info) {
		put(keyCode, code);
		put(keyMessage, info);
	}
	
	/**
	 * 
	 */
	public  static final long serialVersionUID = 1L;
	/**
	 * 成功码
	 */
	public static final int codeSuccessed = 1;
	/**
	 * 退出登录
	 */
	public static final int codeOutOfLogin = 2;
	/**
	 * 失败码
	 */
	public static final int codeFailured = -1;
	/**
	 * 未知状态
	 */
	private static final int codeNull = 0;

	public int getCode() {
		return (Integer)get(keyCode);
	}
	public Message code(int code) {
		put(keyCode, code);
		return this;
	}
	public String getMessage() {
		return (String)get(keyMessage);
	}
	
	public Message message(String info) {
		put(keyMessage, info);
		return this;
	}
	
	public Message(int code) {
		this(code,"");
	}

	public Message data(Object data){
		put(keyData, data);
		return this;
	}

	public Object getData() {
		return this.get(keyData);
	}
	/**
	 * 创建成功消息
	 * @return
	 */
	public static Message successed(){
		return new Message(codeSuccessed);
	}

	/**
	 * 创建消息
	 * @return
	 */
	public static Message failured(){
		return new Message(codeFailured);
	}
	/**
	 * 创建空状态消息
	 * @return
	 */
	public static Message non(){
		return new Message(codeNull);
	}
	
}
