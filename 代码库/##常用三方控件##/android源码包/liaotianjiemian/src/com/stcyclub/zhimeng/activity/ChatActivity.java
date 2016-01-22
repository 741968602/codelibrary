package com.stcyclub.zhimeng.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import net.tsz.afinal.FinalDb;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stcyclub.zhimeng.R;
import com.stcyclub.zhimeng.chat.ChatMsgAdapter;
import com.stcyclub.zhimeng.chat.ChatMsgEntity;
import com.stcyclub.zhimeng.chat.FaceRelativeLayout;
import com.stcyclub.zhimeng.po.MemberInfo;
import com.stcyclub.zhimeng.po.MessageInfo;
import com.stcyclub.zhimeng.utils.Utils;
/**
 * 0�������ڷ���
 * 1�����ͳɹ�
 * 00��������ʧ��
 * @author xy
 *
 */
public class ChatActivity extends Activity {
	EditText et_sendmessage;
	//LinearLayout more_operate;
	private ListView mListView;
	private ChatMsgAdapter mAdapter;
	private final String PREFERENCES_NAME = "cloudTeam_userinfo";
	SharedPreferences preferences ;
	
	private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();
	private List<ChatMsgEntity> unSendmDataArrays = new ArrayList<ChatMsgEntity>();

	private List<MessageInfo> msgInfo=new ArrayList<MessageInfo>();
	
	
	RelativeLayout ll_facechoose;
	ImageButton btn_face;
	TextView nickName;
	InputMethodManager imm;
	MemberInfo memberInfo;//�������
	TextView msgHint;
	closeHintThread ct=null;
	@Override  
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ����û��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chat_activity);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		//��ʼ��preferences
		preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
		Utils.db = FinalDb.create(this, "mydb");
		
		init();
		
		//��������Ϣ
		Utils.updataMsg=new Handler(){
			public void handleMessage(Message msg) {
				if(msg.what==Utils.UPDATA_MSG){//����������Ϣ
					MessageInfo msgInfo=(MessageInfo) msg.obj;
					Log.d("TAG", "��������Ϣ.."+msgInfo.toString());
					Utils.db.save(msgInfo);//����Ϣ���浽���ݿ�
					if(msgInfo.getMsg_from().equals(Utils.CHAT_UID)){
						mAdapter.addNewMsg(msgInfo);
					}else{
						//������Ƿ�����ǰ�������������Ϣ�����Ϸ�����ʾ������ʾ
						MemberInfo m=Utils.db.findById(msgInfo.getMsg_from(), MemberInfo.class);
						if(null!=m){
							String str=m.getNickname()+"������Ϣ";
							msgHint.setText(str);
							msgHint.setVisibility(View.VISIBLE);
							ct=new closeHintThread();
							ct.start();
						}
					}
				}
			};
		};
	}

	

	/**
	 * ��ʼ������
	 */
	private void init() {
		Utils.handlerInput=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what==Utils.CLOSE_INPUT){//�ر������
					imm.hideSoftInputFromWindow(ChatActivity.this.getCurrentFocus().getWindowToken(),  
	                         InputMethodManager.HIDE_NOT_ALWAYS);
				}else if(msg.what==Utils.CLOSE_MSG_HINT){
					//������Ϣ��ʾ
					msgHint.setVisibility(View.GONE);
				}
			}
		};
		
		imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		// ����activityʱ���Զ����������
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		et_sendmessage = (EditText) findViewById(R.id.et_sendmessage);
		mListView=(ListView) findViewById(R.id.listview);
		ll_facechoose=(RelativeLayout) findViewById(R.id.ll_facechoose);
		btn_face=(ImageButton) findViewById(R.id.btn_face);
		nickName=(TextView) findViewById(R.id.nickName);
		msgHint=(TextView) findViewById(R.id.msgHint);
		
		
		nickName.setText("����į����");
		mAdapter = new ChatMsgAdapter(this, mDataArrays);
		mListView.setAdapter(mAdapter);
		mListView.setSelection(mListView.getCount() - 1);
		
	}
	
	
	public int clickPosition=-1;
	
	/**
	 * �԰�ť��ӵ���¼�����
	 * @param v
	 *     View
	 */
	public void btnClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			ChatActivity.this.finish();
			//�˳�ʱ�Ƴ���ǰ���������
			Utils.CHAT_UID="";
			break;
		case R.id.btn_send:
			// ������Ϣ
			String sendMsg=et_sendmessage.getText().toString();
			if(sendMsg.length()>0){
				sendMessage("0",sendMsg,true);
			}
			break;
		case R.id.chatView:
			// �رձ���,���������ʱ
			Utils.handler.sendEmptyMessage(1);
			break;
		case R.id.chat_camera:
			//�������
			Intent intent = new Intent();
			Intent intent_camera = getPackageManager().getLaunchIntentForPackage("com.android.camera");
			if (intent_camera != null) {
				intent.setPackage("com.android.camera");
			}
			intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
			ChatActivity.this.startActivityForResult(intent, Utils.GET_CAMERA);
			break;
		case R.id.chat_picture:
			intent=new Intent(ChatActivity.this,ScaleImageFromSdcardActivity.class);
			ChatActivity.this.startActivityForResult(intent, Utils.SHOW_ALL_PICTURE);
			//�����л����������ұ߽��룬����˳�
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);  				
			
			break;
		case R.id.chat_file:
			//intent=new Intent(ChatActivity.this,LocationFileLiabraryActivity.class);
//			intent=new Intent(ChatActivity.this,FileLibraryActivity.class);
//			ChatActivity.this.startActivityForResult(intent, 1001);
//			//�����л����������ұ߽��룬����˳�
//			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);  				
			
			break;
		case R.id.chat_person:
			
			break;
		case R.id.right_btn:
//			Intent intent =new Intent(ChatActivity.this,TestPushActivity.class);
//			ChatActivity.this.startActivity(intent);
			break;
		default:
			break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		Log.d("TAG", "requestCode"+requestCode+" ,resultCode"+resultCode);
		if(requestCode ==Utils.GET_CAMERA && resultCode == Activity.RESULT_OK && null != data){
			   String sdState=Environment.getExternalStorageState();
			   if(!sdState.equals(Environment.MEDIA_MOUNTED)){
				   Toast t=Utils.showToast(getApplicationContext(), "δ�ҵ�SDK", Toast.LENGTH_LONG);
				   t.show();
			    Log.d("TAG", "sd card unmount");
			    return;
			   }
			   new DateFormat();
			   String name= DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA))+".jpg";
			   Bundle bundle = data.getExtras();
			   //��ȡ������ص����ݣ���ת��ΪͼƬ��ʽ
			   Bitmap bitmap;
			   String filename = null;
			   bitmap = (Bitmap)bundle.get("data");
			   FileOutputStream fout = null;
			   //�����ļ��洢·��
			   File file = new File("/sdcard/cloudteam/");
			   if(!file.exists()){
				   file.mkdirs();
			   }
			   filename=file.getPath()+"/"+name;
			   try {
			    fout = new FileOutputStream(filename);
			    //��ͼƬ����ѹ��
			    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout);
			   } catch (FileNotFoundException e) {
			    e.printStackTrace();
			   }finally{
			    try {
			     fout.flush();
			     fout.close();
			    } catch (IOException e) {
			     e.printStackTrace();
			    }
			   }
			   Log.d("TAG", "��Ƭ·��"+filename);
			   //ȥ��һ��ҳ��鿴ͼƬ
			   Intent intent=new Intent(ChatActivity.this,CameraActivity.class);
			   intent.putExtra("camera", filename);
			   ChatActivity.this.startActivityForResult(intent, Utils.SHOW_CAMERA);

		}else if(requestCode==Utils.SHOW_CAMERA&&resultCode==Utils.SHOW_CAMERA_RESULT){
			Bundle bundle = data.getExtras();
			Object camera=bundle.get("imgUrl");
			Log.d("TAG", "��Ҫ���������ͼƬ��������"+camera.toString());
			//��ͼƬ���͵��������
			if(camera.toString().length()>0){
				sendMessage("0","["+camera.toString()+"]",true);
			}
			//��ͼƬ�첽���͵������������Ұѷ��͵�ͼƬ��ʾ���������
			
		}else if(requestCode==Utils.SHOW_ALL_PICTURE&&resultCode==Utils.SHOW_PICTURE_RESULT){
			Log.d("TAG", "��Ҫ��ѡ���ͼƬ���͵�������");
			List<String> bmpUrls=new ArrayList<String>();
			
			Bundle bundle = data.getExtras();
			Object[] selectPictures=(Object[]) bundle.get("selectPicture");
			for (int i = 0; i < selectPictures.length; i++) {
				Log.d("TAG", "selectPictures[i]"+selectPictures[i]);
				String bmpUrl=ScaleImageFromSdcardActivity.map.get(Integer.parseInt(selectPictures[i].toString()));
				bmpUrls.add(bmpUrl);
				Log.d("TAG", "bmp"+bmpUrl);
				sendMessage("0","["+bmpUrl+"]",true);
			}
			Log.d("TAG", "bmps.size()"+bmpUrls.size());
			Toast.makeText(ChatActivity.this, "ѡ��ͼƬ��"+selectPictures.length, Toast.LENGTH_LONG).show();
			//��ͼƬ���͵�������
			
			
			
		}else if(requestCode==1001&&resultCode==10001){
			Log.d("TAG", "��Ҫ��ѡ����ļ����͵�������");
			Bundle bundle = data.getExtras();
			String filePath=bundle.getString("filePath");
			Toast.makeText(ChatActivity.this, "filePath"+filePath, Toast.LENGTH_LONG).show();
		}
	}
	
	
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK&& ((FaceRelativeLayout) findViewById(R.id.FaceRelativeLayout)).hideFaceView()) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	/**
	 * ������Ϣ
	 */
	public void sendMessage(String id,String sendMsg,final boolean isNew) {
		String contString =sendMsg ;
		//������Ϣ�ı���
		final MessageInfo msg=new MessageInfo();
		if(!(id.endsWith("0"))){//�����id���Ǵ����ݿ���ȡ��������
			msg.set_id(Integer.parseInt(id));
		}
		msg.setMsg_content(contString);
		//msg.setMsg_from(preferences.getString("USERID", ""));
		//msg.setMsg_to(memberInfo.getUid());
		msg.setMsg_acceptTime(Utils.getNowDate());
		
//		if(msg.getMsg_from().equals(msg.getMsg_to())){//������Լ����Լ�����Ϣ����ȥ������Ϣ����
//			msg.setMsg_from("");
//		}
		Log.d("TAG", "msg���͵���Ϣ..."+msg.toString());
		if (contString.length() > 0) {
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setDb_id(msg.get_id()+"");
			entity.setDate(Utils.getNowDate());
			//entity.setName(preferences.getString("USERID", ""));
			entity.setMsgType(false);
			entity.setText(contString);
			entity.setStatus("0");
			if(isNew){
				mDataArrays.add(entity);
			}
			mAdapter.notifyDataSetChanged();
			et_sendmessage.setText("");
			mListView.setSelection(mListView.getCount() - 1);
		}
		
		//������Ϣ�ķ��͵�������
		
	}
	 
	

	/**
	 * ������ʾ��Ϣ
	 *
	 */
	class closeHintThread extends Thread {

		@Override
		public void run() {
			super.run();
			try {
				sleep(3000);
				Utils.handlerInput.sendEmptyMessage(Utils.CLOSE_MSG_HINT);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	protected void onDestroy() {
		//�˳���ʱ��û�з��ͳ�ȥ����Ϣ���浽���ݿ���
//		for (int i = 0; i < unSendmDataArrays.size(); i++) {
//			Utils.db.save(unSendmDataArrays.get(i));
//		}
		if(null!=ct&&ct.isAlive()){
			ct.interrupt();
		}
		super.onDestroy();
	}
}
