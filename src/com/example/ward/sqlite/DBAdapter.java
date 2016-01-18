package com.example.ward.sqlite;
//package com.cellcom.sqlite;
//
//import com.cellcom.grid.GridViews;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteDatabase.CursorFactory;
//import android.database.sqlite.SQLiteException;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;
//import android.webkit.WebChromeClient.CustomViewCallback;
//
//public class DBAdapter {
//	public static final String TAG = "SQLite" ;//LogCat
//	private static final String DB_NAME = "grid.db" ;//数据库名
//	private static final String DB_TABLE = "gridview" ;//数据库表名
//	private static final int DB_VERSION = 1 ;//数据库版本号
//	public static final String KEY_ID = "_id" ; //表属性ID
//	public static final String KEY_IMG_ID = "img_id"; //表属性图片ID
//	public static final String KEY_NAME = "name"; //表属性
//	
//	private SQLiteDatabase db ;   //声明一个SQLiteDatanbase对象
//	private Context mContext ;    //声明一个Context对象
//	
//	private DBOpenHelper dbOpenHelper;  //声明一个DBOpenHelper对象
//	
//	public DBAdapter(Context context){
//		mContext = context;
//	}
//	
//	/** 空间不够存储的时候设为只读
//	* @throws SQLiteException
//	*/
//	public void open() throws SQLiteException {
//		//创建，打开数据库grade.db
//		dbOpenHelper = new DBOpenHelper(mContext, DB_NAME, null, DB_VERSION);
//		try {
//			db = dbOpenHelper.getWritableDatabase();
//		} catch (SQLiteException e) {
//			db = dbOpenHelper.getReadableDatabase();
//		}
//	}
//	
//	/**
//	* 调用SQLiteDatabase对象的close()方法关闭数据库
//	*/
//	public void close() {
//		if (db != null) {
//			db.close();
//			db = null;
//		}
//	}
//	
//	/**
//	* 向表中添加一条数据
//	*/
//	public long insert(GridViews gridInfo) {
//		ContentValues newValues = new ContentValues();
//		newValues.put(KEY_IMG_ID, gridInfo.IMG_ID);
//		newValues.put(KEY_NAME, gridInfo.NAME);
//		return db.insert(DB_TABLE, null, newValues);
//	}
//	
//	/**
//	* 根据输入ID删除一条数据
//	* @param id
//	* @return
//	*/
//	public long deleteOneData(long id) {
//		return db.delete(DB_TABLE, KEY_ID + "=" + id, null);
//	}
//	
//	/**
//	* 删除所有数据
//	* @return
//	*/
//	public long deleteAllData() {
//		return db.delete(DB_TABLE, null, null);
//	}
//	
//	/**
//	* 根据id查询数据的代码
//	* @param id
//	* @return
//	*/
//	public GridViews[] queryOneData(long id) {
//		Cursor result = db.query(DB_TABLE, new String[] { KEY_ID, KEY_IMG_ID,
//				KEY_NAME}, KEY_ID + "=" + id, null, null, null,
//				null);
//		return ConvertToGridInfo(result);
//	}
//	
//	/**
//	* 查询全部数据
//	* @return
//	*/
//	public GridViews[] queryAllData() {
//		Cursor result = db.query(DB_TABLE, new String[]{KEY_ID, KEY_IMG_ID,KEY_NAME}, null, null, null, null, null);
//		return ConvertToGridInfo(result);
//	}
//	
//	/**
//	* 根据id更新一条数据
//	*/
//	public long updateOneData(long id, GridViews gridInfo) {
//		ContentValues newValues = new ContentValues();
//		newValues.put(KEY_IMG_ID, gridInfo.IMG_ID);
//		newValues.put(KEY_NAME, gridInfo.NAME);
//		return db.update(DB_TABLE, newValues, KEY_ID + "=" + id, null);
//	}
//	
//	/**
//	* ConvertToGrade(Cursor cursor)是私有函数，
//	* 作用是将查询结果转换为用来存储数据自定义的grade类对象
//	*/
////	private GridViews[] ConvertToGridInfo(Cursor cursor) {
////		int resultCounts = cursor.getCount();
////		if (resultCounts == 0 || !cursor.moveToFirst()) {
////			return null;
////		}
////		GridViews[] gridInfos = new GridViews[resultCounts];
////		Log.i(TAG, "grades len:" + gridInfos.length);
////		for (int i = 0; i < resultCounts; i++) {
////			gridInfos[i]  = new GridViews();
////			gridInfos[i].ID = cursor.getInt(0);
////			gridInfos[i].IMG_ID = cursor.getInt(cursor.getColumnIndex(KEY_IMG_ID));
////			gridInfos[i].NAME = cursor.getString(cursor.getColumnIndex(KEY_NAME));
////					
////			Log.i(TAG, "grade " + i + "info :" + gridInfos[i].toString());
////			cursor.moveToNext();
////		}
////		return gridInfos;
////	}
//	
//	
//	/**
//	* 静态Helper类，用于建立、更新和打开数据库
//	*/
//	private static class DBOpenHelper extends SQLiteOpenHelper {
//		/*
//		 * 使用SQL 命令创建表
//		 */
//		private static final String DB_CREATE = "CREATE TABLE " + DB_TABLE + " ("
//				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//				+ KEY_IMG_ID + " INTEGER,"
//				+ KEY_NAME + " TEXT"
//				+ ");";
//
//		//创建和打开数据库
//		public DBOpenHelper(Context context, String name,
//				CursorFactory factory, int version) {
//			super(context, name, factory, version);
//		}
//
//		/*
//		 * 函数在数据库第一次建立时被调用， 一般用来用来创建数据库中的表，并做适当的初始化工作
//		 */
//		@Override
//		public void onCreate(SQLiteDatabase db) {
//			db.execSQL(DB_CREATE);
//			Log.i(TAG, "onCreate");
//		}
//
//		/*
//		 * SQL命令。onUpgrade()函数在数据库需要升级时被调用， 通过调用SQLiteDatabase对象的execSQL()方法，
//		 * 执行创建表的一般用来删除旧的数据库表，并将数据转移到新版本的数据库表中
//		 */
//		@Override
//		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
//			// 为了简单起见，并没有做任何的的数据转移，而仅仅删除原有的表后建立新的数据库表
//			Log.i(TAG, "oldVersion : " + oldVersion + "newVersion: " + newVersion );
//			_db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
//			onCreate(_db);
//			Log.i(TAG, "Upgrade");
//		}
//	}
//}