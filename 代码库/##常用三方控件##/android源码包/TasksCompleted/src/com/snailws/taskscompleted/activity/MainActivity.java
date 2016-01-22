package com.snailws.taskscompleted.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.snailws.taskscompleted.R;

/**
 * 
 * @author naiyu(http://snailws.com)
 * @version 1.0
 */
public class MainActivity extends Activity {
	
	private TasksCompletedView mTasksView;
	
	private int mTotalProgress;
	private int mCurrentProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initVariable();
		initView();
		
		new Thread(new ProgressRunable()).start();
	}
	
	private void initVariable() {
		mTotalProgress = 100;
		mCurrentProgress = 0;
	}
	
	private void initView() {
		mTasksView = (TasksCompletedView) findViewById(R.id.tasks_view);
	}
	
	class ProgressRunable implements Runnable {

		@Override
		public void run() {
			while (mCurrentProgress < mTotalProgress) {
				mCurrentProgress += 1;
				mTasksView.setProgress(mCurrentProgress);
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}


}
