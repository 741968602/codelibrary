package com.jike.gsondemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.jike.gsondemo.bean.Book;

/**
 * Gson的基本用法
 * 
 * https://api.douban.com/v2/book/1220562
 * 
 * @author Administrator
 * 
 */
public class MainActivity extends Activity {
	private String url = "https://api.douban.com/v2/book/1220562";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getData();
	}

	private void getData() {
		StringRequest request = new StringRequest(url, new Listener<String>() {

			@Override
			public void onResponse(String arg0) {
				Log.i("info", arg0);
				dealData(arg0);
			}

		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub

			}
		});
		new Volley().newRequestQueue(getApplicationContext()).add(request);
	}

	private void dealData(String result) {
		Gson gson = new Gson();
		Book book = gson.fromJson(result, Book.class);
		Log.i("info", book.getTitle() + ":" + book.getPublisher() + ":"
				+ book.getTags().size());
	}
}
