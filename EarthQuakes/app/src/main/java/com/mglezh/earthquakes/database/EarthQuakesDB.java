package com.mglezh.earthquakes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mglezh.earthquakes.model.EarthQuake;

/**
 * Created by cursomovil on 27/03/15.
 */
public class EarthQuakesDB {

    private EarthQuakeOpenHelper helper;
    private SQLiteDatabase db;

    private String id_KEY = "_id";
    private String place_KEY = "place";
    private String magnitude_KEY = "magnitude";
    private String lat_KEY = "lat";
    private String long_KEY = "long";
    private String url_KEY = "url";
    private String time_KEY = "time";

    public EarthQuakesDB(Context context) {
        this.helper = new EarthQuakeOpenHelper(context, EarthQuakeOpenHelper.DATABASE_NAME,
                          null,
                          EarthQuakeOpenHelper.DATABASE_VERSION);

        this.db = helper.getWritableDatabase();
    }

    private	static	class EarthQuakeOpenHelper	extends SQLiteOpenHelper {

        private	static	final	String	DATABASE_NAME	=	"earthQuakes.db";
        private	static	final	String	DATABASE_TABLE	=	"EARTHQUAKES";
        private	static	final	int	DATABASE_VERSION	=	1;

        //	SQL	Statement	to	create	a	new	database.
        private	static	final	String	DATABASE_CREATE	=
                "CREATE TABLE " +
                DATABASE_TABLE +
                "(_id TEXT PRIMARY KEY, place TEXT, magnitude REAL, lat REAL, long REAL, url TEXT, time INTEGER)";

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

    public void putEarthQuake(EarthQuake earthQuake) {
        //	Create	a	new	row	of	values	to	insert.
        ContentValues newValues	=	new	ContentValues();
        //	Assign	values	for	each	row.

        //id_KEY, place_KEY, magnitude_KEY, lat_KEY, long_KEY, url_KEY, time_KEY
        newValues.put(id_KEY,	        earthQuake.get_id());
        newValues.put(magnitude_KEY,	earthQuake.getMagnitude());
        newValues.put(place_KEY,	    earthQuake.getPlace());
        newValues.put(lat_KEY,	        earthQuake.getCoords().getLat());
        newValues.put(long_KEY,	        earthQuake.getCoords().getLng());
        newValues.put(url_KEY,	        earthQuake.getUrl());
        newValues.put(time_KEY,	        earthQuake.getTime().getTime());

        db.insert(helper.DATABASE_TABLE, null, newValues);
    }

    public Cursor getEarthQuakeFiltersByMagnitude(double magnitude){

        String[] result_columns	= new String[] {id_KEY, magnitude_KEY, place_KEY, lat_KEY, long_KEY, url_KEY, time_KEY};

        String	where	=	magnitude_KEY	+	">="	+	magnitude;

        String	whereArgs[]	=	null;
        String	groupBy	    =	null;
        String	having	    =	null;
        String	order	    =	null;


        Cursor cursor =	db.query(helper.DATABASE_TABLE,
                result_columns,	where,
                whereArgs,	groupBy,	having,	order);

        return cursor;

    }


}
