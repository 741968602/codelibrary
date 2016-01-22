package com.eagle.parking.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eagle.parking.R;
import com.eagle.parking.R.id;
import com.eagle.parking.R.layout;
import com.eagle.parking.vo.ConsumeWaste;
import com.eagle.parking.vo.ParkingSpotLease;

public class ParkingOrderConsumeListAdapter extends BaseAdapter{

	private List<ParkingSpotLease> list;
	private LayoutInflater inflater;
	private Context context;
	
	
	public ParkingOrderConsumeListAdapter(List<ParkingSpotLease> list,Context context){
		this.list = list;
		this.context = context;
		this.inflater = LayoutInflater .from(context);
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder = null;
		
		//if(convertView == null){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.parkingorserconsume_list, null);
			
			holder.orderconsume_parkname = (TextView) convertView.findViewById(R.id.orderconsume_parkname);
			holder.orderconsume_consume = (TextView) convertView .findViewById(R.id.orderconsume_consume);
			holder.orderconsume_orderStartTime = (TextView) convertView.findViewById(R.id.orderconsume_orderStartTime);
			holder.orderconsume_status = (TextView) convertView.findViewById(R.id.orderconsume_status);
			holder.orderconsume_img=(ImageView)convertView.findViewById(R.id.orderconsume_img);
			ParkingSpotLease userPark = list.get(position);
			
			if(!"已完成".equals(userPark.getConsumeOrderStatus())){
				holder.orderconsume_img.setVisibility(View.INVISIBLE);
				userPark.setConsume("");
				holder.orderconsume_consume.setText(userPark.getConsume());//消费
			}

			else{
				holder.orderconsume_consume.setText(userPark.getConsume()+"元");//消费
			}
			holder.orderconsume_parkname.setText(userPark.getParkName()+"-"+userPark.getSpaceId()+"号");//停车场名与车位号
			holder.orderconsume_orderStartTime.setText(userPark.getOrderStartTime().split(" ")[0]);//订单开始时间
			holder.orderconsume_status.setText(userPark.getConsumeOrderStatus());//订单状态
			convertView.setTag(holder);
	//	}else{
	//		holder = (ViewHolder) convertView.getTag();
	//	}
		
	
		return convertView;
		
		
	}

	
	class ViewHolder{
		TextView orderconsume_parkname;//停车场名称
		TextView orderconsume_status;//订单状态
		TextView orderconsume_consume;//消费金额
		TextView orderconsume_orderStartTime;//订单开始时间
		ImageView orderconsume_img;
	}

}
