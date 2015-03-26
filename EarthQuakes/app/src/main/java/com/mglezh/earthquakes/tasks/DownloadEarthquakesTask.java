package com.mglezh.earthquakes.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.mglezh.earthquakes.R;
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

/**
 * Created by cursomovil on 25/03/15.
 */
public class DownloadEarthquakesTask extends AsyncTask<String, EarthQuake, Integer>  {

    public interface AddEarthQuakeInterface {
        public void addEarthQuake(EarthQuake earthquake);
        public void notifyTotal(Integer Total);
    }

    private JSONObject json;
    private String TAG = "UPDATE_EarthQuake";

    private AddEarthQuakeInterface target;

    public DownloadEarthquakesTask(AddEarthQuakeInterface target) {
        this.target = target;
    }

    @Override
    protected Integer doInBackground(String... urls) {
        Integer count = null;

        if (urls.length > 0){
            count = updateEarthQuakes(urls[0]);
        }
        return count; // Lo que devuelve aquí va como parámetro Integer al onPostExecute(Integer integer)
    }

    @Override
    protected void onProgressUpdate(EarthQuake... earthQuakes) {
        super.onProgressUpdate(earthQuakes);
        target.addEarthQuake(earthQuakes[0]);
        //
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        target.notifyTotal(integer);
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

            publishProgress(earthQuake);
            // Provoca que se llame al onProgressUpdate

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
