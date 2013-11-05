package com.example.ward.sqlite;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import com.example.ward.sample.GridViews;
import com.example.ward.sample.GridViews.GridInfos;

public class BaseProvider extends ContentProvider {

	private static final String TAG = "BaseProvider";

	// 数据库名称
	private static final String DATABASE_NAME = "base_grid.db";

	// 数据库版本
	private static final int DATABASE_VERSION = 2;

	public static final String KEY_ID = "_id"; // 表属性ID
	public static final String KEY_IMG_ID = "img_id"; // 表属性图片ID
	public static final String KEY_NAME = "name"; // 表属性

	private SQLiteDatabase db; // 声明一个SQLiteDatanbase对象
	private Context mContext; // 声明一个Context对象
	private static DatabaseHelper mDatabaseHelper;

	// 如果URI 匹配成功，则返回该常量
	private static final int GRIDS = 1;

	/**
	 * A projection map used to select columns from the database
	 */
	private static HashMap<String, String> sNotesProjectionMap;

	/**
	 * A UriMatcher instance
	 */
	private static final UriMatcher sUriMatcher;

	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(GridViews.AUTHORITY, "grids", GRIDS);

		/*
		 * Creates and initializes a projection map that returns all columns
		 */

		// Creates a new projection map instance. The map returns a column name
		// given a string. The two are usually equal.
//		sNotesProjectionMap = new HashMap<String, String>();
//
//		// Maps the string "_ID" to the column name "_ID"
//		sNotesProjectionMap.put(GridViews.GridInfos._ID,
//				GridViews.GridInfos._ID);
//
//		// Maps "img_id" to "title"
//		sNotesProjectionMap.put(GridViews.GridInfos.COLUMN_NAME_IMG_ID,
//				GridViews.GridInfos.COLUMN_NAME_IMG_ID);
//
//		// Maps "name" to "name"
//		sNotesProjectionMap.put(GridViews.GridInfos.COLUMN_NAME_NAME,
//				GridViews.GridInfos.COLUMN_NAME_IMG_ID);

	}

	public BaseProvider(){}
	
	public BaseProvider(Context context) {
		mContext = context;
	}

	/**
	 * 空间不够存储的时候设为只读
	 * 
	 * @throws SQLiteException
	 */
	public void open() throws SQLiteException {
		// 创建，打开数据库grade.db
		mDatabaseHelper = new DatabaseHelper(mContext);
		try {
			db = mDatabaseHelper.getWritableDatabase();
		} catch (SQLiteException e) {
			db = mDatabaseHelper.getReadableDatabase();
		}
	}

	/**
	 * 调用SQLiteDatabase对象的close()方法关闭数据库
	 */
	public void close() {
		if (db != null) {
			db.close();
			db = null;
		}
	}

	// 刪除操作
	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		// TODO Auto-generated method stub
		// Opens the database object in "write" mode.
		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
		String finalWhere;

		int count;

		// Does the delete based on the incoming URI pattern.
		switch (sUriMatcher.match(uri)) {

		// If the incoming pattern matches the general pattern for notes, does a
		// delete
		// based on the incoming "where" columns and arguments.
		case GRIDS:
			count = db.delete(GridViews.GridInfos.TABLE_NAME, // The database
																// table name
					where, // The incoming where clause column names
					whereArgs // The incoming where clause values
					);
			break;

		// If the incoming URI matches a single note ID, does the delete based
		// on the
		// incoming data, but modifies the where clause to restrict it to the
		// particular note ID.
		// case NOTE_ID:
		// /*
		// * Starts a final WHERE clause by restricting it to the
		// * desired note ID.
		// */
		// finalWhere =
		// NotePad.Notes._ID + // The ID column name
		// " = " + // test for equality
		// uri.getPathSegments(). // the incoming note ID
		// get(NotePad.Notes.NOTE_ID_PATH_POSITION)
		// ;
		//
		// // If there were additional selection criteria, append them to the
		// final
		// // WHERE clause
		// if (where != null) {
		// finalWhere = finalWhere + " AND " + where;
		// }
		//
		// // Performs the delete.
		// count = db.delete(
		// NotePad.Notes.TABLE_NAME, // The database table name.
		// finalWhere, // The final WHERE clause
		// whereArgs // The incoming where clause values.
		// );
		// break;

		// If the incoming pattern is invalid, throws an exception.
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		/*
		 * Gets a handle to the content resolver object for the current context,
		 * and notifies it that the incoming URI changed. The object passes this
		 * along to the resolver framework, and observers that have registered
		 * themselves for the provider are notified.
		 */
//		getContext().getContentResolver().notifyChange(uri, null);

		// Returns the number of rows deleted.
		return count;
	}

	// 获得数据类型
	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 插入操作
	 */
	@Override
	public Uri insert(Uri uri, ContentValues values) {

		// Validates the incoming URI. Only the full provider URI is allowed for
		// inserts.
		// 验证参数URI，只有符合的uri才能插入
		if (sUriMatcher.match(uri) != GRIDS) {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		// A map to hold the new record's values.
		ContentValues contentValues;

		// If the incoming values map is not null, uses it for the new values.
		if (values != null) {
			System.out.println("not null");
			contentValues = new ContentValues(values);
		} else {
			System.out.println("null");
			// Otherwise, create a new value map
			contentValues = new ContentValues();
		}

		// Opens the database object in "write" mode.
		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

		// Performs the insert and returns the ID of the new note.
		long rowId = db.insert(GridViews.GridInfos.TABLE_NAME, // The table to
																// insert into.
				null,
				// NotePad.Notes.COLUMN_NAME_NOTE, // A hack, SQLite sets this
				// column value to null
				// // if values is empty.
				contentValues // A map of column names, and the values to insert
								// into the columns.
				);

		// If the insert succeeded, the row ID exists.
		if (rowId > 0) {
//			// Creates a URI with the note ID pattern and the new row ID
//			// appended to it.
			Uri noteUri = ContentUris.withAppendedId(
					GridViews.GridInfos.CONTENT_ID_URI_BASE, rowId);
//
//			// Notifies observers registered against this provider that the data
//			// changed.
//			getContext().getContentResolver().notifyChange(noteUri, null);
//			db.close();
			return noteUri;
		}else {
			// If the insert didn't succeed, then the rowID is <= 0. Throws an
			// exception.
			throw new SQLException("Failed to insert row into " + uri);
		}

	}

	@Override
	public boolean onCreate() {
		
		mDatabaseHelper = new DatabaseHelper(getContext());
		return true;
	}

	// 查詢操作
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
	     SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
	        int match = sUriMatcher.match(uri);
	        if (match == GRIDS) {
				return db.query(GridViews.GridInfos.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
			}else {
				System.out.println("match=" + match);
				return null;
			}
}
	
	public GridInfos[] queryInfos(Uri uri,String[] projection){
		Cursor cursor = query(uri, projection, null, null, null);
		return ConvertToGridInfo(cursor);
	}

	/**
	 * 更新操作
	 */
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		// Opens the database object in "write" mode.
		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
		int count;
		String finalWhere;

		// Does the update based on the incoming URI pattern
		switch (sUriMatcher.match(uri)) {

		// If the incoming URI matches the general notes pattern, does the
		// update based on
		// the incoming data.
		case GRIDS:

			// Does the update and returns the number of rows updated.
			count = db.update(GridViews.GridInfos.TABLE_NAME, // The database
																// table name.
					values, // A map of column names and new values to use.
					selection, // The where clause column names.
					selectionArgs // The where clause column values to select
									// on.
					);
			break;

		// If the incoming URI matches a single note ID, does the update based
		// on the incoming
		// data, but modifies the where clause to restrict it to the particular
		// note ID.
		// case NOTE_ID:
		// // From the incoming URI, get the note ID
		// String noteId =
		// uri.getPathSegments().get(NotePad.Notes.NOTE_ID_PATH_POSITION);
		//
		// /*
		// * Starts creating the final WHERE clause by restricting it to the
		// incoming
		// * note ID.
		// */
		// finalWhere =
		// NotePad.Notes._ID + // The ID column name
		// " = " + // test for equality
		// uri.getPathSegments(). // the incoming note ID
		// get(NotePad.Notes.NOTE_ID_PATH_POSITION)
		// ;
		//
		// // If there were additional selection criteria, append them to the
		// final WHERE
		// // clause
		// if (where !=null) {
		// finalWhere = finalWhere + " AND " + where;
		// }
		//
		//
		// // Does the update and returns the number of rows updated.
		// count = db.update(
		// NotePad.Notes.TABLE_NAME, // The database table name.
		// values, // A map of column names and new values to use.
		// finalWhere, // The final WHERE clause to use
		// // placeholders for whereArgs
		// whereArgs // The where clause column values to select on, or
		// // null if the values are in the where argument.
		// );
		// break;
		// If the incoming pattern is invalid, throws an exception.
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		/*
		 * Gets a handle to the content resolver object for the current context,
		 * and notifies it that the incoming URI changed. The object passes this
		 * along to the resolver framework, and observers that have registered
		 * themselves for the provider are notified.
		 */
		getContext().getContentResolver().notifyChange(uri, null);

		// Returns the number of rows updated.
		return count;

	}
	
	//将Cursor对象转化成GridInfos对象
	private GridInfos[] ConvertToGridInfo(Cursor cursor) {
		int resultCounts = cursor.getCount();
		if (resultCounts == 0 || !cursor.moveToFirst()) {
			return null;
		}
//		GridViews[] gridInfos = new GridViews[resultCounts];
		GridInfos[] gridInfos = new GridViews.GridInfos[resultCounts];
		Log.i(TAG, "grades len:" + gridInfos.length);
		
		for (int i = 0; i < resultCounts; i++) {
			gridInfos[i]  = new GridViews.GridInfos();
//			gridInfos[i]._ID = cursor.getString(0);
			gridInfos[i].COLUMN_NAME_IMG_ID = cursor.getString(cursor.getColumnIndex("img_id"));
			gridInfos[i].COLUMN_NAME_NAME = cursor.getString(cursor.getColumnIndex("name"));
					
			Log.i(TAG, "grade " + i + "info :" + gridInfos[i].toString());
			cursor.moveToNext();
		}
		
		db.close();
		return gridInfos;
	}

	/**
	 * 
	 * This class helps open, create, and upgrade the database file. Set to
	 * package visibility for testing purposes.
	 */
	static class DatabaseHelper extends SQLiteOpenHelper {

		/*
		 * 使用SQL 命令创建表
		 */
		private static final String DB_CREATE = "CREATE TABLE "
				+ GridViews.GridInfos.TABLE_NAME + " ("
				+ GridViews.GridInfos._ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ GridViews.GridInfos.COLUMN_NAME_IMG_ID + " INTEGER,"
				+ GridViews.GridInfos.COLUMN_NAME_NAME + " TEXT" + ");";

		DatabaseHelper(Context context) {
			// calls the super constructor, requesting the default cursor
			// factory.
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		/**
		 * 
		 * Creates the underlying database with table name and column names
		 * taken from the NotePad class.
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DB_CREATE);
		}

		/**
		 * 
		 * Demonstrates that the provider must consider what happens when the
		 * underlying datastore is changed. In this sample, the database is
		 * upgraded the database by destroying the existing data. A real
		 * application should upgrade the database in place.
		 */

		/*
		 * SQL命令。onUpgrade()函数在数据库需要升级时被调用， 通过调用SQLiteDatabase对象的execSQL()方法，
		 * 执行创建表的一般用来删除旧的数据库表，并将数据转移到新版本的数据库表中
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// 为了简单起见，并没有做任何的的数据转移，而仅仅删除原有的表后建立新的数据库表
			Log.i(TAG, "oldVersion : " + oldVersion + "newVersion: "
					+ newVersion);
			db.execSQL("DROP TABLE IF EXISTS " + GridViews.GridInfos.TABLE_NAME);
			onCreate(db);
			Log.i(TAG, "Upgrade");
		}
	}
}
