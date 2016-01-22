package com.stcyclub.zhimeng.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Stack;

import net.tsz.afinal.FinalDb;
import android.app.Activity;
import android.app.Application;
import android.util.Log;

/**
 * 
 * @author xy
 *
 */
public class MyApplication extends Application {
	public static MyApplication myApplicaton;
	Stack<Activity> stack=new Stack<Activity>();
	public static boolean checkUpdate=true;   //��֤����
	//�������ڸ�ʽ
	public static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private FinalDb db;//����FinalDb ���ݿ����
	@Override
	public void onCreate() {
		db.create(this,"cloudTeam");
//		GroupInfo g=new GroupInfo();
//		MemberInfo m=new MemberInfo();
//		MessageInfo msg=new MessageInfo();
//		db.save(g);
//		db.save(m);
//		db.save(msg);
		myApplicaton= MyApplication.this;
	}
	
	
	/**
	 * 
	 * @return MyApplicaton
	 */
	public static MyApplication getInstens(){
			
			return myApplicaton;
		}
	public void addActivity(Activity ac){
		stack.push(ac);  //ѹջ
	}
	public void popActivity(Activity ac){
		ac.finish();
		stack.remove(ac);
	}
	public void destoryActivity(){
		
		Log.i("TAG", "stack.size()==="+stack.size());
		for (Activity ac : stack) {
			if(ac!=null){
				ac.finish();
			}
		}
		MyApplication.this.stack.clear();  
		
	}
	/**
	 * �˳�ϵͳ
	 */
	public void myExitApp(){
		destoryActivity();
		System.exit(0);
	}
}
