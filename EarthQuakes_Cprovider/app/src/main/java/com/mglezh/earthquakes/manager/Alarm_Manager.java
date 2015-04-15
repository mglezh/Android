package com.mglezh.earthquakes.manager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.mglezh.earthquakes.R;
import com.mglezh.earthquakes.services.DownloadEarthQuakesService;

/**
 * Created by cursomovil on 1/04/15.
 */
public class Alarm_Manager {
    // Al ser static puedo acceder a ellos sin instanciar la clase
    public static void setAlarm(Context context, int interval){

        //Get	a	reference	to	the	Alarm	Manager
        AlarmManager alarmManager =
                (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        //Set	the	alarm	to	wake	the	device	if	sleeping.
        int	alarmType =	AlarmManager.RTC;

        Intent download	=	new	Intent(context, DownloadEarthQuakesService.class);
        PendingIntent alarmIntent;
        alarmIntent	=	PendingIntent.getService(context, 0, download, 0);

        //Wake	up	the	device	to	fire	an	alarm	in	half	an	hour,	and	every
        //half-hour	after	that.
        alarmManager.setInexactRepeating(alarmType,
                interval,
                interval,
                alarmIntent);
    }

    public static void cancelAlarm(Context context){
        AlarmManager alarmManager =
                (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        Intent download	=	new	Intent(context, DownloadEarthQuakesService.class);
        PendingIntent alarmIntent;
        alarmIntent	=	PendingIntent.getService(context, 0, download, 0);
        alarmManager.cancel(alarmIntent);
    }

    public static void updateAlarm(Context context, int interval){
        setAlarm(context, interval);
    }

}
