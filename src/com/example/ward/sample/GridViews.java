package com.example.ward.sample;

import android.net.Uri;
import android.provider.BaseColumns;

public class GridViews {

	public static String AUTHORITY = "com.cellcom.sqlite";
	
	//构造方法私有化，使之不能被实例化
	private GridViews(){
		
	}
	
	public static final class GridInfos implements BaseColumns{
//		//构造方法私有化，使之不能被实例化
//		private GridInfos(){
//			
//		}
		
		public static final String TABLE_NAME = "grids";
		
        /**
         * The scheme part for this provider's URI
         */
        private static final String SCHEME = "content://";
        
        /**
         * Path part for the grids URI
         */
        private static final String PATH_GRIDS = "/grids";
        
        /**
         * Path part for the Note ID URI
         */
        private static final String PATH_GRID_ID = "/grids/";
        
        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI =  Uri.parse(SCHEME + AUTHORITY + PATH_GRIDS);
        
        /**
         * The content URI base for a single note. Callers must
         * append a numeric note id to this Uri to retrieve a note
         */
        public static final Uri CONTENT_ID_URI_BASE
            = Uri.parse(SCHEME + AUTHORITY + PATH_GRID_ID);
        
        /**
         * Column name for the img_id of the note
         * <P>Type: INTEGER</P>
         */
        public static String COLUMN_NAME_IMG_ID = "img_id";

        /**
         * Column name of the name
         * <P>Type: TEXT</P>
         */
        public static String COLUMN_NAME_NAME = "name";

//        /**
//         * Column name for the creation timestamp
//         * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
//         */
//        public static final String COLUMN_NAME_CREATE_DATE = "created";

	}
//	public int ID = -1;
//	public int IMG_ID;
//	public String NAME;
}
