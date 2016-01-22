package com.stcyclub.zhimeng.adapter;

import java.util.List;

import com.stcyclub.zhimeng.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyBaseAdapter extends BaseAdapter {

	Context ctx;
	List<Bitmap> bitmaps;
	LayoutInflater inflater;
	public MyBaseAdapter(Context ctx,List<Bitmap> bitmaps){
		this.ctx=ctx;
		this.bitmaps=bitmaps;
		this.inflater=LayoutInflater.from(ctx);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return bitmaps.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if( convertView == null ){
			convertView=inflater.inflate(R.layout.show_picture_item_iv,null);
		}
		ImageView item_iv=(ImageView)convertView.findViewById(R.id.item_iv);
		item_iv.setImageBitmap(bitmaps.get(position));
		
		return convertView;
	}

}
