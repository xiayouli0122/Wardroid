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
//	private static final String DB_NAME = "grid.db" ;//���ݿ���
//	private static final String DB_TABLE = "gridview" ;//���ݿ����
//	private static final int DB_VERSION = 1 ;//���ݿ�汾��
//	public static final String KEY_ID = "_id" ; //������ID
//	public static final String KEY_IMG_ID = "img_id"; //������ͼƬID
//	public static final String KEY_NAME = "name"; //������
//	
//	private SQLiteDatabase db ;   //����һ��SQLiteDatanbase����
//	private Context mContext ;    //����һ��Context����
//	
//	private DBOpenHelper dbOpenHelper;  //����һ��DBOpenHelper����
//	
//	public DBAdapter(Context context){
//		mContext = context;
//	}
//	
//	/** �ռ䲻���洢��ʱ����Ϊֻ��
//	* @throws SQLiteException
//	*/
//	public void open() throws SQLiteException {
//		//�����������ݿ�grade.db
//		dbOpenHelper = new DBOpenHelper(mContext, DB_NAME, null, DB_VERSION);
//		try {
//			db = dbOpenHelper.getWritableDatabase();
//		} catch (SQLiteException e) {
//			db = dbOpenHelper.getReadableDatabase();
//		}
//	}
//	
//	/**
//	* ����SQLiteDatabase�����close()�����ر����ݿ�
//	*/
//	public void close() {
//		if (db != null) {
//			db.close();
//			db = null;
//		}
//	}
//	
//	/**
//	* ��������һ������
//	*/
//	public long insert(GridViews gridInfo) {
//		ContentValues newValues = new ContentValues();
//		newValues.put(KEY_IMG_ID, gridInfo.IMG_ID);
//		newValues.put(KEY_NAME, gridInfo.NAME);
//		return db.insert(DB_TABLE, null, newValues);
//	}
//	
//	/**
//	* ��������IDɾ��һ������
//	* @param id
//	* @return
//	*/
//	public long deleteOneData(long id) {
//		return db.delete(DB_TABLE, KEY_ID + "=" + id, null);
//	}
//	
//	/**
//	* ɾ����������
//	* @return
//	*/
//	public long deleteAllData() {
//		return db.delete(DB_TABLE, null, null);
//	}
//	
//	/**
//	* ����id��ѯ���ݵĴ���
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
//	* ��ѯȫ������
//	* @return
//	*/
//	public GridViews[] queryAllData() {
//		Cursor result = db.query(DB_TABLE, new String[]{KEY_ID, KEY_IMG_ID,KEY_NAME}, null, null, null, null, null);
//		return ConvertToGridInfo(result);
//	}
//	
//	/**
//	* ����id����һ������
//	*/
//	public long updateOneData(long id, GridViews gridInfo) {
//		ContentValues newValues = new ContentValues();
//		newValues.put(KEY_IMG_ID, gridInfo.IMG_ID);
//		newValues.put(KEY_NAME, gridInfo.NAME);
//		return db.update(DB_TABLE, newValues, KEY_ID + "=" + id, null);
//	}
//	
//	/**
//	* ConvertToGrade(Cursor cursor)��˽�к�����
//	* �����ǽ���ѯ���ת��Ϊ�����洢�����Զ����grade�����
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
//	* ��̬Helper�࣬���ڽ��������ºʹ����ݿ�
//	*/
//	private static class DBOpenHelper extends SQLiteOpenHelper {
//		/*
//		 * ʹ��SQL �������
//		 */
//		private static final String DB_CREATE = "CREATE TABLE " + DB_TABLE + " ("
//				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//				+ KEY_IMG_ID + " INTEGER,"
//				+ KEY_NAME + " TEXT"
//				+ ");";
//
//		//�����ʹ����ݿ�
//		public DBOpenHelper(Context context, String name,
//				CursorFactory factory, int version) {
//			super(context, name, factory, version);
//		}
//
//		/*
//		 * ���������ݿ��һ�ν���ʱ�����ã� һ�����������������ݿ��еı������ʵ��ĳ�ʼ������
//		 */
//		@Override
//		public void onCreate(SQLiteDatabase db) {
//			db.execSQL(DB_CREATE);
//			Log.i(TAG, "onCreate");
//		}
//
//		/*
//		 * SQL���onUpgrade()���������ݿ���Ҫ����ʱ�����ã� ͨ������SQLiteDatabase�����execSQL()������
//		 * ִ�д������һ������ɾ���ɵ����ݿ����������ת�Ƶ��°汾�����ݿ����
//		 */
//		@Override
//		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
//			// Ϊ�˼��������û�����κεĵ�����ת�ƣ�������ɾ��ԭ�еı�����µ����ݿ��
//			Log.i(TAG, "oldVersion : " + oldVersion + "newVersion: " + newVersion );
//			_db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
//			onCreate(_db);
//			Log.i(TAG, "Upgrade");
//		}
//	}
//}
