package com.eagle.parking.db;

import java.util.ArrayList;
import java.util.List;

import com.eagle.parking.vo.Park;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ParkCollectionsDBManager {
	private static final String TAG = "ParkCollectionsDBManager";
	private SQLiteDatabase database;

	private static final String DATABASE_PATH = "/data/data/com.eagle.parking/databases";
	private static final String DATABASE_NAME = "area.db";
	private static String outFileName = DATABASE_PATH + "/" + DATABASE_NAME;
	private static final String COLLECTIONS = "tbl_parkcollections";// 新闻表
	
	public ParkCollectionsDBManager(Context context) {
		Log.i(TAG, "ParkCollectionsDBManager");
		new AreaDatabase(context);
		database = SQLiteDatabase.openOrCreateDatabase(outFileName, null);
	}
	
	
	/**
	 * 获取tbl_parkcollections表
	 * @return 表的游标
	 */
	public Cursor queryParkCollectionsCursor() {
		Log.i(TAG, "queryParkCollectionsCursor---begin");
		Cursor cursor = database.rawQuery("select * from " + COLLECTIONS, null);
		return cursor;
	}
	
	
	/**
	 * 获取表内容
	 * @return 停车场列表
	 */
	public List<Park> queryParkCollections(){
		Cursor cursor = queryParkCollectionsCursor();
		List<Park> list = new ArrayList<Park>();
		
		while(cursor.moveToNext()){
			Park park = new Park();
			park.setParkId(cursor.getInt(cursor.getColumnIndex("parkId")));
			park.setParkName(cursor.getString(cursor.getColumnIndex("parkName")));
			park.setParkAddress(cursor.getString(cursor.getColumnIndex("parkAddress")));
			list.add(park);
		}
		cursor.close();
		
		Log.i(TAG, "queryParkCollections--end"+list.toString());
		return list;		
	}
	
	
	/**
	 * 添加已收藏停车场
	 * @param park某个停车场
	 */
	public void addParkCollections(Park park){		
		ContentValues values = new ContentValues();
		
		//先查询是否有此停车场
		if(queryPark(park.getParkId())){
			return ;
		}
		values.put("parkId", park.getParkId());
		values.put("parkName", park.getParkName());
		values.put("parkAddress", park.getParkAddress());
		database.insert(COLLECTIONS, null, values);
		
		Log.i(TAG, "addParkCollections--end"+park);
	}
	
	/**
	 * 根据id，查找某个停车场
	 * @param parkId需要被查找的停车场id
	 * @return 当找到此id时，返回true
	 */
	public boolean queryPark(int parkId){
		Cursor cursor = database.query(COLLECTIONS, new String[]{"parkId"}, "parkId=?", new String[]{String.valueOf(parkId)}, null, null, null);
		if(cursor.moveToNext()){
			return true;
		}
		return false;
	}
	
	/**
	 * 删除某个停车场
	 * @param parkId
	 */
	public void deleteParkCollections(int parkId){
		database.delete(COLLECTIONS, "parkId=?", new String[]{String.valueOf(parkId)});
		Log.i(TAG, "deleteParkCollections--end"+parkId);
	}
	
	/**
	 * 删除所有停车场内容
	 */
	public void deleteParks(){
//		database.delete(COLLECTIONS,"parkId>?", new String[]{String.valueOf(0)});
		database.delete(COLLECTIONS,null,null);
		Log.i(TAG, "删除表内全部停车场");
	}
	
	/**
	 * 关闭数据库
	 */
	public void close(){
		database.close();
		Log.i(TAG, "close--end");
	}
}
