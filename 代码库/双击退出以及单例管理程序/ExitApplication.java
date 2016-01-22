package com.miaotech.health.utils;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;

/**
 * 单例模式管理所有的Activity统一进行销毁
 * 
 */
public class ExitApplication {

	private List<Activity> activityList = new LinkedList<Activity>();
	private static ExitApplication instance;

	private ExitApplication() {
	}
	

	// 单例模式中获取唯一的ExitApplication()实例
	public static ExitApplication getInstance() {
		if (null == instance) {
			instance = new ExitApplication();
		}
		return instance;

	}

	// 加载到Activity容器中
	public void addActivity(Activity activity) {
		activityList.add((Activity) activity);
		
		System.err.println("acticityList.size=="+activityList.size());
	}

	
	// 遍历所有的Activity并finsh()
	public void exit() {

		for (Activity activity : activityList) {
			((Activity) activity).finish();
		}
		

//		System.exit(0);

	}
}