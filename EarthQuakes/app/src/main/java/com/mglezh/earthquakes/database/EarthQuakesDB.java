package com.mglezh.earthquakes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mglezh.earthquakes.model.EarthQuake;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cursomovil on 27/03/15.
 */
public class EarthQuakesDB {

    private EarthQuakeOpenHelper helper;
    private SQLiteDatabase db;

    public static final String id_KEY = "_id";
    public static final String place_KEY = "place";
    public static final String magnitude_KEY = "magnitude";
    public static final String lat_KEY = "lat";
    public static final String long_KEY = "long";
    public static final String url_KEY = "url";
    public static final String time_KEY = "time";

    String[] result_columns	= new String[] {id_KEY, magnitude_KEY, place_KEY, lat_KEY, long_KEY, url_KEY, time_KEY};

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
                DATABASE_TABLE  +
                "(" +
                id_KEY    + " TEXT PRIMARY KEY, " +
                place_KEY + " TEXT, magnitude REAL, " +
                lat_KEY   + " REAL, " +
                long_KEY  + " REAL, " +
                url_KEY   + " TEXT, " +
                time_KEY  + " INTEGER)";


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
        try {
            db.insert(helper.DATABASE_TABLE, null, newValues);
        } catch (android.database.SQLException ex){

        }

    }

    /*

    public Cursor getEarthQuakeFiltersByMagnitude(double magnitude){

        // Los elementos se obtienen mediante una Query que me devuelve un cursor


        String	where	=	magnitude_KEY	+	">?" ;

        String	whereArgs[]	=	new String[] {Double.toString(magnitude)}; // Sustituye al ?
        String	groupBy	    =	null;
        String	having	    =	null;
        String	order	    =	null;


        Cursor cursor =	db.query(helper.DATABASE_TABLE,
                result_columns,	where,
                whereArgs,	groupBy,	having,	order);

        return cursor;

    }
    */

    public List<EarthQuake> getAll(double magnitude){ return query(null,null);}

    public List<EarthQuake> getAllByMagnitude(double magnitude){

        String	where	=	magnitude_KEY	+	">=?" ;
        String	whereArgs[]	=	new String[] {Double.toString(magnitude)}; // Sustituye al ?
        return query(where,whereArgs);

    };

    public EarthQuake getEarthQuake(String id){

        String	where	=	id_KEY	+	"=?" ;
        String	whereArgs[]	=	new String[] {id}; // Sustituye al ?

        return query(where,whereArgs).get(0);
    };

    private List<EarthQuake> query(String where, String[] whereArgs){

        List<EarthQuake> earthQuakes = new ArrayList<>();

        Cursor cursor =	db.query(
                helper.DATABASE_TABLE,
                result_columns,
                where,
                whereArgs,
                null,
                null,
                time_KEY + " DESC");

        HashMap<String, Integer> indexes = new HashMap<>();
        for (int i = 0; i < result_columns.length; i++) {
            indexes.put(result_columns[i], cursor.getColumnIndex(result_columns[i]));
        }
        while (cursor.moveToNext())	{
            // Si no creo un terremoto nuevo cada vez al agregarlos en la lista todos apuntarían al mismo elemento
            // terremoto porque al adicionar un elemento a una lista lo que se agrega es su dirección
            EarthQuake earthquake = new EarthQuake();
            earthquake.set_id(cursor.getString(indexes.get(id_KEY)));
            earthquake.setMagnitude(cursor.getDouble(indexes.get(magnitude_KEY)));
            earthquake.setPlace(cursor.getString(indexes.get(place_KEY)));
            //earthquake.setCoords();
            earthquake.setUrl(cursor.getString(indexes.get(url_KEY)));
            earthquake.setTime(cursor.getLong(indexes.get(time_KEY)));

            earthQuakes.add(0, earthquake);
        }

        cursor.close();

        return earthQuakes;

    };

}
