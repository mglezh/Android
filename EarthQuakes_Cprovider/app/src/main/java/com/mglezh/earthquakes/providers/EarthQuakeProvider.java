package com.mglezh.earthquakes.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;

public class EarthQuakeProvider extends ContentProvider {

    public	static	final Uri CONTENT_URI = Uri.parse("content://com.mglezh.earthquakes.provider/earthquakes");
    private	static	final	int	ALLROWS	=	1;
    private	static	final	int	SINGLE_ROW	=	2;
    private	static	final UriMatcher uriMatcher;

    private EarthQuakeOpenHelper earthQuakeOpenHelper;

    static	{
        uriMatcher	= new	UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.mglezh.earthquakes.provider",  "earthquakes",	ALLROWS);
        uriMatcher.addURI("com.mglezh.earthquakes.provider",  "earthquakes/#",	SINGLE_ROW);
    }


    public static class Columns implements BaseColumns{
        public static final String id_KEY = "_id";
        public static final String place_KEY = "place";
        public static final String magnitude_KEY = "magnitude";
        public static final String lat_KEY = "lat";
        public static final String long_KEY = "long";
        public static final String url_KEY = "url";
        public static final String time_KEY = "time";
    }

    public EarthQuakeProvider() {
    }

    @Override
    public String getType(Uri uri) {
        //	Return	a	string	that	identifies	the	MIME	type
        //	for	a	Content	Provider	URI
        switch	(uriMatcher.match(uri))	{
            case	ALLROWS:
                return	"vnd.android.cursor.dir/vnd.mglezh.provider.earthquakes";
            case	SINGLE_ROW:
                return	"vnd.android.cursor.item/vnd.mglezh.provider.earthquakes";
            default:
                throw	new	IllegalArgumentException("Unsupported	URI:	"	+	uri);
        }
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        earthQuakeOpenHelper = new	EarthQuakeOpenHelper(getContext(),
        EarthQuakeOpenHelper.DATABASE_NAME,null,EarthQuakeOpenHelper.DATABASE_VERSION);
        return true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = earthQuakeOpenHelper.getWritableDatabase();
        /*
        switch	(uriMatcher.match(uri))	{
            case :
                String table
        }*/
        long id = db.insert(EarthQuakeOpenHelper.DATABASE_TABLE, null, values);

        if (id> -1) {
            //	Construct	and	return	the	URI	of	the	newly	inserted	row.
            Uri	insertedId = ContentUris.withAppendedId(CONTENT_URI, id);
            //	Notify	any	observers	of	the	change	in	the	data	set.
            getContext().getContentResolver().notifyChange(insertedId,	null);
            return	insertedId;
        }
        else
            return	null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db;
        try {
            db = earthQuakeOpenHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
        db = earthQuakeOpenHelper.getReadableDatabase();
        }

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch	(uriMatcher.match(uri))	{
            case SINGLE_ROW	:
                String	rowID =	uri.getPathSegments().get(1);
                queryBuilder.appendWhere(Columns.id_KEY	+ "=");
                selectionArgs = new String[] {rowID};
            default:	break;
        }

        queryBuilder.setTables(EarthQuakeOpenHelper.DATABASE_TABLE);

        Cursor cursor = queryBuilder.query(db,projection,selection,
                selectionArgs,null, null,sortOrder);

        return cursor;
    }

    private	static	class EarthQuakeOpenHelper	extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "earthQuakes.db";
        private static final String DATABASE_TABLE = "EARTHQUAKES";
        private static final int DATABASE_VERSION = 1;

        //	SQL	Statement	to	create	a	new	database.
        private static final String DATABASE_CREATE =
                "CREATE TABLE " +
                        DATABASE_TABLE +
                        "(" +
                        Columns.id_KEY + " TEXT PRIMARY KEY, " +
                        Columns.place_KEY + " TEXT, magnitude REAL, " +
                        Columns.lat_KEY + " REAL, " +
                        Columns.long_KEY + " REAL, " +
                        Columns.url_KEY + " TEXT, " +
                        Columns.time_KEY + " INTEGER)";

        private EarthQuakeOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //	Simplest	case	is	to	drop	the	old	table	and	create	a	new	one.
            db.execSQL("DROP TABLE IF EXISTS"	+	DATABASE_TABLE);
            //	Create	a	new	one.
            onCreate(db);
        }
    }

        @Override
        public int update(Uri uri, ContentValues values, String selection,
                          String[] selectionArgs) {
            // TODO: Implement this to handle requests to update one or more rows.
            throw new UnsupportedOperationException("Not yet implemented");
        }

        @Override
        public int delete(Uri uri, String selection, String[] selectionArgs) {
            // Implement this to handle requests to delete one or more rows.
            throw new UnsupportedOperationException("Not yet implemented");
        }

    }
