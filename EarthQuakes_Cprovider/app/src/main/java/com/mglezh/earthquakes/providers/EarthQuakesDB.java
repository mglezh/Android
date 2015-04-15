package com.mglezh.earthquakes.providers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mglezh.earthquakes.model.Coordinate;
import com.mglezh.earthquakes.model.EarthQuake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cursomovil on 27/03/15.
 */
public class EarthQuakesDB {

    public static final String id_KEY = "_id";
    public static final String place_KEY = "place";
    public static final String magnitude_KEY = "magnitude";
    public static final String lat_KEY = "lat";
    public static final String long_KEY = "long";
    public static final String url_KEY = "url";
    public static final String time_KEY = "time";

    private Context context;

    public EarthQuakesDB(Context context) {
        this.context = context;
    }

    public static String[] result_columns = new String[]{
            EarthQuakeProvider.Columns.id_KEY,
            EarthQuakeProvider.Columns.magnitude_KEY,
            EarthQuakeProvider.Columns.place_KEY,
            EarthQuakeProvider.Columns.lat_KEY,
            EarthQuakeProvider.Columns.long_KEY,
            EarthQuakeProvider.Columns.url_KEY,
            EarthQuakeProvider.Columns.time_KEY
    };

    public void putEarthQuake(EarthQuake earthQuake) {
        //	Create	a	new	row	of	values	to	insert.
        ContentValues insert = new ContentValues();
        //	Assign	values	for	each	row.

        //id_KEY, place_KEY, magnitude_KEY, lat_KEY, long_KEY, url_KEY, time_KEY
        insert.put(EarthQuakeProvider.Columns.id_KEY, earthQuake.get_id());
        insert.put(EarthQuakeProvider.Columns.magnitude_KEY, earthQuake.getMagnitude());
        insert.put(EarthQuakeProvider.Columns.place_KEY, earthQuake.getPlace());
        insert.put(EarthQuakeProvider.Columns.lat_KEY, earthQuake.getCoords().getLat());
        insert.put(EarthQuakeProvider.Columns.long_KEY, earthQuake.getCoords().getLng());
        insert.put(EarthQuakeProvider.Columns.url_KEY, earthQuake.getUrl());
        insert.put(EarthQuakeProvider.Columns.time_KEY, earthQuake.getTime().getTime());

        //	Get	the	Content	Resolver.
        ContentResolver cr = context.getContentResolver();
        cr.insert(EarthQuakeProvider.CONTENT_URI, insert);
    }


    public List<EarthQuake> getAll(double magnitude) {
        return query(null, null);
    }

    public List<EarthQuake> getAllByMagnitude(double magnitude) {
        String where = magnitude_KEY + ">?";
        String whereArgs[] = new String[]{Double.toString(magnitude)}; // Sustituye al ?
        return query(where, whereArgs);
    }

    ;

    public List<EarthQuake> getById(String id) {
        String where = EarthQuakeProvider.Columns.id_KEY + "=?";
        String whereArgs[] = new String[]{id}; // Sustituye al ?
        return query(where, whereArgs);
    }

    ;

    public EarthQuake getEarthQuake(String id) {

        String where = EarthQuakeProvider.Columns.id_KEY + "=?";
        String whereArgs[] = new String[]{id}; // Sustituye al ?

        return query(where, whereArgs).get(0);
    }

    ;

    private List<EarthQuake> query(String where, String[] whereArgs) {

        List<EarthQuake> earthQuakes = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        String orderBy = EarthQuakeProvider.Columns.time_KEY + " DESC";

        Cursor cursor = cr.query(
                EarthQuakeProvider.CONTENT_URI, result_columns, where, whereArgs, orderBy);

        HashMap<String, Integer> indexes = new HashMap<>();
        for (int i = 0; i < result_columns.length; i++) {
            indexes.put(result_columns[i], cursor.getColumnIndex(result_columns[i]));
        }

        while (cursor.moveToNext()) {
            Coordinate coords = new Coordinate(cursor.getFloat(indexes.get(lat_KEY)), cursor.getFloat(indexes.get(long_KEY)), 0);
            // Si no creo un terremoto nuevo cada vez al agregarlos en la lista todos apuntarían al mismo elemento
            // terremoto porque al adicionar un elemento a una lista lo que se agrega es su dirección
            EarthQuake earthquake = new EarthQuake();
            earthquake.set_id(cursor.getString(indexes.get(id_KEY)));
            earthquake.setMagnitude(cursor.getDouble(indexes.get(magnitude_KEY)));
            earthquake.setPlace(cursor.getString(indexes.get(place_KEY)));

            earthquake.setCoords(coords);
            earthquake.setUrl(cursor.getString(indexes.get(url_KEY)));
            earthquake.setTime(cursor.getLong(indexes.get(time_KEY)));

            earthQuakes.add(0, earthquake);
        }

        cursor.close();

        return earthQuakes;

    }
}
