package com.example.visioneh.englishhelper.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * 加密工具类
 */
public class MD5Util {
	
	public static String md5(String text){
		try{
			//获得md5加密算法
			MessageDigest digest = MessageDigest.getInstance("md5");
			//加密原字符串
			digest.update(text.getBytes());
			//获得加密结果
			byte[] bytes = digest.digest();
			StringBuilder builder = new StringBuilder();
			//将加密结果转换为16进制字符串
			for(int i = 0;i < bytes.length;i++){
				String s = Integer.toHexString(Math.abs(bytes[i]));
				builder.append(s);
			}
			return builder.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
