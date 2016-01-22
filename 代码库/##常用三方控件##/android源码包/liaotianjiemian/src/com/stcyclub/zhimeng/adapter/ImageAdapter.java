package com.stcyclub.zhimeng.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.stcyclub.zhimeng.R;

public class ImageAdapter extends BaseAdapter{

    private Context ctx;
    
    private  List<Bitmap> bitmapList1 ;
    
    public LayoutInflater inflater;
    Resources res;
    public static ArrayList<Integer> ClickList=new ArrayList<Integer>();
    /**
     * 记录选择的照片编号
     * @param position
     */
    public void add(int position){
    	int j=-1;
    	for (int i = 0; i < ClickList.size(); i++) {
			if(ClickList.get(i)==position){
				j=i;
				break;
			}
		}
    	if(j<0){
    		ClickList.add(position);
    	}else{
    		ClickList.remove(j);
    	}
    }
    public ArrayList<Integer> getClickList() {
		return ClickList;
	}
	// 构造函数
    public ImageAdapter(Context context)
    {
        this.ctx = context;
        inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        res = ctx.getResources();
    }
    
  

	public void setImgMap(List<Bitmap> bitmapList1)
    {
        this.bitmapList1 = bitmapList1;
    }

   

  

    @Override
    public int getCount()
    {
        // TODO Auto-generated method stub
        return bitmapList1.size();
    }

    @Override
    public Object getItem(int position)
    {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {	
        	convertView = inflater.inflate(R.layout.adapter_grid_item, null);
        }
        // 给ImageView设置资源
        ImageView imageView = (ImageView) convertView.findViewById(R.id.AdapterGridItemTwoImage);
        LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.item_layout);
        // 设置布局图片120*120显示
        //imageView.setLayoutParams(new GridView.LayoutParams(125, 125));
        
        // 设置自显示比例类型
        //imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        

        //遍历
        if (bitmapList1 != null)
        {
            if (bitmapList1!= null && position < bitmapList1.size())
            {
            	int clickTemp=-1;
            	for (int i = 0; i < ClickList.size(); i++) {
        			if(ClickList.get(i)==position){
        				clickTemp=position;
        				break;
        			}
        		}
                if(clickTemp>=0){
                	//Log.d("TAG", "clickTemp"+clickTemp);
                	BitmapDrawable bd=new BitmapDrawable(bitmapList1.get(position));
                	layout.setBackgroundDrawable(bd);
                	imageView.setImageBitmap(null);
                	imageView.setBackgroundResource(R.drawable.picture_select);
                }else{
                	imageView.setImageBitmap(bitmapList1.get(position));
                	layout.setBackgroundResource(R.color.transparent);
                }
            }
            else
            {
                imageView.setImageResource(R.drawable.ic_launcher);
            }
        }
        return convertView;
    }
    public void addData(List<Bitmap> bitmapList1 ){
    	this.bitmapList1=bitmapList1;
    	notifyDataSetChanged();
    }
   

}
