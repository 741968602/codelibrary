package com.miaotech.health.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;

import android.util.Log;

import com.miaotech.health.api.joggle;

public class Connect {

	static  String  targetUrl= joggle.connUrl;//服务器连接URL

	public  static String connect(String params) throws JSONException{
		System.out.println("params==================="+params);
		String temp = "";
		String a="";
		try {
			URL url = new URL(targetUrl);
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setRequestMethod("POST");
			OutputStream out = urlConn.getOutputStream();
			out.write(params.getBytes("UTF-8"));
			out.flush();

			int retcod = urlConn.getResponseCode();
			System.out.println("server response  code[返回码为:" + retcod);
			if (retcod == 200) {
				InputStream in = urlConn.getInputStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(in));

				System.err.println("*********开始*******");
				while ((temp = reader.readLine()) != null)
				{
					
					Log.i("返回的数据是+++++++++++",temp);
					System.out.println(temp);
					a=temp;
				}

				System.err.println("*********结束********");
				reader.close();
				in.close();
				urlConn.disconnect();
				return a;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		return false;
		return a;
	}
}
