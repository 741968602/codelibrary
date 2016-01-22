package com.stcyclub.zhimeng.utils;

import java.text.SimpleDateFormat;

import net.tsz.afinal.FinalDb;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class Utils {
	
	public static final int GET_CAMERA =0x11;//�������
	public static final int SHOW_CAMERA = 0x12;//��ʾ�����ͼƬ
	public static final int SHOW_CAMERA_RESULT = 0x13;//������Ƭ�鿴��Ĳ���
	public static final int SHOW_ALL_PICTURE = 0x14;//�鿴ͼƬ
	public static final int SHOW_PICTURE_RESULT = 0x15;//�鿴ͼƬ����
	public static final int SHOW_SELECT_PICTURE = 0x16;//�鿴ѡ��ͼƬ
	public static final int SHOW_SELECT_PICTURE_RESULT = 0x17;//�鿴ѡ���ȷ������ͼƬ����
	
	public static final int CLOSE_INPUT = 0x01;//�ر������
	public static final int CLOSE_MORE_OPERATE = 0x02;//�ر�������·������������Ľ���
	public static final int UPDATA_MSG = 0x03;//�ر�������·������������Ľ���
	public static final int CLOSE_MSG_HINT = 0x04;//�ر����������Ϣ��ʾ
	public static final int PERSONAL_MSG_HINT = 0x05;//һ���˵���Ϣ��ʾ
	public static final int MULTI_MSG_HINT = 0x06;//����˵���Ϣ��ʾ
	public static final int UNREAD_MULTI_MSG_HINT = 0x07;//δ����Ϣ����˵���Ϣ��ʾ
		
	public static Handler handler;//���ڹرձ���
	public static Handler handlerInput;//���������
	public static Handler updataMsg;//���ڸ�����������������Ϣ
	public static FinalDb db;
	
	public static final int CHANGED = 100;//����UI
	public static  String CHAT_UID ;//��ǰ��������uid
	// ��ȡAppKey
    public static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String apiKey = null;
        if (context == null || metaKey == null) {
        	return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
            	apiKey = metaData.getString(metaKey);
            }
        } catch (NameNotFoundException e) {
        }
        return apiKey;
    }
  
	/**
	 * toast�ķ�װ
	 * @param ctx Context
	 * @param text show Info
	 * @param show show Time
	 * @return
	 */
	public static Toast showToast(Context ctx,String text,int show){
		Toast t=Toast.makeText(ctx, text, show);
		return t;
	}
	/*
	 * toast�ķ�װ
	 */
	public static Toast showToast(Context ctx,String text,int show,int gravity, int xOffset, int yOffset){
		Toast t=Toast.makeText(ctx, text, show);
		t.setGravity(gravity, xOffset, yOffset);
		
		return t;
	}
	/**
	 * ��ȡϵͳ��ǰʱ��
	 * @return
	 */
	public static String getNowDate(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String nowTime=sdf.format(System.currentTimeMillis());
		return nowTime;
	}
	/**
	 * ��ȡϵͳ��ǰ��ϸʱ��
	 * @return
	 */
	public static String getNowDateDetial(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime=sdf.format(System.currentTimeMillis());
		return nowTime;
	}

}
