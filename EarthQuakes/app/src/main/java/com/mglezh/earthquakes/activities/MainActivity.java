package com.mglezh.earthquakes.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mglezh.earthquakes.R;
import com.mglezh.earthquakes.services.DownloadEarthQuakesService;


public class MainActivity extends ActionBarActivity /*implements DownloadEarthquakesTask.AddEarthQuakeInterface*/ {

    static final int PREFS_ACTIVITY = 1;
    private long alarmWait = AlarmManager.INTERVAL_FIFTEEN_MINUTES;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRepeatingAlarm(this);
        //downLoadEartQuakes();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent prefIntent = new Intent(this, SettingActivity.class);
            startActivityForResult(prefIntent, PREFS_ACTIVITY);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onResume() {
        super.onResume();
        // Para que se actualiza la lista cuando se vuelva a esta activity ya creada
        // downLoadEartQuakes();
    }


    private void downLoadEartQuakes(){
        /*
        DownloadEarthquakesTask task = new DownloadEarthquakesTask(getActivity(), this);
        task.execute(getString(R.string.earthquakes_url));
        */
        Intent download = new Intent(this, DownloadEarthQuakesService.class);
        startService(download);
    }

    private	void setRepeatingAlarm(Context context) {

        //Get	a	reference	to	the	Alarm	Manager

        AlarmManager alarmManager =
                (AlarmManager)getSystemService(context.ALARM_SERVICE);
        //Set	the	alarm	to	wake	the	device	if	sleeping.
        int	alarmType =	AlarmManager.ELAPSED_REALTIME;

        Intent	download	=	new	Intent(this, DownloadEarthQuakesService.class);
        PendingIntent alarmIntent	=	PendingIntent.getService(this,	0,
                download,	0);

        //Wake	up	the	device	to	fire	an	alarm	in	half	an	hour,	and	every
        //half-hour	after	that.
        alarmManager.setRepeating(alarmType,
                alarmWait,
                alarmWait,
                alarmIntent);

    }

}
