package com.mglezh.earthquakes.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.util.Log;

import com.mglezh.earthquakes.R;
import com.mglezh.earthquakes.activities.MainActivity;
import com.mglezh.earthquakes.database.EarthQuakesDB;
import com.mglezh.earthquakes.model.Coordinate;
import com.mglezh.earthquakes.model.EarthQuake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadEarthQuakesService extends Service {

    private EarthQuakesDB earthQuakeDB;
    private JSONObject json;
    private String TAG = "UPDATE_EarthQuake";
    private int NOTIFICATION_QUAKE = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        this.earthQuakeDB = new EarthQuakesDB(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        Thread t = new Thread(new Runnable(){
            @Override
            public void run() {
                updateEarthQuakes(getString(R.string.earthquakes_url));
            }
        });
        t.start();

        return Service.START_STICKY;
    }

    private Integer updateEarthQuakes(String earthquakesfeed){

        Integer count = 0;

        try	{
            URL url = new URL(earthquakesfeed);

            //	Create	a	new	HTTP	URL	connection
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection)connection;

            int	responseCode	=	httpConnection.getResponseCode();
            if	(responseCode	==	HttpURLConnection.HTTP_OK)	{
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(
                        httpConnection.getInputStream(), "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();

                String inputStr;
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);

                json = new JSONObject(responseStrBuilder.toString());
                JSONArray earthquakes = json.getJSONArray("features");

                count = earthquakes.length();

                for (int i = earthquakes.length()-1; i >= 0; i--) {
                    processEarthQuakeTask(earthquakes.getJSONObject(i));
                }

                if (count > 0) {
                    showNotification(count);
                }
             }
        } catch	(MalformedURLException e)	{
            e.printStackTrace();
        } catch	(IOException e)	{
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return count;
    }

    private void showNotification(int newQuakes) {

        Intent intentToFire = new Intent(this, MainActivity.class);
        PendingIntent activityIntent = PendingIntent.getActivity(this,0, intentToFire, 0);

        Notification.Builder builder = new Notification.Builder(this);

        builder.setSmallIcon(R.drawable.ic_launcher)
                //.setTicker(Integer.toString(newQuakes) + " new earthquakes")
                .setContentTitle(getString(R.string.app_name))
                .setContentText(Integer.toString(newQuakes) + " new earthquakes")
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_SOUND |
                        Notification.DEFAULT_VIBRATE)
                .setSound(
                        RingtoneManager.getDefaultUri(
                                RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setContentIntent(activityIntent);

        Notification notification =	builder.getNotification();

        String	svc	= this.NOTIFICATION_SERVICE;
        NotificationManager	notificationManager
                =	(NotificationManager)getSystemService(svc);
        notificationManager.notify(NOTIFICATION_QUAKE, notification);
    }

    private void processEarthQuakeTask(JSONObject jsonObject){
        try{
            String id = jsonObject.getString("id");
            JSONArray jsonCoords = jsonObject.getJSONObject("geometry").getJSONArray("coordinates");
            Coordinate coords = new Coordinate(jsonCoords.getDouble(0), jsonCoords.getDouble(1), jsonCoords.getDouble(2));
            JSONObject properties  = jsonObject.getJSONObject("properties");

            EarthQuake earthQuake = new EarthQuake();
            earthQuake.set_id(id);
            earthQuake.setPlace(properties.getString("place"));
            earthQuake.setMagnitude(properties.getDouble("mag"));
            earthQuake.setTime(properties.getLong("time"));
            earthQuake.setUrl(properties.getString("url"));
            earthQuake.setCoords(coords);

            Log.d(TAG, earthQuake.toString());
            Log.d(TAG, coords.toString());

            earthQuakeDB.putEarthQuake(earthQuake);

            // publishProgress(earthQuake);
            // Provoca que se llame al onProgressUpdate

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
