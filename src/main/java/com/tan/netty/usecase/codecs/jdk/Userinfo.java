package com.tan.netty.usecase.codecs.jdk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

public class Userinfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userName;
	
	private int userId;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public byte[] codec(){
		ByteBuffer buf=ByteBuffer.allocate(1024);
		byte[]value=this.userName.getBytes();
		buf.putInt(value.length);
		buf.put(value);
		buf.putInt(this.userId);
		buf.flip();
		value=null;
		byte[]result=new byte[buf.remaining()];
		buf.get(result);
		return result;
	}
	
	public static void main(String[] args) throws IOException {
		Userinfo userinfo=new Userinfo();
		userinfo.setUserId(100);
		userinfo.setUserName("tanl");
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		ObjectOutputStream os=new ObjectOutputStream(bos);
		os.writeObject(userinfo);
		os.flush();
		os.close();
		byte[] b=bos.toByteArray();
		System.out.println("jdk serializable length :"+b.length);
		bos.close();
		System.out.println("byte serializable length :"+userinfo.codec().length);
	}
}
