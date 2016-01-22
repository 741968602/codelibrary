package com.eagle.parking.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.eagle.parking.R;
import com.eagle.safe.Joggle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	private Context context;

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
//		db.close();
		// 创建数据库
		Log.i("创建数据库+DBHelper", "创建数据库");
		db.setVersion(Joggle.VERSHION);
//		new AreaDatabase(context);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//创建数据库
		Log.i("创建数据库+DBHelper", "更新数据库");
		
		switch (oldVersion) {//查看数据库，当数据库为1时，下面的程序依次进行，不需要break
		case 1://版本2
			Log.i("更新数据库---", "1111");
			creatTableACF(db);
			
		case 2://版本3
			Log.i("更新数据库---", "2222");
			changeNews(db);
			createTablePeccancy(db);
			
			
		default:
			break;
		}
	}
	
	/**
	 * 创建违章查询表，版本2更新内容
	 */
	private void creatTableACF(SQLiteDatabase db){
		
		String creatTable = "CREATE TABLE 'ACF_SEARH_CONDITION' ( "
				+ "'ABBR' varchar(20),'CITY_CODE'  varchar(20),"
				+ "'CITY_NAME'  varchar(20),"
				+ "'PROVINCE'  varchar(20),'id'  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL);";
		
		db.execSQL(creatTable);
		
		InputStream inputStream = context.getResources().openRawResource(R.raw.acf_searh_condition);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		
		String sqlStr = "";
		db.beginTransaction();
		try {
			while ((sqlStr =bufferedReader.readLine())!=null) {
				
				db.execSQL(sqlStr);
			}
			db.setTransactionSuccessful();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
			db.endTransaction();
		}
		
	}
	
	/**
	 * 修改news表，添加parkId/parkName;版本3
	 * @param db
	 */
	private void changeNews(SQLiteDatabase db){
		db.beginTransaction();
		
		db.execSQL("ALTER TABLE tbl_news add column parkId TEXT(11) default('')");
		db.execSQL("ALTER TABLE tbl_news add column parkName TEXT(100) default('')");
		db.execSQL("ALTER TABLE tbl_news add column expireDate TEXT(32) default('')");
		
		
		db.setTransactionSuccessful();
		db.endTransaction();
	}
	
	/**
	 * 创建存储违章查询，车牌号对应车架号/发动机号
	 * @param db
	 */
	private void createTablePeccancy(SQLiteDatabase db){
		
		String creatTableSQL = "CREATE TABLE 'tbl_peccancy' ( "
				+ "'vehicleId' varchar(50) not null,'engine'  varchar(100),"
				+ "'frame'  varchar(100),"
				+" PRIMARY KEY ('vehicleId'))";
		
		db.beginTransaction();
		
		db.execSQL(creatTableSQL);
		
		db.setTransactionSuccessful();
		db.endTransaction();
	}

}
