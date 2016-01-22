package com.jike.photoviewdemo;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 加载网络图片和本地图片
 * 
 * PhotoView进行缩放处理
 * 
 * @author Administrator
 * 
 */

public class MainActivity extends Activity {
	private PhotoView iv_photo;
	private PhotoViewAttacher attacher;
	private ImageLoader loader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		iv_photo = (PhotoView) findViewById(R.id.iv_photo);
		attacher = new PhotoViewAttacher(iv_photo);
		// 加载本地图片，缩放处理
		// try {
		// InputStream is = getAssets().open("photoview.jpg");
		// Bitmap bm = BitmapFactory.decodeStream(is);
		// iv_photo.setImageBitmap(bm);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// 加载网络图片
		loader = ImageLoader.getInstance();
		loader.displayImage("https://www.baidu.com/img/bdlogo.png", iv_photo);
		iv_photo.setOnPhotoTapListener(new OnPhotoTapListener() {
			
			@Override
			public void onPhotoTap(View arg0, float arg1, float arg2) {
				
			}
		});
	}

}
