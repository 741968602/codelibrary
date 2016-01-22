package com.stcyclub.zhimeng.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.TextView;

import com.stcyclub.zhimeng.R;
import com.stcyclub.zhimeng.adapter.MyBaseAdapter;
import com.stcyclub.zhimeng.utils.Utils;

public class PicturesShowActivity extends Activity {
	Gallery gly;
	TextView picture_tv;
	//Bitmap[] bitmaps=new Bitmap[]{};
	int index = 0;
	MyBaseAdapter adapter = null;
	List<Bitmap> bitmaps = new ArrayList<Bitmap>();
	Object[] selectPicturesObj = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pictures_show);
		init();
	}

	/**
	 * 数据初始化
	 */
	private void init() {
		gly = (Gallery) findViewById(R.id.gly_picture);
		picture_tv=(TextView)findViewById(R.id.picture_count);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			selectPicturesObj=(Object[]) extras.get("selectPicture");
		}
		ArrayList<Integer> selectPictures =new ArrayList<Integer>();
		Log.d("TAG", "str"+selectPicturesObj.length);
		for (int i = 0; i < selectPicturesObj.length; i++) {
			selectPictures.add(Integer.parseInt(selectPicturesObj[i].toString()));
		}
		//Log.d("TAG", "selectPictures[i]size" + selectPictures.size());
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 4;
		for (int i = 0; i < selectPictures.size(); i++) {
			//Log.d("TAG", "selectPictures[i]" + selectPictures.get(i));
			String bmpUrl = ScaleImageFromSdcardActivity.map.get(selectPictures
					.get(i));
			//Log.d("TAG", "bmpUrl.............show" + bmpUrl);
			Bitmap bm =BitmapFactory.decodeFile(bmpUrl,options);
			//Log.d("TAG", "bm.............show" + bm);
			bitmaps.add(bm);
			
		}
		adapter = new MyBaseAdapter(PicturesShowActivity.this, bitmaps);
		gly.setAdapter(adapter);
		gly.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				//Log.d("TAG", "position.............show" + position);
				//Toast.makeText(PicturesShowActivity.this, ""+position, Toast.LENGTH_LONG).show();
				picture_tv.setText((position+1)+"/"+bitmaps.size());
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	public void btnClick(View v){
		switch (v.getId()) {
		case R.id.show_image_btn_back:
			PicturesShowActivity.this.finish();
			overridePendingTransition(R.anim.in_from_left, R.anim.out_to_rigth);
			break;
		case R.id.btn_send_show_picture:
			Intent intent=new Intent();
			intent.putExtra("selectPicture", selectPicturesObj);
			setResult(Utils.SHOW_SELECT_PICTURE_RESULT,intent);
			PicturesShowActivity.this.finish();
			overridePendingTransition(R.anim.in_from_left, R.anim.out_to_rigth);
			break;
		default:
			break;
		}
	}

}
