package com.eagle.parking.utils;

import java.util.ArrayList;
import java.util.List;

import com.eagle.parking.R;
import com.eagle.parking.adapter.ParkSimpleAdapter;
import com.eagle.parking.vo.Park;
import com.eagle.safe.Joggle;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ParkNameDialog extends BaseDialog{
	
	public interface OnParkNameDialogListener{
		public void back(Park park);
	}
	
	private static final int GET_PARKLIST = 1000;
	
	private List<Park> parknameList;
	private List<Park> searchList;
	private ListView parknameListView;
	private EditText searchEdit;
	private ParkSimpleAdapter parkSimpleAdapter;
	private OnParkNameDialogListener parkNameDialogListener;


	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case Joggle.HANDLER_NETWORK:
				showNetWorkToast();
				break;

			case GET_PARKLIST:
				parknameList = MyParseJson.parkSimple((String) msg.obj);
				
				if(parknameList.size()>0){
					
					//初始化数据
					searchList.clear();
					searchList.addAll(parknameList);
					
					setParkSimpleAdapter();
					
					searchEdit.addTextChangedListener(searchTextWatcher);
					
				}else{
					Toast.makeText(getContext(), "未查到任何停车场，请稍后再查", Toast.LENGTH_SHORT).show();
				}
				
				break;
			
				
			default:
				break;
			}
		};
	};
	
	
	public ParkNameDialog(Context context,OnParkNameDialogListener parkNameDialogListener) {
		super(context);
		this.parkNameDialogListener = parkNameDialogListener;
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parkname_dialog);
		
		
		searchEdit = (EditText)findViewById(R.id.search_edit);
		parknameListView = (ListView)findViewById(R.id.listview);
		
		searchList = new ArrayList<Park>();
		
		showProgress();
		new Thread(getParkListRunnable).start();
		
	}

	//请求停车场列表
	private Runnable getParkListRunnable = new Runnable() {
		
		@Override
		public void run() {
			
			String result = MyWebService.getParkList();
			dismissProgress();
			
			if(result == null){//网络异常
				handler.sendMessage(handler.obtainMessage(Joggle.HANDLER_NETWORK));
				
			}else{
				
				handler.sendMessage(handler.obtainMessage(GET_PARKLIST, result));
			}
			
		}
	};

	
	//设置停车场选择对话框的选择事件
	private void setParkSimpleAdapter(){
		parkSimpleAdapter = new ParkSimpleAdapter(searchList, getContext());
		parknameListView.setAdapter(parkSimpleAdapter);
		parknameListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Park park =  searchList.get(position);
				parkNameDialogListener.back(park);
				
				dismiss();
			}
		});
	}
	
	
	// 搜索的监听
	private TextWatcher searchTextWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void afterTextChanged(Editable s) {
			searchList.clear();
			for (int i = 0; i < parknameList.size(); i++) {
				if (parknameList.get(i).getParkName().contains(searchEdit.getText().toString())) {
					searchList.add(parknameList.get(i));
				}
				
				setParkSimpleAdapter();
			}
		}
	};
}
