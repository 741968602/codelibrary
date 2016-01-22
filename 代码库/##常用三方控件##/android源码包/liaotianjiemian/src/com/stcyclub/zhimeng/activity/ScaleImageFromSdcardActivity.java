package com.stcyclub.zhimeng.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stcyclub.zhimeng.R;
import com.stcyclub.zhimeng.adapter.ImageAdapter;
import com.stcyclub.zhimeng.utils.Utils;

import android.R.color;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class ScaleImageFromSdcardActivity extends Activity implements
		OnScrollListener{
	
	Bitmap bm;
	int IndexPposition = 1;
	int pageCount = 30;
	Context mContext = ScaleImageFromSdcardActivity.this;
	public static HashMap<Integer, String> map = new HashMap<Integer, String>();// 用来存放MediaStore获取的照片路径
	int counter = 0;// 计数总共有多少条图片地址的路径的数据
	int getImgCounter = 0;// 计数总共有多少条图片地址的路径的数据
	Cursor cursor;// 定义游标来操作图片地址
	private GridView gridview;
	List<Bitmap> bitmapList = new ArrayList<Bitmap>();// 存放图片

	ImageAdapter imageAdapter;
	GestureDetector mGestureDetector;
	private int visibleLastIndex = 0; // 最后的可视项索引
	private int visibleItemCount; // 当前窗口可见项总数
	myThread mt;
	boolean flag = true;// 记录加载数据线程是否开启

	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				flag = true;
				imageAdapter.addData(bitmapList);
				gridview.setSelection(visibleLastIndex - visibleItemCount + 1); // 设置选中项
				break;
			case 2:
				if (mt != null) {
					mt.interrupted();
					Log.d("TAG", "线程关闭");
				}
				break;
			default:
				break;
			}
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.select_image_from_sdcard);
		OperateSdcard();

		init();
	}

	/**
	 * 初始化数据
	 */
	public void init() {

		imageAdapter = new ImageAdapter(ScaleImageFromSdcardActivity.this);
		gridview = (GridView) findViewById(R.id.gridview);
		imageAdapter.setImgMap(bitmapList);
		gridview.setAdapter(imageAdapter);
		gridview.setOnScrollListener(this); // 添加滑动监听
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
//				Toast.makeText(getApplicationContext(), "" + position,
//						Toast.LENGTH_LONG).show();
				imageAdapter.add(position);
				imageAdapter.notifyDataSetChanged();
			}
		});
	}

	/**
	 * 按钮事件
	 */
	public void btnClick(View v) {
		switch (v.getId()) {
		case R.id.image_btn_back:
			ScaleImageFromSdcardActivity.this.finish();
			// imageAdapter.setImgMap(null);
			imageAdapter.ClickList.clear();// 清除选择数据
			// 设置切换动画，从左边进入，右边退出
			overridePendingTransition(R.anim.in_from_left, R.anim.out_to_rigth);
			break;
		case R.id.btn_preview:
			if(imageAdapter.ClickList.size()>0){
				Intent intent = new Intent(ScaleImageFromSdcardActivity.this,
						PicturesShowActivity.class);
				Object[] data = imageAdapter.getClickList().toArray();
				intent.putExtra("selectPicture", data);
				ScaleImageFromSdcardActivity.this.startActivityForResult(intent,
						Utils.SHOW_SELECT_PICTURE);
				// 设置切换动画，从右边进入，左边退出
				overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
				imageAdapter.ClickList.clear();// 清除选择数据
				imageAdapter.notifyDataSetChanged();
			}else{
				Toast.makeText(ScaleImageFromSdcardActivity.this, "请选择图片", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.btn_send_picture:
			Log.d("TAG", "发送选择的图片");
			Intent intent = new Intent();
			Object[] data = imageAdapter.getClickList().toArray();
			intent.putExtra("selectPicture", data);
			Log.d("TAG", "发送选择的图片sc" + data.toString());
			ScaleImageFromSdcardActivity.this.setResult(
					Utils.SHOW_PICTURE_RESULT, intent);

			ScaleImageFromSdcardActivity.this.finish();
			// 设置切换动画，从左边进入，右边退出
			overridePendingTransition(R.anim.in_from_left, R.anim.out_to_rigth);
			imageAdapter.ClickList.clear();// 清除选择数据
			break;
		case R.id.btn_clear_select:
			imageAdapter.ClickList.clear();// 清除选择数据
			imageAdapter.notifyDataSetChanged();
			break;
		}
	}

	// 运用MediaStore来操作sd卡，获取照片
	public void OperateSdcard() {
		String[] proj = { MediaStore.Images.Media.DATA };
		Uri mUri = Uri.parse("content://media/external/images/media");
		cursor = managedQuery(mUri, proj, null, null, null);
		counter = cursor.getCount();
		// Log.d("TAG", "counter1"+counter);
		new Thread() {
			@Override
			public void run() {
				try {
					cursor.moveToPosition((IndexPposition - 1) * pageCount);
					for (int i = 0; i < pageCount; i++) {
						if (cursor != null) {
							if (counter > (IndexPposition - 1) * pageCount + i) {
								int column_index = cursor
										.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
								String fileName = cursor
										.getString(column_index);
								// String url=cursor.get
								// Log.d("TAG", i+"fileName..."+fileName);
								BitmapFactory.Options options = new BitmapFactory.Options();
								options.inSampleSize = 4;
								bm = BitmapFactory
										.decodeFile(fileName, options);
								if (bm != null) {
									map.put(getImgCounter, fileName);
									getImgCounter = getImgCounter + 1;
									//Log.d("TAG", "bm" + bm);
									Bitmap btm2 = Bitmap.createScaledBitmap(bm,
											150, 150, false); // 这里你可以自定义它的大小
									bitmapList.add(btm2);
									myHandler.sendEmptyMessage(1);
								}

							}
							cursor.moveToNext();

						}
					}
				} catch (Exception e) {
					Log.d("TAG", "查找图片是加载过程中退出，cursor移动异常");
				}
			}
		}.start();
	}

	class myThread extends Thread {
		@Override
		public void run() {
			Log.d("TAG", "线程开始");
			try {
				cursor.moveToPosition((IndexPposition - 1) * pageCount);
				for (int i = 0; i < pageCount - 10; i++) {
					if (counter > (IndexPposition - 1) * pageCount + i) {
						int column_index = cursor
								.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
						String fileName = cursor.getString(column_index);
						//Log.d("TAG", i + "fileName..." + fileName);
						BitmapFactory.Options options = new BitmapFactory.Options();
						options.inSampleSize = 4;
						bm = BitmapFactory.decodeFile(fileName, options);
						if (bm != null) {
							map.put(getImgCounter, fileName);
							getImgCounter = getImgCounter + 1;
							//Log.d("TAG", "bm" + bm);
							Bitmap btm2 = Bitmap.createScaledBitmap(bm, 150,
									150, false); // 这里你可以自定义它的大小
							bitmapList.add(btm2);
							myHandler.sendEmptyMessage(1);
						}
					}

					cursor.moveToNext();
				}
			} catch (Exception e) {
				Log.d("TAG", "查找图片是加载过程中退出，cursor移动异常");
			}
			myHandler.sendEmptyMessage(2);// 关闭线程
			Log.d("TAG", "加载20条完成");

		}
	}

	public String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取根目录
		}
		return sdDir.toString();

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.visibleItemCount = visibleItemCount;
		visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
		// Log.d("TAG","visibleLastIndex.."+visibleLastIndex+"\t visibleItemCount"+visibleItemCount
		// );

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int itemsLastIndex = imageAdapter.getCount() - 1; // 数据集最后一项的索引
		int lastIndex = itemsLastIndex - 5; // 加上底部的loadMoreView项
		// Log.d("TAG", "lastIndex"+lastIndex);
		// Log.d("TAG", "itemsLastIndex"+itemsLastIndex);
		// Log.d("TAG", "scrollState"+scrollState);
		if (flag && visibleLastIndex >= lastIndex) {
			flag = false;
			// 如果是自动加载,可以在这里放置异步加载数据的代码
			// Log.d("TAG", "进来加载数据"+lastIndex);
			// Log.d("TAG", "进来加载数据"+itemsLastIndex);
			// Log.d("TAG", "IndexPposition.."+IndexPposition);
			IndexPposition = IndexPposition + 1;
			Log.d("TAG", "IndexPposition.." + IndexPposition);
			mt = new myThread();
			mt.start();
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == Utils.SHOW_SELECT_PICTURE
				&& resultCode == Utils.SHOW_SELECT_PICTURE_RESULT) {
			//获取传过来的选择的图片
			Bundle bundle = data.getExtras();
			Object[] datas=(Object[]) bundle.get("selectPicture");
			//创建intent将选择的图片传送到下一个activity
			Intent intent = new Intent();
			intent.putExtra("selectPicture", datas);
			
			Log.d("TAG", "发送选择的图片sc" + datas.toString());
			ScaleImageFromSdcardActivity.this.setResult(
					Utils.SHOW_PICTURE_RESULT, intent);

			ScaleImageFromSdcardActivity.this.finish();
			// 设置切换动画，从左边进入，右边退出
			overridePendingTransition(R.anim.in_from_left, R.anim.out_to_rigth);
			imageAdapter.ClickList.clear();// 清除选择数据
		}

	}

}
