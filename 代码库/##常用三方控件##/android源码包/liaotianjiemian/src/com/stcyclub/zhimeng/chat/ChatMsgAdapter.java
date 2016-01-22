package com.stcyclub.zhimeng.chat;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

import com.stcyclub.zhimeng.R;
import com.stcyclub.zhimeng.po.MemberInfo;
import com.stcyclub.zhimeng.po.MessageInfo;
import com.stcyclub.zhimeng.utils.Utils;

/**
 ******************************************
 * @文件描述	: 消息数据填充起
 ******************************************
 */
public class ChatMsgAdapter extends BaseAdapter {

	public static interface IMsgViewType {
		int IMVT_COM_MSG = 0;
		int IMVT_TO_MSG = 1;
	}

	private List<ChatMsgEntity> coll;
	private LayoutInflater mInflater;
	private Context context;
	Resources res;
	public ChatMsgAdapter(Context context, List<ChatMsgEntity> coll) {
		this.coll = coll;
		mInflater = LayoutInflater.from(context);
		this.context = context;
		res = context.getResources();
	}

	public int getCount() {
		return coll.size();
	}

	public Object getItem(int position) {
		return coll.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public int getItemViewType(int position) {
		ChatMsgEntity entity = coll.get(position);
		if (entity.getMsgType()) {
			return IMsgViewType.IMVT_COM_MSG;
		} else {
			return IMsgViewType.IMVT_TO_MSG;
		}
	}

	public int getViewTypeCount() {
		return 2;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ChatMsgEntity entity = coll.get(position);
		boolean isComMsg = entity.getMsgType();

		ViewHolder viewHolder = null;
		if (convertView == null) {
			if (isComMsg) {
				convertView = mInflater.inflate(R.layout.chatting_item_msg_text_left, null);
				ImageView img=(ImageView) convertView.findViewById(R.id.iv_userhead);
				
				//Log.d("TAG", "uid..."+entity.getName());
				//修改为相应的照片
				img.setImageBitmap(getIcon(entity.getName()));
				
			} else {
				convertView = mInflater.inflate(R.layout.chatting_item_msg_text_right, null);
				ImageView img=(ImageView) convertView.findViewById(R.id.iv_userhead);
				//设置为自己的照片(应该在本地数据库中读取，如果没有就设置默认的)
				img.setImageBitmap(BitmapFactory.decodeResource(res,R.drawable.xiaohei));
				
			}
			viewHolder = new ViewHolder();
			viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
			viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
			viewHolder.progressBar1 = (ProgressBar) convertView.findViewById(R.id.progressBar1);
			viewHolder.sendFail = (ImageView) convertView.findViewById(R.id.sendFail);
			viewHolder.isComMsg = isComMsg;
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if(null!=entity.getStatus()&&viewHolder.isComMsg==false){
			Log.d("TAG", "entity.getStatus()..."+entity.getStatus());
			if(entity.getStatus().equals("0")){
				Log.d("TAG", "显示发送加载框");
				viewHolder.progressBar1.setVisibility(View.VISIBLE);
				if(viewHolder.sendFail.getVisibility()==View.VISIBLE){
					viewHolder.sendFail.setVisibility(View.GONE);
				}
			}else if(entity.getStatus().equals("00")){
				Log.d("TAG", "隐藏发送加载框");
				if(viewHolder.progressBar1.getVisibility()==View.VISIBLE){
					viewHolder.progressBar1.setVisibility(View.GONE);
				}
				viewHolder.sendFail.setVisibility(View.VISIBLE);
			}else{
				Log.d("TAG", "隐藏发送加载框");
				if(viewHolder.progressBar1.getVisibility()==View.VISIBLE){
					viewHolder.progressBar1.setVisibility(View.GONE);
				}
				if(viewHolder.sendFail.getVisibility()==View.VISIBLE){
					viewHolder.sendFail.setVisibility(View.GONE);
				}
			}
		}
		viewHolder.tvSendTime.setText(entity.getDate());
		SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(context, entity.getText());
		viewHolder.tvContent.setText(spannableString);
		convertView.setBackgroundResource(R.color.white);
		return convertView;
	}

	class ViewHolder {
		public TextView tvSendTime;
		public TextView tvContent;
		public ProgressBar progressBar1;
		public ImageView sendFail;
		public boolean isComMsg = true;
	}
	
	public Bitmap getIcon(String uid){
		Bitmap bmp;
//		ArrayList<MemberInfo> ms=(ArrayList<MemberInfo>) Utils.db.findAll(MemberInfo.class);
//		Log.d("TAG", "ms.."+ms.size());
//		for (int i = 0; i < ms.size(); i++) {
//			Log.d("TAG", "getUid.."+ms.get(i).getUid());
//			
//		}
		Log.d("TAG", "getIcon.."+uid);
		
		MemberInfo m=Utils.db.findById(uid, MemberInfo.class) ;
		if(null!=m){
			if(null==m.getThumb()){//设置为默认的
				bmp=BitmapFactory.decodeResource(res,R.drawable.xiaohei);
				//Log.d("TAG", "来获取成员的图像是默认的..default_icon20");
				return bmp;
			}else{//设置为传过来的图片
				bmp=BitmapFactory.decodeResource(res,R.drawable.default_icon22);
				//Log.d("TAG", "获取成员的图像是自带的default_icon20");
				return bmp;
			}
		}else{//该对象不存在
			bmp=BitmapFactory.decodeResource(res,R.drawable.xiaohei);
			//Log.d("TAG", "来获取成员的图像是默认的..default_icon20");
			return bmp;
			
		}
	//	return null;
		
	}
	/**
	 * 对传进来的新消息进行更新
	 * @param msgInfo
	 */
	public void addNewMsg(MessageInfo msgInfo) {
		ChatMsgEntity chat=new ChatMsgEntity();
		chat.setDate(msgInfo.getMsg_acceptTime());
		chat.setMsgType(true);
		chat.setName(msgInfo.getMsg_from()+"");
		chat.setText(msgInfo.getMsg_content());
		Log.d("TAG", "chat.."+chat.toString());
		coll.add(chat);
		notifyDataSetChanged();
	}
	/**
	 * 对重新发生的消息进行状态的更新
	 */
	public void updataMsg(int position,String status){
		ChatMsgEntity chat=coll.get(position);
		chat.setStatus(status);
		coll.set(position, chat);
		notifyDataSetChanged();
	}
	
	/**
	 * 返回点击事件的处的内容
	 * @param position
	 * @return
	 */
	public ChatMsgEntity sendMsg(int position){
		ChatMsgEntity chatMsg=coll.get(position);
		//coll.remove(position);
		return chatMsg;
	}



}
