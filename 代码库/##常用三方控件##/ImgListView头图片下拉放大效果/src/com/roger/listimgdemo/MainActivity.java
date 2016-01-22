package com.roger.listimgdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.roger.listimgdemo.R;

public class MainActivity extends Activity {

	private Context sContext;
	private List<String> sNewsList;
	private NewsAdapter sNewsAdapter;
	private ImgListView sListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sContext = this;
		sNewsList = new ArrayList<String>();
		geneItems();
		sListView = (ImgListView) findViewById(R.id.xListView);
//		sListView.setImageId(R.drawable.top_img);//可以再资源文件中通过 imglistview:headimage="@drawable/top_img"   设置图片，也可通过本方法
		sNewsAdapter = new NewsAdapter();
		sListView.setAdapter(sNewsAdapter);
	}
	private void geneItems() {
		for (int i = 0; i != 10; ++i) {
			sNewsList.add(""+i);
		}
	}
	private class NewsAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public NewsAdapter() {
			mInflater = LayoutInflater.from(sContext);
		}

		@Override
		public int getCount() {
			return sNewsList.size();
		}

		@Override
		public Object getItem(int position) {
			return sNewsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder h = null;
			if (convertView == null) {
				h = new Holder();
				convertView = mInflater.inflate(R.layout.list_item, null);
				h.content = (TextView) convertView.findViewById(R.id.tv_content);
				convertView.setTag(h);
			} else {
				h = (Holder) convertView.getTag();
			}
			h.content.setText(sNewsList.get(position));
			return convertView;
		}

		private class Holder {
			public TextView content;
		}
	}

}
