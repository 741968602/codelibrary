package com.eagle.parking.db;

import java.util.ArrayList;
import java.util.List;

import com.eagle.parking.vo.News;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 对于新闻的信息的相关操作
 * 
 * @author XieWenqian
 *
 */
public class NewsDBManager {
	private static final String TAG = "NewsDBManager";
	private SQLiteDatabase database;

	private static final String DATABASE_PATH = "/data/data/com.eagle.parking/databases";
	private static final String DATABASE_NAME = "area.db";
	private static String outFileName = DATABASE_PATH + "/" + DATABASE_NAME;
	private static final String TABLE_NAME_NEWS = "tbl_news";// 新闻表

	public NewsDBManager(Context context) {
		Log.i(TAG, "NewsDBManager");
		new AreaDatabase(context);
		database = SQLiteDatabase.openOrCreateDatabase(outFileName, null);
	}

	/**
	 * 获取news表,根據date倒序
	 */
	public Cursor queryNewsCursor() {
		Log.i("获取province表", "NewsDBManager------queryNewsCursor");
		Cursor cursor = database.rawQuery("select * from " + TABLE_NAME_NEWS
				+ " order by date desc", null);
		return cursor;
	}

	/**
	 *  查询news具体信息
	 * @return
	 */
	public List<News> queryNewsList() {

		Log.i("查询news表内容", "NewsDBManager------queryNewsList--begin");

		Cursor cursor = queryNewsCursor();
		List<News> list = new ArrayList<News>();

		while (cursor.moveToNext()) {
			News news = new News();
			news.setNewsId(cursor.getInt(cursor.getColumnIndex("newsId")));
			news.setNewsTitle(cursor.getString(cursor
					.getColumnIndex("newsTitle")));
			news.setNewsContent(cursor.getString(cursor
					.getColumnIndex("newsContent")));
			news.setPublisher(cursor.getString(cursor
					.getColumnIndex("publisher")));
			news.setPublishDate(cursor.getString(cursor
					.getColumnIndex("publishDate")));
			news.setRead(cursor.getString(cursor.getColumnIndex("read")));
			news.setDate(cursor.getString(cursor.getColumnIndex("date")));
			news.setParkName(cursor.getString(cursor.getColumnIndex("parkName")));
			list.add(news);
		}
		cursor.close();
		Log.i("查询news表内容---end", list.toString());
		return list;
	}

	/**
	 *  向表内添加信息
	 * @param news
	 */
	public void addNews(News news) {
		Log.i("新增news表内容", "NewsDBManager------addNews--begin");

		ContentValues values = new ContentValues();
		values.put("newsId", news.getNewsId());
		values.put("newsTitle", news.getNewsTitle());
		values.put("newsContent", news.getNewsContent());
		values.put("publisher", news.getPublisher());
		values.put("publishDate", news.getPublishDate());
		values.put("expireDate", news.getExpireDate());
		values.put("read", news.getRead());
		values.put("date", news.getDate());
		values.put("parkId", news.getParkId());
		values.put("parkName", news.getParkName());
		Log.i("新增news表内容", values.toString());
		database.insert(TABLE_NAME_NEWS, null, values);
		Log.i("新增news表内容", "NewsDBManager------addNews--end");
	}

	/**
	 *  将新闻都标为已读
	 */
	public void updateRead() {
		Log.i("标新闻为已读", "NewsDBManager------updateRead--begin");

		ContentValues values = new ContentValues();
		values.put("read", "2");
		database.update(TABLE_NAME_NEWS, values, "read=?", new String[] { "1" });
		Log.i("标新闻为已读", "NewsDBManager------updateRead--end");
	}

	/**
	 * 清空表
	 */
	public void deleteNews() {
		database.delete(TABLE_NAME_NEWS, null, null);
		Log.i(TAG, "删除表内全部新闻停车场");
	}

	/**
	 * 删除过期新闻
	 */
	public void deleteExpireNews(){
		String sql = "DELETE from "+TABLE_NAME_NEWS+" WHERE expireDate < (SELECT datetime('now'))";
		database.execSQL(sql);
	}
	
	/**
	 * 关闭数据库
	 */
	public void close() {
		Log.i("关闭数据库", "NewsDBManager------close");
		database.close();
	}
}
