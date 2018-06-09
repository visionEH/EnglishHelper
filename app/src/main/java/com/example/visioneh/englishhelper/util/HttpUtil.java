package com.example.visioneh.englishhelper.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 网络连接的工具类
 * @author xray
 *
 */
public class HttpUtil {

	/**
	 * 通过GET请求获得服务器的数据
	 * @param urlStr
	 * @return
	 */
	public static String get(String urlStr){
		InputStream inputStream = null;
		HttpURLConnection conn = null;
		try {
			//创建URL对象
			URL url = new URL(urlStr);
			//打开连接
			conn = (HttpURLConnection) url.openConnection();
			//设置请求方法
			conn.setRequestMethod("GET");
			//设置网络连接超时
			conn.setConnectTimeout(3000);
			//获得输入流
			inputStream = conn.getInputStream();
			//定义字节数组作为缓冲
			byte[] buffer = new byte[1024];
			//每次读取的长度
			int len = 0;
			//StringBuilder用于拼接字符串
			StringBuilder builder = new StringBuilder();
			//循环从输入流中读取数据放入字节数组中
			while((len = inputStream.read(buffer)) != -1){
				//将字节数组转换为字符串
				String str = new String(buffer,0,len);
				//将字符串拼接起来
				builder.append(str);
			}
			//返回完整的字符串
			return builder.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(inputStream != null){
					inputStream.close();
				}
				if(conn != null){
					conn.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 通过POST请求获得服务器的数据
	 * @param urlStr
	 * @return
	 */
	public static String post(String urlStr,String args){
		HttpURLConnection conn = null;
		InputStream inputStream = null;
		try {
			//创建URL对象
			URL url = new URL(urlStr);
			//打开连接
			conn = (HttpURLConnection) url.openConnection();
			//设置请求方法POST
			conn.setRequestMethod("POST");

			//设置网络连接超时
			conn.setConnectTimeout(6000);
			//设置可以向服务器发送数据
			conn.setDoOutput(true);
			//获得输出流
			conn.connect();
			OutputStream outputStream = conn.getOutputStream();
			//将参数从后台发送给服务器
			outputStream.write(args.getBytes());
			outputStream.flush();
			outputStream.close();
			//获得输入流
			inputStream = conn.getInputStream();
			//定义字节数组作为缓冲
			byte[] buffer = new byte[1024];
			//每次读取的长度
			int len = 0;
			//StringBuilder用于拼接字符串
			StringBuilder builder = new StringBuilder();
			//循环从输入流中读取数据放入字节数组中
			while((len = inputStream.read(buffer)) != -1){
				//将字节数组转换为字符串
				String str = new String(buffer,0,len);
				//将字符串拼接起来
				builder.append(str);
			}
			//返回完整的字符串
			return builder.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(inputStream != null){
					inputStream.close();
				}
				if(conn != null){
					conn.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
